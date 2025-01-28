package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.builder.impl.UserSettingsBuilderImpl;
import pl.derleta.nebula.domain.entity.UserSettingsEntity;
import pl.derleta.nebula.domain.model.UserSettings;

/**
 * Utility class for mapping between {@link UserSettingsEntity} and {@link UserSettings}.
 * This class provides a method to transform a database entity ({@link UserSettingsEntity})
 * into a business model ({@link UserSettings}).
 * The class is non-instantiable and provides static methods for conversion.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSettingsMapper {

    /**
     * Converts a {@link UserSettingsEntity} object to a {@link UserSettings} object.
     * This method maps the attributes of the given entity to the corresponding
     * model using a builder pattern to construct the final object.
     *
     * @param entity the {@link UserSettingsEntity} object to be converted
     * @return a {@link UserSettings} object constructed from the provided entity
     */
    public static UserSettings toSetting(final UserSettingsEntity entity) {
        return new UserSettingsBuilderImpl()
                .userId(entity.getId())
                .userSettingsGeneral(UserSettingsGeneralMapper.toSetting(entity.getGeneral()))
                .userSettingsSound(UserSettingsSoundMapper.toSetting(entity.getSound()))
                .build();
    }

}
