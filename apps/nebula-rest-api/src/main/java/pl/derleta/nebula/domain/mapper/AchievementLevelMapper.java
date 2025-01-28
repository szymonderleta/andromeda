package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.builder.impl.AchievementLevelBuilderImpl;
import pl.derleta.nebula.domain.entity.AchievementLevelEntity;
import pl.derleta.nebula.domain.model.AchievementLevel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping between AchievementLevelEntity and AchievementLevel objects.
 * This class provides methods to convert single entity objects and collections of entities
 * to their corresponding model representations. The class is designed to ensure that
 * the mapping is centralized, reusable, and consistent throughout the application.
 * <p>
 * The class is not instantiable and provides only static methods for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AchievementLevelMapper {

    /**
     * Converts a list of AchievementLevelEntity objects to a list of AchievementLevel objects.
     * This method maps each entity in the input list to its corresponding model representation.
     *
     * @param entities the list of AchievementLevelEntity objects to be converted
     * @return a list of AchievementLevel objects corresponding to the input entities
     */
    public static List<AchievementLevel> toAchievementLevels(final List<AchievementLevelEntity> entities) {
        return entities.stream().map(AchievementLevelMapper::toAchievementLevel).collect(Collectors.toList());
    }

    /**
     * Converts an AchievementLevelEntity object to an AchievementLevel object.
     * This method maps the properties of the input entity to a new instance of AchievementLevel.
     *
     * @param entity the AchievementLevelEntity object to be converted
     * @return an AchievementLevel object corresponding to the input entity
     */
    public static AchievementLevel toAchievementLevel(final AchievementLevelEntity entity) {
        return new AchievementLevelBuilderImpl()
                .level(entity.getId().getLevel())
                .value(entity.getValue())
                .build();
    }

}
