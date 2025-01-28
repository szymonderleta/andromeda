package pl.derleta.authorization.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.derleta.authorization.domain.entity.RoleEntity;
import pl.derleta.authorization.domain.model.Role;
import pl.derleta.authorization.repository.RepositoryClass;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * RoleRepository is responsible for managing CRUD operations and queries related to roles in the "roles" table.
 * It provides methods to interact with the database, including retrieving, saving, updating,
 * and deleting role entities, as well as applying filters and pagination. The interactions
 * with the database are facilitated using JdbcTemplate.
 */
@Repository
public class RoleRepository implements RepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Initializes the RoleRepository with a given DataSource.
     * Creates a JdbcTemplate instance using the provided DataSource to enable interaction with the database.
     *
     * @param dataSource the DataSource object used to configure the JdbcTemplate for database operations
     */
    @Autowired
    public RoleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Retrieves the total number of roles in the "roles" table.
     *
     * @return the total count of roles as an Integer.
     */
    public Integer getSize() {
        String sql = """
                SELECT COUNT(*) FROM andromeda.roles;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Retrieves a paginated list of roles from the "roles" table.
     *
     * @param offset the starting position of the records to retrieve
     * @param size   the maximum number of records to retrieve
     * @return a list of RoleEntity objects representing the retrieved roles
     */
    public List<RoleEntity> getPage(final int offset, final int size) {
        String sql = """ 
                SELECT role_id, role_name
                FROM andromeda.roles
                LIMIT ?, ?;
                """;
        return jdbcTemplate.query(sql, new RoleMapper(), offset, size);
    }

    /**
     * Retrieves a list of roles from the "roles" table that match the specified role name filter.
     *
     * @param roleName the partial role name to filter by; supports partial matching using "LIKE".
     * @return a list of RoleEntity objects matching the specified role name filter.
     */
    public List<RoleEntity> findAll(final String roleName) {
        String sql = """
                    SELECT role_id, role_name
                    FROM andromeda.roles
                    WHERE role_name LIKE ?;
                """;
        String roleNameParam = "%" + roleName + "%";
        return jdbcTemplate.query(sql, new RoleMapper(), roleNameParam);
    }

    /**
     * Retrieves a role entity by its unique role ID from the "roles" table.
     *
     * @param roleId the ID of the role to retrieve
     * @return the RoleEntity object corresponding to the specified role ID
     */
    public RoleEntity findById(final int roleId) {
        String sql = """
                  SELECT role_id, role_name
                  FROM andromeda.roles
                  WHERE role_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new RoleMapper(), roleId);
    }

    /**
     * Saves a role with the specified role ID and role details into the "roles" table.
     *
     * @param role_id the ID of the role to be saved
     * @param role    the Role object containing the role details to be saved
     */
    public void save(final int role_id, final Role role) {
        String sql = """ 
                    INSERT INTO andromeda.roles (role_id, role_name)
                    VALUES (?, ?);
                """;
        jdbcTemplate.update(sql,
                role_id,
                role.roleName());
    }

    /**
     * Retrieves the next available role ID by finding the maximum role ID in the "roles" table
     * and incrementing it by 1.
     *
     * @return the next available role ID as an Integer; if the "roles" table is empty, it may return null
     */
    public Integer getNextRoleId() {
        String idSql = "SELECT MAX(role_id) + 1 FROM andromeda.roles";
        return jdbcTemplate.queryForObject(idSql, Integer.class);
    }

    /**
     * Updates the role record in the "roles" table based on the provided role ID.
     *
     * @param roleId the ID of the role to update
     * @param role   the role object containing the updated role details
     */
    public void update(final int roleId, final Role role) {
        String sql = """
                  UPDATE andromeda.roles SET
                  role_name = ?
                  WHERE role_id = ?;
                """;
        jdbcTemplate.update(sql,
                role.roleName(),
                roleId);
    }

    /**
     * Deletes a role record from the "roles" table based on the provided role ID.
     *
     * @param roleId the ID of the role to be deleted
     */
    public void deleteById(final int roleId) {
        String sql = """ 
                    DELETE FROM andromeda.roles
                    WHERE role_id = ?;
                """;
        jdbcTemplate.update(sql, roleId);
    }

    /**
     * Retrieves a sorted page of roles based on the provided filters and pagination parameters.
     *
     * @param offset         the starting position of the records to retrieve
     * @param size           the maximum number of records to retrieve
     * @param sortByParam    the column name to sort the results by
     * @param sortOrderParam the sorting order, either "ASC" for ascending or "DESC" for descending
     * @param roleName       a filter for role names; supports partial matching
     * @return a set of RoleEntity objects representing the filtered and sorted roles
     */
    public Set<RoleEntity> getSortedPageWithFilters(final int offset, final int size, final String sortByParam, final String sortOrderParam, final String roleName) {
        String sql = """ 
                SELECT role_id, role_name
                FROM andromeda.roles
                WHERE role_name LIKE ?
                ORDER BY ?, ?
                LIMIT ?, ?;
                """;

        String roleNameParam = "%" + roleName + "%";
        List<RoleEntity> roles = jdbcTemplate.query(sql, new RoleMapper(), roleNameParam,
                sortByParam, sortOrderParam.toUpperCase(Locale.ROOT),
                offset, size);
        return new HashSet<>(roles);
    }

    /**
     * Retrieves the count of roles that match the provided role name filter.
     *
     * @param roleName the role name filter to search for; supports partial matching
     * @return the count of roles that match the specified filter
     */
    public Integer getFiltersCount(final String roleName) {
        String sql = """ 
                SELECT COUNT(*)
                FROM andromeda.roles
                WHERE role_name LIKE ?
                """;
        String roleNameParam = "%" + roleName + "%";
        return jdbcTemplate.queryForObject(sql, Integer.class, roleNameParam);
    }

    /**
     * A private static class used for mapping rows of a ResultSet to instances of {@link RoleEntity}.
     * <p>
     * This class implements the {@link RowMapper} interface and is responsible
     * for extracting data from a single row of a ResultSet and mapping it to a
     * {@link RoleEntity} object. The mapping is achieved through the method
     * {@code mapRow}, which retrieves the `role_id` and `role_name` columns
     * from the given ResultSet.
     */
    private static class RoleMapper implements RowMapper<RoleEntity> {
        @Override
        public RoleEntity mapRow(ResultSet resultSet, int i) throws SQLException {
            int roleId = resultSet.getInt("role_id");
            String roleName = resultSet.getString("role_name");
            return new RoleEntity(roleId, roleName);
        }
    }

}
