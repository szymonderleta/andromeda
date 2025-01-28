package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.entity.ThemeEntity;
import pl.derleta.nebula.domain.model.Theme;

/**
 * Utility class for mapping a Theme object to a ThemeEntity object.
 * This class provides a method to convert a business model object (Theme) into
 * its corresponding database entity representation (ThemeEntity).
 * <p>
 * The class is designed to be non-instantiable and provides only static methods
 * for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThemeEntityMapper {

    /**
     * Converts a Theme object to a ThemeEntity object.
     * This method maps the relevant properties of the Theme record to the corresponding fields
     * of a ThemeEntity instance.
     *
     * @param item the Theme object to be converted
     * @return a ThemeEntity object that corresponds to the provided Theme object
     */
    public static ThemeEntity toEntity(final Theme item) {
        ThemeEntity entity = new ThemeEntity();
        entity.setId(item.id());
        entity.setName(item.name());
        return entity;
    }

}
