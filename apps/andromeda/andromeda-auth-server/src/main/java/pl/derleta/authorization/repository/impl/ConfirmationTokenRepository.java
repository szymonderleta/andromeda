package pl.derleta.authorization.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.derleta.authorization.domain.entity.ConfirmationTokenEntity;
import pl.derleta.authorization.domain.entity.UserEntity;
import pl.derleta.authorization.repository.RepositoryClass;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * Repository class for managing CRUD operations and other database interactions
 * related to confirmation tokens and their associated user information in the "andromeda" schema.
 * This repository uses JdbcTemplate for querying and updating the database.
 */
@Repository
public class ConfirmationTokenRepository implements RepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new instance of the ConfirmationTokenRepository with the specified DataSource.
     * Initializes the JdbcTemplate for interacting with the database.
     *
     * @param dataSource the DataSource object used to configure the JdbcTemplate for database operations
     */
    @Autowired
    public ConfirmationTokenRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total count of confirmation tokens in the database.
     *
     * @return the total number of confirmation tokens as an Integer.
     */
    public Integer getSize() {
        String sql = """
                SELECT COUNT(*) FROM andromeda.confirmation_tokens;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Retrieves a paginated list of confirmation tokens along with associated user details.
     *
     * @param offset the starting position for retrieving records from the database, used for pagination
     * @param size   the number of records to retrieve, defining the page size
     * @return a list of {@link ConfirmationTokenEntity} objects representing confirmation tokens and their associated users
     */
    public List<ConfirmationTokenEntity> getPage(final int offset, final int size) {
        String sql = """ 
                SELECT u.*, ct.*
                FROM andromeda.users u
                JOIN andromeda.confirmation_tokens ct ON u.user_id = ct.user_id
                LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new ConfirmationTokenMapper(), offset, size);
    }

    /**
     * Retrieves a paginated and sorted list of valid confirmation tokens associated with users.
     * A valid confirmation token is one whose expiration date is greater than the current timestamp.
     *
     * @param offset         the starting position for the records to fetch, used for pagination
     * @param size           the number of records to fetch, defining the page size
     * @param sortByParam    the database column name by which the results should be sorted (e.g., username, email)
     * @param sortOrderParam the sorting order, either "ASC" for ascending or "DESC" for descending
     * @return a list of {@link ConfirmationTokenEntity} objects representing valid confirmation tokens
     */
    public List<ConfirmationTokenEntity> findValid(final int offset, final int size, final String sortByParam, final String sortOrderParam) {
        String sql = """
                    SELECT u.*, ct.*
                    FROM andromeda.users u
                    JOIN andromeda.confirmation_tokens ct ON u.user_id = ct.user_id
                    WHERE expiration_date > NOW()
                    ORDER BY ?, ?
                    LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new ConfirmationTokenMapper(),
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT),
                offset, size);
    }

    /**
     * Retrieves a confirmation token entity from the database based on its unique identifier.
     *
     * @param tokenId the unique identifier of the confirmation token to be retrieved
     * @return the {@link ConfirmationTokenEntity} associated with the given token ID
     */
    public ConfirmationTokenEntity findById(final long tokenId) {
        String sql = """
                  SELECT u.*, ct.*
                  FROM andromeda.users u
                  JOIN andromeda.confirmation_tokens ct ON u.user_id = ct.user_id
                  WHERE token_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new ConfirmationTokenMapper(), tokenId);
    }

    /**
     * Saves a confirmation token in the database with an expiration time of 1 hour.
     *
     * @param tokenId the unique identifier of the token to be saved
     * @param userId  the unique identifier of the user associated with the token
     * @param token   the string representation of the confirmation token
     */
    public void save(final long tokenId, final long userId, final String token) {
        String sql = """ 
                    INSERT INTO andromeda.confirmation_tokens (token_id, user_id, token, expiration_date)
                    VALUES (?, ?, ?, NOW() + INTERVAL 1 HOUR );
                """;
        jdbcTemplate.update(sql,
                tokenId,
                userId,
                token);
    }

    /**
     * Retrieves the next available unique identifier for confirmation tokens
     * by calculating the maximum token ID in the database and incrementing it by 1.
     *
     * @return the next unique token ID as a Long, or null if the query result is empty.
     */
    public Long getNextId() {
        String idSql = "SELECT MAX(token_id) + 1 FROM andromeda.confirmation_tokens";
        return jdbcTemplate.queryForObject(idSql, Long.class);
    }

    /**
     * Deletes a confirmation token record from the database based on the given token ID and user ID.
     *
     * @param tokenId the unique identifier of the confirmation token to be deleted
     * @param userId  the unique identifier of the user associated with the confirmation token
     */
    public void deleteById(final long tokenId, final long userId) {
        String sql = """ 
                    DELETE FROM andromeda.confirmation_tokens
                    WHERE token_id = ? AND user_id = ?;
                """;
        jdbcTemplate.update(sql, tokenId, userId);
    }

    /**
     * Retrieves a paginated and sorted list of confirmation tokens associated with users,
     * filtered by username and email.
     *
     * @param offset         the starting point for the records to fetch, used for pagination.
     * @param size           the number of records to fetch, defining the page size.
     * @param sortByParam    the database column name to sort the results by (e.g., username, email).
     * @param sortOrderParam the sorting order, either "ASC" for ascending or "DESC" for descending.
     * @param username       the username filter to apply, supports partial matches using wildcard patterns.
     * @param email          the email filter to apply, supports partial matches using wildcard patterns.
     * @return a list of {@link ConfirmationTokenEntity} objects that satisfy the provided filters and sorting criteria.
     */
    public List<ConfirmationTokenEntity> getSortedPageWithFilters(final int offset, final int size, final String sortByParam, final String sortOrderParam, final String username, final String email) {
        String sql = """ 
                SELECT u.*, ct.*
                FROM andromeda.users u
                JOIN andromeda.confirmation_tokens ct ON u.user_id = ct.user_id
                WHERE u.username LIKE ?
                AND u.email LIKE ?
                ORDER BY ?, ?
                LIMIT ?, ?;
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        return jdbcTemplate.query(sql, new ConfirmationTokenMapper(),
                usernameParam, emailParam,
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT),
                offset, size);
    }

    /**
     * Retrieves the count of users whose usernames and emails match the given filters.
     *
     * @param username the username filter to be matched, supports partial matching.
     * @param email    the email filter to be matched, supports partial matching.
     * @return the count of users matching the provided filters as a Long.
     */
    public Long getFiltersCount(final String username, final String email) {
        String sql = """ 
                SELECT COUNT(*)
                FROM andromeda.users u
                JOIN andromeda.confirmation_tokens ct ON u.user_id = ct.user_id
                WHERE u.username LIKE ?
                AND u.email LIKE ?;
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        return jdbcTemplate.queryForObject(sql, Long.class, usernameParam, emailParam);
    }

    /**
     * Retrieves the count of confirmation tokens that are still valid, i.e.,
     * those whose expiration date is greater than the current timestamp.
     *
     * @return the count of valid confirmation tokens as a Long.
     */
    public Long getValidCount() {
        String sql = """
                    SELECT COUNT(*)
                    FROM andromeda.confirmation_tokens ct
                    WHERE expiration_date > NOW();
                """;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    /**
     * Sets the expiration date of the specified confirmation token to the current timestamp.
     *
     * @param tokenEntity The {@link ConfirmationTokenEntity} object containing the token ID of the confirmation token to be updated.
     */
    public void setExpired(ConfirmationTokenEntity tokenEntity) {
        String sql = """
                UPDATE andromeda.confirmation_tokens
                SET expiration_date = NOW()
                WHERE token_id = ?;
                """;
        jdbcTemplate.update(sql, tokenEntity.getTokenId());
    }

    /**
     * The ConfirmationTokenMapper is a private static class that implements the RowMapper interface
     * for mapping rows of a relational database table into instances of the ConfirmationTokenEntity class.
     * <p>
     * This class is primarily used to convert a ResultSet obtained from executing a query
     * into a corresponding ConfirmationTokenEntity object. The mapping process includes the extraction
     * of user-specific information (from fields such as user_id, username, email, password) and
     * confirmation token-related information (token_id, token, and expiration_date) from the ResultSet.
     * <p>
     * Each row in the ResultSet is expected to contain data from two primary sources:
     * - User entity fields: Represented by the UserEntity class and outlined by the table prefix "u".
     * - Confirmation token fields: Represented by the ConfirmationTokenEntity class and outlined by the table prefix "ct".
     * <p>
     * The mapRow method reads these fields, constructs UserEntity and ConfirmationTokenEntity objects,
     * and returns the completed ConfirmationTokenEntity instance.
     */
    private static class ConfirmationTokenMapper implements RowMapper<ConfirmationTokenEntity> {
        @Override
        public ConfirmationTokenEntity mapRow(ResultSet resultSet, int i) throws SQLException {

            long userId = resultSet.getLong("u.user_id");
            String username = resultSet.getString("u.username");
            String email = resultSet.getString("u.email");
            String password = resultSet.getString("u.password");
            UserEntity userEntity = new UserEntity(userId, username, email, password);

            long tokenId = resultSet.getLong("token_id");
            String token = resultSet.getString("ct.token");
            Timestamp expirationDate = resultSet.getTimestamp("ct.expiration_date");

            return new ConfirmationTokenEntity(tokenId, userEntity, token, expirationDate);
        }
    }

}
