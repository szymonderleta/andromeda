package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.builder.impl.UserSettingsSoundBuilderImpl;
import pl.derleta.nebula.domain.entity.UserSettingsSoundEntity;
import pl.derleta.nebula.domain.model.UserSettingsSound;

/**
 * Utility class for mapping between UserSettingsSoundEntity and UserSettingsSound objects.
 * This class provides functionality to convert database entities (UserSettingsSoundEntity)
 * to business models (UserSettingsSound).
 * <p>
 * The class is designed to be non-instantiable and provides only static methods for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSettingsSoundMapper {

    /**
     * Converts a UserSettingsSoundEntity object to a UserSettingsSound object.
     * This method maps the fields of the provided UserSettingsSoundEntity
     * instance to the corresponding attributes of a UserSettingsSound object
     * using a builder implementation.
     *
     * @param entity the UserSettingsSoundEntity object to be converted
     * @return a UserSettingsSound object constructed from the provided UserSettingsSoundEntity object
     */
    public static UserSettingsSound toSetting(final UserSettingsSoundEntity entity) {
        return new UserSettingsSoundBuilderImpl()
                .userId(entity.getId())
                .muted(entity.getMuted())
                .battleCry(entity.getBattleCry())
                .volumeMaster(entity.getVolumeMaster())
                .volumeMusic(entity.getVolumeMusic())
                .volumeVoices(entity.getVolumeVoices())
                .volumeEffects(entity.getVolumeEffects())
                .build();
    }

}
