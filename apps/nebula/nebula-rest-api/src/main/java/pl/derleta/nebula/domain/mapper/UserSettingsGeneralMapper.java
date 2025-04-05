package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.builder.impl.UserSettingsGeneralBuilderImpl;
import pl.derleta.nebula.domain.entity.UserSettingsGeneralEntity;
import pl.derleta.nebula.domain.model.UserSettingsGeneral;

/**
 * Utility class for mapping between {@link UserSettingsGeneralEntity} and {@link UserSettingsGeneral}.
 * This class provides a method to transform a database entity ({@link UserSettingsGeneralEntity})
 * into a business model ({@link UserSettingsGeneral}).
 * <p>
 * The class is designed to be non-instantiable and follows a static method approach
 * for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserSettingsGeneralMapper {

    /**
     * Converts a {@link UserSettingsGeneralEntity} object to a {@link UserSettingsGeneral} object.
     * This method maps the attributes of the given entity to the corresponding model
     * using a builder implementation for transformation.
     *
     * @param entity the {@link UserSettingsGeneralEntity} object to be transformed
     * @return a {@link UserSettingsGeneral} object constructed from the provided entity
     */
    public static UserSettingsGeneral toSetting(final UserSettingsGeneralEntity entity) {
        return new UserSettingsGeneralBuilderImpl()
                .userId(entity.getId())
                .theme(ThemeMapper.toTheme(entity.getTheme()))
                .build();
    }

}
