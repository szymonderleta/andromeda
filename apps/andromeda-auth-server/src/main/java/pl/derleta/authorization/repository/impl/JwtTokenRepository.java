package pl.derleta.authorization.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.derleta.authorization.domain.entity.JwtTokenEntity;
import pl.derleta.authorization.domain.entity.UserEntity;
import pl.derleta.authorization.repository.RepositoryClass;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Repository responsible for managing JWT token data storage and retrieval operations,
 * primarily interacting with the `andromeda.jwt_tokens` table and its associated users.
 * Provides methods for CRUD operations, token validation, and advanced queries such as pagination,
 * sorting, and filtering.
 */
@Repository
public class JwtTokenRepository implements RepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor to initialize the JwtTokenRepository with a given data source.
     *
     * @param dataSource the DataSource object used to configure the JdbcTemplate
     */
    @Autowired
    public JwtTokenRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total count of records in the `andromeda.jwt_tokens` table.
     *
     * @return the count of records as an {@code Integer}.
     */
    public Integer getSize() {
        String sql = """
                SELECT COUNT(*) FROM andromeda.jwt_tokens;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Retrieves a paginated list of {@link JwtTokenEntity} objects along with their associated user details.
     *
     * @param offset the starting index for pagination
     * @param size the number of records to retrieve
     * @return a list of {@link JwtTokenEntity} objects representing JWT tokens with their associated user details
     */
    public List<JwtTokenEntity> getPage(final int offset, final int size) {
        String sql = """ 
                SELECT u.*, jt.*
                FROM andromeda.users u
                JOIN andromeda.jwt_tokens jt ON u.user_id = jt.user_id
                LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new JwtTokenMapper(), offset, size);
    }

    /**
     * Retrieves a paginated and sorted list of valid JWT token entities along with their associated user details.
     * A token is considered valid if its expiration date is later than the current time.
     *
     * @param offset the starting index for pagination
     * @param size the number of records to retrieve
     * @param sortByParam the column name by which to sort the results
     * @param sortOrderParam the sort order, either "ASC" (ascending) or "DESC" (descending)
     * @return a list of {@link JwtTokenEntity} objects representing valid JWT tokens with their associated user details
     */
    public List<JwtTokenEntity> findValid(final int offset, final int size, final String sortByParam, final String sortOrderParam) {
        String sql = """
                    SELECT u.*, jt.*
                    FROM andromeda.users u
                    JOIN andromeda.jwt_tokens jt ON u.user_id = jt.user_id
                    WHERE expiration_date > NOW()
                    ORDER BY ?, ?
                    LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new JwtTokenMapper(),
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT),
                offset, size);
    }

    /**
     * Retrieves a {@link JwtTokenEntity} by its unique token ID from the database.
     * The method joins user and token data to construct a composite object representing the token and its associated user.
     *
     * @param tokenId the unique identifier of the token to be retrieved
     * @return the {@link JwtTokenEntity} matching the specified token ID, or null if no match is found
     */
    public JwtTokenEntity findById(final long tokenId) {
        String sql = """
                  SELECT u.*, jt.*
                  FROM andromeda.users u
                  JOIN andromeda.jwt_tokens jt ON u.user_id = jt.user_id
                  WHERE token_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new JwtTokenMapper(), tokenId);
    }

    /**
     * Saves a new JWT token record to the database with the specified token ID, user ID,
     * token string, and an expiration date set to one day from the current time.
     *
     * @param tokenId the unique identifier of the token to be inserted
     * @param userId the unique identifier of the user associated with the token
     * @param token the token string to be saved
     */
    public void save(final long tokenId, final long userId, final String token) {
        String sql = """ 
                    INSERT INTO andromeda.jwt_tokens (token_id, user_id, token, expiration_date)
                    VALUES (?, ?, ?, NOW() + INTERVAL 1 DAY);
                """;
        jdbcTemplate.update(sql,
                tokenId,
                userId,
                token);
    }

    /**
     * Retrieves the next available unique identifier for a JWT token
     * by querying the maximum value of the token ID in the database
     * and incrementing it by one.
     *
     * @return the next available token ID as a Long, or null if the query result is empty
     */
    public Long getNextId() {
        String idSql = "SELECT MAX(token_id) + 1 FROM andromeda.jwt_tokens";
        return jdbcTemplate.queryForObject(idSql, Long.class);
    }

    /**
     * Deletes a JWT token record from the database for the specified token ID and user ID.
     *
     * @param tokenId the unique identifier of the token to be deleted
     * @param userId the unique identifier of the user associated with the token to be deleted
     */
    public void deleteById(final long tokenId, final long userId) {
        String sql = """ 
                    DELETE FROM andromeda.jwt_tokens
                    WHERE token_id = ? AND user_id = ?;
                """;
        jdbcTemplate.update(sql, tokenId, userId);
    }

    /**
     * Retrieves a sorted and filtered list of JWT token entities with associated user details.
     * This method allows pagination and sorting, and filters results by provided username
     * and email patterns.
     *
     * @param offset the starting index for pagination
     * @param size the number of records to retrieve
     * @param sortByParam the column name by which to sort the results
     * @param sortOrderParam the sort order, either "ASC" (ascending) or "DESC" (descending)
     * @param username the username filter pattern to match, supports SQL LIKE syntax
     * @param email the email filter pattern to match, supports SQL LIKE syntax
     * @return a list of {@link JwtTokenEntity} objects that match the specified filters and sorting parameters
     */
    public List<JwtTokenEntity> getSortedPageWithFilters(final int offset, final int size, final String sortByParam, final String sortOrderParam, final String username, final String email) {
        String sql = """ 
                SELECT u.*, jt.*
                FROM andromeda.users u
                JOIN andromeda.jwt_tokens jt ON u.user_id = jt.user_id
                WHERE u.username LIKE ?
                AND u.email LIKE ?
                ORDER BY ?, ?
                LIMIT ?, ?;
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        return jdbcTemplate.query(sql, new JwtTokenMapper(),
                usernameParam, emailParam,
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT),
                offset, size);
    }

    /**
     * Retrieves the count of records from the database where the username and email
     * match the given filter patterns. The filters are applied using SQL LIKE
     * operators for partial matching.
     *
     * @param username the username filter pattern to match, wrapped in wildcard '%'
     * @param email the email filter pattern to match, wrapped in wildcard '%'
     * @return the count of records matching the specified username and email filters as a Long
     */
    public Long getFiltersCount(final String username, final String email) {
        String sql = """ 
                SELECT COUNT(*)
                FROM andromeda.users u
                JOIN andromeda.jwt_tokens jt ON u.user_id = jt.user_id
                WHERE u.username LIKE ?
                AND u.email LIKE ?;
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        return jdbcTemplate.queryForObject(sql, Long.class, usernameParam, emailParam);
    }

    /**
     * Retrieves the count of valid JWT tokens from the database.
     * A token is considered valid if its expiration date is later than the current time.
     *
     * @return the number of valid JWT tokens as a Long
     */
    public Long getValidCount() {
        String sql = """
                    SELECT COUNT(*)
                    FROM andromeda.jwt_tokens jt
                    WHERE expiration_date > NOW();
                """;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    /**
     * A mapper class that implements the {@link RowMapper} interface to map rows of a {@link ResultSet}
     * to instances of {@link JwtTokenEntity}.
     *
     * This class is designed to extract the necessary columns from the result set and construct a
     * {@link JwtTokenEntity} object, along with its associated {@link UserEntity}.
     *
     * The mapping is performed by overriding the {@code mapRow} method, which pulls user-related and
     * token-related data from the result set and populates the corresponding objects.
     */
    private static class JwtTokenMapper implements RowMapper<JwtTokenEntity> {
        @Override
        public JwtTokenEntity mapRow(ResultSet resultSet, int i) throws SQLException {

            long userId = resultSet.getLong("u.user_id");
            String username = resultSet.getString("u.username");
            String email = resultSet.getString("u.email");
            String password = resultSet.getString("u.password");
            UserEntity userEntity = new UserEntity(userId, username, email, password);

            long tokenId = resultSet.getLong("token_id");
            String token = resultSet.getString("jt.token");
            Timestamp expirationDate = resultSet.getTimestamp("jt.expiration_date");

            return new JwtTokenEntity(tokenId, userEntity, token, expirationDate);
        }
    }

}
