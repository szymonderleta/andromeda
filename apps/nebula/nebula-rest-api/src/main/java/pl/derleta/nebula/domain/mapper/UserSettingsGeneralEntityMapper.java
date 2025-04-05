package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.entity.UserSettingsGeneralEntity;
import pl.derleta.nebula.domain.model.UserSettingsGeneral;

/**
 * Utility class for mapping between UserSettingsGeneral and UserSettingsGeneralEntity objects.
 * This class provides static methods to convert the UserSettingsGeneral business model
 * into its corresponding database entity representation, UserSettingsGeneralEntity.
 * <p>
 * The class is designed to be non-instantiable and operates exclusively through static methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSettingsGeneralEntityMapper {

    /**
     * Converts a UserSettingsGeneral object to a UserSettingsGeneralEntity object.
     * This method maps the properties of the provided UserSettingsGeneral record to
     * the corresponding fields of a new UserSettingsGeneralEntity instance.
     *
     * @param item the UserSettingsGeneral object to be converted
     * @return a UserSettingsGeneralEntity object constructed from the provided UserSettingsGeneral object
     */
    public static UserSettingsGeneralEntity toEntity(final UserSettingsGeneral item) {
        UserSettingsGeneralEntity entity = new UserSettingsGeneralEntity();
        entity.setId(item.userId());
        entity.setTheme(
                ThemeEntityMapper.toEntity(item.theme())
        );
        return entity;
    }

}
