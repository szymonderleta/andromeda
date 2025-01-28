package pl.derleta.authorization.config.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repository class for managing operations on JWT tokens in the database.
 * This class interacts with the database using JdbcTemplate to perform CRUD operations
 * related to JWT token data.
 */
@Repository
public class AuthApiRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthApiRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the next available unique identifier for a token.
     * The method executes a query to calculate the maximum token_id from the jwt_tokens table,
     * increments it by one, and returns it. If no tokens exist, it defaults to 1.
     *
     * @return the next available unique identifier for the token as a {@code Long}.
     */
    public Long getNextId() {
        String idSql = "SELECT COALESCE(MAX(token_id) + 1, 1) FROM andromeda.jwt_tokens";
        return jdbcTemplate.queryForObject(idSql, Long.class);
    }

    /**
     * Retrieves a JWT token value from the database by its token ID.
     * Executes a SQL query to fetch the token associated with the provided ID.
     *
     * @param tokenId the unique identifier of the JWT token record in the database
     * @return the JWT token as a {@code String}, or {@code null} if no record is found
     */
    public String findById(final long tokenId) {
        String sql = """
                SELECT jt.token
                FROM andromeda.jwt_tokens jt
                WHERE token_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new JwtTokenMapper(), tokenId);
    }

    /**
     * Saves a JWT token into the database with an associated user ID and token ID.
     * This method inserts the token data into the `andromeda.jwt_tokens` table
     * along with an expiration date set to one day from the current timestamp.
     *
     * @param tokenId the unique identifier for the token
     * @param userId the unique identifier of the user associated with the token
     * @param token the JWT token to be saved in the database
     */
    public void save(final long tokenId, final long userId, final String token) {
        System.out.println(token.length());
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
     * Implementation of the {@link RowMapper} interface to map a single column value
     * from a database result set to a {@code String}.
     * <p>
     * This class is specifically used to extract the "token" column from the result set
     * of a query on the `andromeda.jwt_tokens` table. It is designed to be a reusable
     * mapper for mapping the "jt.token" column to a string value.
     * <p>
     * Usage typically occurs within the context of DAO or repository methods, where
     * the result set of a query needs to be mapped to a single string value.
     */
    private static class JwtTokenMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString("jt.token");
        }
    }

}
