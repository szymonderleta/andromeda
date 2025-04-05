package pl.derleta.nebula.repository.filter;

import org.springframework.data.jpa.domain.Specification;
import pl.derleta.nebula.domain.entity.GameEntity;

/**
 * A utility class providing specifications for querying {@link GameEntity}
 * objects based on various criteria.
 * <p>
 * This class is designed to be used with Spring Data JPA's {@link Specification}
 * API to create predicates for filtering {@link GameEntity} instances.
 * <p>
 * The specifications in this class allow filtering by:
 * - Game name (partial matches allowed using "LIKE").
 * - Enable status (exact match).
 * - Combination of both filters.
 * <p>
 * All methods in this class are static and immutable.
 */
public final class GamesSpecifications {

    /**
     * Creates a specification to filter {@link GameEntity} objects based on both "name" and "enable" status.
     * Combines the {@link #hasName(String)} specification for partial matching of the "name"
     * field and the {@link #isEnable(boolean)} specification for filtering by the "enable" field.
     *
     * @param name   the substring to filter {@link GameEntity} objects by their "name" field.
     *               If null or empty, the name filter is not applied.
     * @param enable the desired enable status to filter the {@link GameEntity} objects (true for enabled, false for disabled).
     * @return a {@link Specification} that combines both "name" filtering and "enable" status filtering
     * to identify matching {@link GameEntity} objects.
     */
    public static Specification<GameEntity> hasAllFilters(final String name, final boolean enable) {
        return Specification.where(hasName(name))
                .and(isEnable(enable));
    }

    /**
     * Creates a specification to filter {@link GameEntity} objects based on a partial match
     * of their "name" field using the SQL "LIKE" operator.
     *
     * @param name the substring to filter {@link GameEntity} objects by their "name" field.
     *             If the name is null or empty, all entities will be considered as matching.
     * @return a {@link Specification} that can be used to filter {@link GameEntity} objects
     * whose "name" field contains the specified substring.
     */
    public static Specification<GameEntity> hasName(final String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    /**
     * Creates a specification to filter {@link GameEntity} objects based on their "enable" status.
     *
     * @param enable the desired enable status to filter the {@link GameEntity} objects (true for enabled, false for disabled).
     * @return a {@link Specification} that can be used to filter {@link GameEntity} objects by their "enable" field.
     */
    public static Specification<GameEntity> isEnable(final boolean enable) {
        return (root, query, cb) -> cb.equal(root.get("enable"), enable);
    }

}
