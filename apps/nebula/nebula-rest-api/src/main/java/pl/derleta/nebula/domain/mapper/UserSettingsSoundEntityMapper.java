package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.entity.UserSettingsSoundEntity;
import pl.derleta.nebula.domain.model.UserSettingsSound;

/**
 * Utility class for mapping between UserSettingsSound and UserSettingsSoundEntity objects.
 * This class provides methods to convert business models (UserSettingsSound) into
 * database entities (UserSettingsSoundEntity).
 * <p>
 * The class is designed to be non-instantiable and offers static methods for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSettingsSoundEntityMapper {

    /**
     * Converts a UserSettingsSound object to a UserSettingsSoundEntity object.
     * This method maps the fields of the provided UserSettingsSound record
     * to the corresponding attributes of a new UserSettingsSoundEntity instance.
     *
     * @param item the UserSettingsSound record to be converted
     * @return a UserSettingsSoundEntity object containing the mapped data
     */
    public static UserSettingsSoundEntity toEntity(final UserSettingsSound item) {
        UserSettingsSoundEntity entity = new UserSettingsSoundEntity();
        entity.setId(item.userId());
        entity.setMuted(item.muted());
        entity.setBattleCry(item.battleCry());
        entity.setVolumeMaster(item.volumeMaster());
        entity.setVolumeMusic(item.volumeMusic());
        entity.setVolumeVoices(item.volumeVoices());
        entity.setVolumeEffects(item.volumeEffects());
        return entity;
    }

}
