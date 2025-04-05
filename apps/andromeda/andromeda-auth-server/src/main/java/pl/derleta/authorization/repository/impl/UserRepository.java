package pl.derleta.authorization.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.derleta.authorization.domain.entity.UserEntity;
import pl.derleta.authorization.domain.model.User;
import pl.derleta.authorization.repository.RepositoryClass;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Responsible for interacting with the `andromeda.users` table in the database to perform CRUD operations.
 * Provides methods for accessing and manipulating user data, such as retrieving paginated user lists,
 * checking user status, and applying filters to user queries.
 */
@Repository
public class UserRepository implements RepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new instance of UserRepository.
     * Initializes the JdbcTemplate with the provided DataSource to allow interaction with the database.
     *
     * @param dataSource the DataSource object used to configure the database connection
     */
    @Autowired
    public UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total number of user records in the database.
     *
     * @return the total count of users as an Integer
     */
    public Integer getSize() {
        String sql = """
                SELECT COUNT(*) FROM andromeda.users;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Retrieves a paginated list of UserEntity records from the database.
     * The method fetches a specific number of records starting from a given offset.
     *
     * @param offset the starting point for pagination, indicating the number of records to skip
     * @param size   the maximum number of records to retrieve in the result set
     * @return a list of UserEntity objects representing the retrieved records
     */
    public List<UserEntity> getPage(final int offset, final int size) {
        String sql = """ 
                SELECT user_id, username, password, email
                FROM andromeda.users
                LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new UserMapper(), offset, size);
    }

    /**
     * Retrieves a UserEntity from the database based on the provided user ID.
     *
     * @param userId the unique identifier of the user to be retrieved
     * @return a UserEntity object representing the user with the specified ID, or null if no user is found
     */
    public UserEntity findById(final long userId) {
        String sql = """
                  SELECT user_id, username, password, email
                  FROM andromeda.users
                  WHERE user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new UserMapper(), userId);
    }

    /**
     * Retrieves a UserEntity from the database based on the provided email address.
     *
     * @param email the email address of the user to be retrieved
     * @return a UserEntity object representing the user with the specified email, or null if no user is found
     */
    public UserEntity findByEmail(String email) {
        String sql = """
                  SELECT user_id, username, password, email
                  FROM andromeda.users
                  WHERE email = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new UserMapper(), email);
    }

    /**
     * Checks whether the user with the specified user ID is blocked.
     *
     * @param userId the unique identifier of the user whose blocked status is to be checked
     * @return true if the user is blocked, false otherwise
     */
    public Boolean isBlocked(final long userId) {
        String sql = """
                  SELECT blocked
                  FROM andromeda.users
                  WHERE user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
    }

    /**
     * Checks if the user with the specified userId is verified.
     *
     * @param userId the unique identifier of the user whose verification status is to be checked
     * @return true if the user is verified, false otherwise
     */
    public Boolean isVerified(final long userId) {
        String sql = """
                  SELECT verified
                  FROM andromeda.users
                  WHERE user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
    }

    /**
     * Saves a new user to the database. The user's details, such as username, password, and email,
     * are stored along with automatic timestamps for creation and update. The user is initialized
     * with default values for `verified` (false) and `blocked` (false).
     *
     * @param userId the unique identifier for the user to be saved
     * @param user   the user's details including username, password, and email
     */
    public void save(final long userId, final User user) {
        String sql = """ 
                    INSERT INTO andromeda.users (user_id, username, password, email, created_at, updated_at, verified, blocked)
                    VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, false);
                """;
        jdbcTemplate.update(sql,
                userId,
                user.username(),
                user.password(),
                user.email());
    }

    /**
     * Retrieves the next available user ID by fetching the maximum user_id from
     * the andromeda.users table and incrementing it by one.
     *
     * @return the next available user ID as a Long, or null if no users exist in the table
     */
    public Long getNextUserId() {
        String idSql = "SELECT MAX(user_id) + 1 FROM andromeda.users";
        return jdbcTemplate.queryForObject(idSql, Long.class);
    }

    /**
     * Updates an existing user in the database with new details.
     * The method updates the username, email, password,
     * and sets the updated_at timestamp to the current time.
     *
     * @param userId the unique identifier of the user to be updated
     * @param user   the new details for the user, including username, email, and password
     */
    public void update(final long userId, final User user) {
        String sql = """
                  UPDATE andromeda.users
                  SET username = ?, email = ?,
                  password = ?, updated_at = CURRENT_TIMESTAMP
                  WHERE user_id = ?;
                """;
        jdbcTemplate.update(sql,
                user.username(),
                user.email(),
                user.password(),
                userId);
    }

    /**
     * Deletes a user from the database based on the provided user ID.
     *
     * @param userId the unique identifier of the user to be deleted
     */
    public void deleteById(final long userId) {
        String sql = """ 
                    DELETE FROM andromeda.users
                    WHERE user_id = ?;
                """;
        jdbcTemplate.update(sql, userId);
    }

    /**
     * Retrieves a paginated and sorted list of UserEntity records from the database
     * based on the provided filters for username and email. The method applies the
     * specified sorting parameters and uses SQL LIKE operators for partial matching
     * of the filters.
     *
     * @param offset         the starting point for pagination, indicating the number of records to skip
     * @param size           the maximum number of records to retrieve in the result set
     * @param sortByParam    the column name by which the result set should be sorted
     * @param sortOrderParam the sorting order, either "ASC" for ascending or "DESC" for descending
     * @param username       the username filter to apply; supports partial matching with SQL LIKE
     * @param email          the email filter to apply; supports partial matching with SQL LIKE
     * @return a list of UserEntity objects that match the specified filters, sorted and limited by pagination parameters
     */
    public List<UserEntity> getSortedPageWithFilters(final int offset, final int size, final String sortByParam, final String sortOrderParam, final String username, final String email) {
        String sql = """ 
                SELECT user_id, username, password, email
                FROM andromeda.users
                WHERE username LIKE ?
                AND email LIKE ?
                ORDER BY ?, ?
                LIMIT ?, ?;
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        return jdbcTemplate.query(sql, new UserMapper(), usernameParam, emailParam,
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT),
                offset, size);
    }

    /**
     * Retrieves the count of user records from the database that match the specified
     * username and email filters. The method performs a case-insensitive search
     * using SQL LIKE operators on both the username and email fields.
     *
     * @param username the username filter to match records; supports partial matching
     * @param email    the email filter to match records; supports partial matching
     * @return the count of records that match the specified filters
     */
    public Long getFiltersCount(final String username, final String email) {
        String sql = """ 
                SELECT COUNT(*)
                FROM andromeda.users
                WHERE username LIKE ?
                AND email LIKE ?
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        return jdbcTemplate.queryForObject(sql, Long.class, usernameParam, emailParam);
    }

    /**
     * Checks if the provided email exists in the database by querying the andromeda.users table.
     *
     * @param email the email address to be checked for existence
     * @return true if the email exists, false otherwise
     */
    public boolean isEmailExist(final String email) {
        String sql = """
                SELECT COUNT(*)
                FROM andromeda.users
                WHERE email LIKE ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        if (count != null) {
            return count > 0;
        }
        return false;
    }

    /**
     * Checks if a given login exists in the database by querying the andromeda.users table.
     *
     * @param login the username to be checked for existence in the database
     * @return true if the login exists, false otherwise
     */
    public boolean isLoginExist(final String login) {
        String sql = """
                SELECT COUNT(*)
                FROM andromeda.users
                WHERE users.username LIKE ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        if (count != null) {
            return count > 0;
        }
        return false;
    }

    /**
     * Unlocks a user by updating their status in the database.
     * This method sets the `verified` column to true, `blocked` column to false,
     * and updates the `updated_at` timestamp to the current time.
     *
     * @param userId the unique identifier of the user to be unlocked
     */
    public void unlock(final long userId) {
        String sql = """
                UPDATE andromeda.users
                SET verified = true, blocked = false, updated_at = CURRENT_TIMESTAMP
                WHERE user_id = ?;
                """;
        jdbcTemplate.update(sql, userId);
    }

    /**
     * Updates the status of a user in the database identified by the specified userId.
     * This method updates the `verified` and `blocked` columns for the user and
     * sets the `updated_at` timestamp to the current time.
     *
     * @param userId   the unique identifier of the user whose status is to be updated
     * @param verified the new verified status to be set for the user
     * @param blocked  the new blocked status to be set for the user
     */
    public void updateStatus(final long userId, final boolean verified, final boolean blocked) {
        String sql = """
                UPDATE andromeda.users
                SET verified = ?, blocked = ?, updated_at = CURRENT_TIMESTAMP
                WHERE user_id = ?;
                """;
        jdbcTemplate.update(sql, verified, blocked, userId);
    }

    /**
     * Updates the password of a user identified by userId in the database.
     * This method sets the password to the specified encryptedPassword and updates the updated_at timestamp.
     *
     * @param userId            the unique identifier of the user whose password is to be updated
     * @param encryptedPassword the new encrypted password to be set for the user
     */
    public void updatePassword(final long userId, final String encryptedPassword) {
        String sql = """
                UPDATE andromeda.users
                SET password = ?, updated_at = CURRENT_TIMESTAMP
                WHERE user_id = ?;
                """;
        jdbcTemplate.update(sql, encryptedPassword, userId);
    }

    /**
     * The UserMapper class is a private static implementation of the RowMapper interface.
     * It is designed to map rows of a ResultSet to instances of the UserEntity class.
     * <p>
     * This class is responsible for reading the fields `user_id`, `username`, `email`,
     * and `password` from a ResultSet and constructing a UserEntity object corresponding
     * to the data in each row.
     * <p>
     * It is used internally in database query operations to translate database records
     * into UserEntity objects.
     */
    private static class UserMapper implements RowMapper<UserEntity> {
        @Override
        public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {

            long userId = resultSet.getLong("user_id");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");

            return new UserEntity(userId, username, email, password);
        }
    }

}
