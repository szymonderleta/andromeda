package pl.derleta.authorization.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.derleta.authorization.domain.entity.RoleEntity;
import pl.derleta.authorization.domain.entity.UserEntity;
import pl.derleta.authorization.domain.entity.UserRoleEntity;
import pl.derleta.authorization.repository.RepositoryClass;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Repository class responsible for managing database operations related
 * to user-role mappings in the "andromeda" schema. This includes fetching,
 * saving, deleting, and filtering user-role entries, as well as retrieving
 * related user and role details.
 * <p>
 * The class uses Spring's {@link JdbcTemplate} to perform SQL queries and updates.
 * It operates on the "user_roles" table and integrates with the "users" and "roles"
 * tables to fetch complete user-role information.
 */
@Repository
public class UserRoleRepository implements RepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs an instance of UserRoleRepository and initializes the JdbcTemplate
     * with the provided DataSource for database operations.
     *
     * @param dataSource the DataSource to be used for initializing the JdbcTemplate
     */
    @Autowired
    public UserRoleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total count of user-role mappings from the database.
     *
     * @return the total number of user-role mappings as an Integer
     */
    public Integer getSize() {
        String sql = """
                SELECT COUNT(*) FROM andromeda.user_roles;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Retrieves a paginated list of user-role mappings from the database.
     * This method fetches user-role records along with associated user and role details
     * and maps the result to a list of {@link UserRoleEntity} objects.
     *
     * @param offset the starting position for the records to retrieve
     * @param size   the number of records to retrieve
     * @return a list of {@link UserRoleEntity} objects containing user, role, and user-role details
     */
    public List<UserRoleEntity> getPage(final int offset, final int size) {
        String sql = """ 
                SELECT ur.user_role_id, u.*, r.*
                FROM andromeda.users u
                JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                JOIN andromeda.roles r ON ur.role_id = r.role_id
                LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new UserRoleMapper(), offset, size);
    }

    /**
     * Retrieves a UserRoleEntity based on the specified user ID and role ID.
     * This method fetches a user-role mapping record from the database,
     * along with associated user and role details, and maps the result
     * to a UserRoleEntity object.
     *
     * @param userId the unique identifier for the user
     * @param roleId the unique identifier for the role
     * @return a UserRoleEntity object containing user, role, and user-role details;
     * throws an exception if no matching record is found
     */
    public UserRoleEntity findByIds(final long userId, final int roleId) {
        String sql = """
                  SELECT ur.user_role_id, u.*, r.*
                  FROM andromeda.users u
                  JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                  JOIN andromeda.roles r ON ur.role_id = r.role_id
                  WHERE u.user_id = ?
                  AND r.role_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new UserRoleMapper(), userId, roleId);
    }

    /**
     * Retrieves a UserRoleEntity based on the specified user role ID.
     * This method fetches a user role record from the database, along with associated
     * user and role details, and maps the result to a UserRoleEntity object.
     *
     * @param userRoleId the unique identifier for the user-role mapping to be retrieved
     * @return a UserRoleEntity object containing user, role, and user-role details;
     * or throws an exception if no matching record is found
     */
    public UserRoleEntity findById(final long userRoleId) {
        String sql = """
                  SELECT ur.user_role_id, u.*, r.*
                  FROM andromeda.users u
                  JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                  JOIN andromeda.roles r ON ur.role_id = r.role_id
                  WHERE user_role_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new UserRoleMapper(), userRoleId);
    }

    /**
     * Persists a mapping of user roles into the database.
     *
     * @param userRoleId the unique identifier for the user-role mapping
     * @param userId     the identifier of the user
     * @param roleId     the identifier of the role
     */
    public void save(final long userRoleId, final long userId, final int roleId) {
        String sql = """ 
                    INSERT INTO andromeda.user_roles (user_role_id, user_id, role_id)
                    VALUES (?, ?, ?);
                """;
        jdbcTemplate.update(sql,
                userRoleId,
                userId,
                roleId);
    }

    /**
     * Retrieves the next available unique identifier for the user_roles table by querying the maximum
     * user_role_id and incrementing it by 1.
     *
     * @return the next unique user_role_id as a Long, or null if no records exist in the user_roles table
     */
    public Long getNextId() {
        String idSql = "SELECT MAX(user_role_id) + 1 FROM andromeda.user_roles";
        return jdbcTemplate.queryForObject(idSql, Long.class);
    }

    /**
     * Deletes a user-role mapping from the database for the specified user and role IDs.
     *
     * @param userId the ID of the user whose user-role mapping is to be deleted
     * @param roleId the ID of the role whose user-role mapping is to be deleted
     */
    public void deleteById(final long userId, final int roleId) {
        String sql = """ 
                    DELETE FROM andromeda.user_roles
                    WHERE user_id = ? AND role_id = ?;
                """;
        jdbcTemplate.update(sql, userId, roleId);
    }

    /**
     * Retrieves a paginated and sorted list of user-role mappings with the specified filters.
     * The method applies filters for username, email, and role name, and sorts the results
     * based on the given sorting parameters, with pagination support.
     *
     * @param offset         the starting position of the page to retrieve
     * @param size           the number of items to retrieve per page
     * @param sortByParam    the column by which the results will be sorted
     * @param sortOrderParam the sorting order, either "ASC" or "DESC"
     * @param username       the username filter condition, a string to match (supports partial matching with wildcards)
     * @param email          the email filter condition, a string to match (supports partial matching with wildcards)
     * @param roleName       the role name filter condition, a string to match (supports partial matching with wildcards)
     * @return a list of UserRoleEntity objects matching the specified filters, sorted and paginated accordingly
     */
    public List<UserRoleEntity> getSortedPageWithFilters(final int offset, final int size, final String sortByParam, final String sortOrderParam, final String username, final String email, final String roleName) {
        String sql = """ 
                SELECT ur.user_role_id, u.*, r.*
                FROM andromeda.users u
                JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                JOIN andromeda.roles r ON ur.role_id = r.role_id
                WHERE u.username LIKE ?
                AND u.email LIKE ?
                AND r.role_name LIKE ?
                ORDER BY ?, ?
                LIMIT ?, ?;
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        String roleNameParam = "%" + roleName + "%";
        return jdbcTemplate.query(sql, new UserRoleMapper(),
                usernameParam, emailParam, roleNameParam, // search params
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT), // order param
                offset, size); // page, size
    }

    /**
     * Retrieves the count of user-role entries that match the given filters: username, email, and role name.
     *
     * @param username the username filter condition, a string to be matched (supports partial matching using wildcards)
     * @param email    the email filter condition, a string to be matched (supports partial matching using wildcards)
     * @param roleName the role name filter condition, a string to be matched (supports partial matching using wildcards)
     * @return the count of user-role entries that satisfy the specified filters, as a Long
     */
    public Long getFiltersCount(final String username, final String email, final String roleName) {
        String sql = """ 
                SELECT COUNT(*)
                FROM andromeda.users u
                JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                JOIN andromeda.roles r ON ur.role_id = r.role_id
                WHERE u.username LIKE ?
                AND u.email LIKE ?
                AND r.role_name LIKE ?
                """;
        String usernameParam = "%" + username + "%";
        String emailParam = "%" + email + "%";
        String roleNameParam = "%" + roleName + "%";
        return jdbcTemplate.queryForObject(sql, Long.class, usernameParam, emailParam, roleNameParam);
    }

    /**
     * Maps rows of a {@link ResultSet} to a {@link UserRoleEntity}.
     * It extracts user role details, user information, and role information
     * from the result set and constructs a {@link UserRoleEntity} object.
     * <p>
     * This mapper is typically used in conjunction with Spring's JDBC Template
     * for database operations that query for user roles and their associated
     * user and role entities.
     * <p>
     * Implements the {@link RowMapper} interface to define the custom mapping logic.
     * <p>
     * The mapping includes:
     * - user role ID
     * - user details (user ID, username, email, password)
     * - role details (role ID, role name)
     * <p>
     * Throws {@link SQLException} if an SQL error occurs while accessing the result set.
     */
    private static class UserRoleMapper implements RowMapper<UserRoleEntity> {
        @Override
        public UserRoleEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            long userRoleId = resultSet.getLong("ur.user_role_id");

            long userId = resultSet.getLong("u.user_id");
            String username = resultSet.getString("u.username");
            String email = resultSet.getString("u.email");
            String password = resultSet.getString("u.password");
            UserEntity userEntity = new UserEntity(userId, username, email, password);

            int roleId = resultSet.getInt("r.role_id");
            String roleName = resultSet.getString("r.role_name");
            RoleEntity roleEntity = new RoleEntity(roleId, roleName);

            return new UserRoleEntity(userRoleId, userEntity, roleEntity);
        }
    }

}
