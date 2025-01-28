package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.builder.impl.ThemeBuilderImpl;
import pl.derleta.nebula.domain.entity.ThemeEntity;
import pl.derleta.nebula.domain.model.Theme;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping between ThemeEntity and Theme objects.
 * This class provides static methods to transform data from database entities
 * (ThemeEntity) into business models (Theme).
 * <p>
 * The class is designed to be non-instantiable and serves as an abstraction layer
 * for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThemeMapper {

    /**
     * Converts a list of ThemeEntity objects to a list of Theme objects.
     * This method transforms each ThemeEntity in the provided list into a corresponding Theme object.
     *
     * @param entities the list of ThemeEntity objects to be converted
     * @return a list of Theme objects constructed from the provided list of ThemeEntity objects
     */
    public static List<Theme> toThemes(final List<ThemeEntity> entities) {
        return entities.stream().map(ThemeMapper::toTheme).collect(Collectors.toList());
    }

    /**
     * Converts a ThemeEntity object to a Theme object.
     * This method maps the attributes of the ThemeEntity instance to a Theme record
     * using a builder implementation.
     *
     * @param entity the ThemeEntity object to be transformed
     * @return a Theme object constructed from the provided ThemeEntity
     */
    public static Theme toTheme(final ThemeEntity entity) {
        return new ThemeBuilderImpl()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
