package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.entity.UserSettingsEntity;
import pl.derleta.nebula.domain.model.UserSettings;

/**
 * Utility class for mapping between UserSettings and UserSettingsEntity objects.
 * This class provides a method to transform a business model object, UserSettings,
 * into its corresponding database entity representation, UserSettingsEntity.
 * The mapping operation includes nested mapping of sound and general settings components.
 * <p>
 * The class is designed to be non-instantiable and exposes only static methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSettingsEntityMapper {

    /**
     * Converts a UserSettings record to a UserSettingsEntity object.
     * Maps the attributes of the UserSettings instance to a new UserSettingsEntity instance,
     * including its nested sound and general settings.
     *
     * @param item the UserSettings object to be converted
     * @return a UserSettingsEntity object constructed from the provided UserSettings instance
     */
    public static UserSettingsEntity toEntity(final UserSettings item) {
        UserSettingsEntity entity = new UserSettingsEntity();
        entity.setId(item.userId());
        entity.setSound(UserSettingsSoundEntityMapper.toEntity(item.sound()));
        entity.setGeneral(UserSettingsGeneralEntityMapper.toEntity(item.general()));
        return entity;
    }

}
