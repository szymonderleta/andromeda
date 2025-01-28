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
 * UserRolesRepository is a Spring repository implementation used to manage user role data.
 * This class provides methods to retrieve user roles based on various filters and criteria.
 * It utilizes JdbcTemplate for executing SQL queries and dynamically maps result sets
 * to corresponding entities using a custom RowMapper.
 */
@Repository
public class UserRolesRepository implements RepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new instance of the UserRolesRepository using the provided data source.
     * Initializes a JdbcTemplate for executing database queries.
     *
     * @param dataSource the DataSource to be used for initializing the JdbcTemplate
     */
    @Autowired
    public UserRolesRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves a list of user roles associated with the provided user ID and optional filters.
     * This method queries the database to fetch user roles that match the specified criteria.
     *
     * @param userId         the ID of the user whose roles are to be retrieved
     * @param sortByParam    the column by which the result should be sorted
     * @param sortOrderParam the order of sorting, either "ASC" or "DESC"
     * @param roleName       the role name filter; partial matches are supported
     * @return a list of {@link UserRoleEntity} objects that match the given parameters
     */
    public List<UserRoleEntity> get(final long userId, final String sortByParam, final String sortOrderParam, final String roleName) {
        String sql = """ 
                SELECT ur.user_role_id, u.*, r.*
                FROM andromeda.users u
                JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                JOIN andromeda.roles r ON ur.role_id = r.role_id
                WHERE u.user_id = ?
                AND r.role_name LIKE ?
                ORDER BY ?, ?;
                """;
        String roleNameParam = "%" + roleName + "%";
        return jdbcTemplate.query(sql, new UserRolesMapper(),
                userId, roleNameParam,
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT)
        );
    }

    /**
     * Retrieves a list of user roles associated with the provided username and email.
     * The method performs a database query to fetch user roles from the `user_roles` table
     * matched with the corresponding user and role details.
     *
     * @param username the username to filter the user roles by
     * @param email    the email to filter the user roles by
     * @return a list of {@link UserRoleEntity} objects that match the specified username and email
     */
    public List<UserRoleEntity> get(final String username, final String email) {
        String sql = """ 
                SELECT ur.user_role_id, u.*, r.*
                FROM andromeda.users u
                JOIN andromeda.user_roles ur ON u.user_id = ur.user_id
                JOIN andromeda.roles r ON ur.role_id = r.role_id
                WHERE u.username = ?
                AND u.email = ?;
                """;
        return jdbcTemplate.query(sql, new UserRolesMapper(), username, email);
    }


    /**
     * A RowMapper implementation for mapping rows of a ResultSet to instances of UserRoleEntity.
     * This class is used to map the result of a SQL query into the appropriate objects, including
     * UserEntity and RoleEntity, and then collates these objects into a UserRoleEntity.
     * <p>
     * The mapping is handled as follows:
     * - Extracts user_role_id, user information (user_id, username, email, password), and role
     * information (role_id, role_name) from the ResultSet.
     * - Creates a UserEntity object using the user details.
     * - Creates a RoleEntity object using the role details.
     * - Combines these into a UserRoleEntity object.
     * <p>
     * This class is designed to be used with JDBC's JdbcTemplate for executing queries
     * and mapping result sets.
     */
    private static class UserRolesMapper implements RowMapper<UserRoleEntity> {
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
