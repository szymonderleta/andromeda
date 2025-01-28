package pl.derleta.nebula.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.derleta.nebula.domain.builder.impl.GameEntityBuilderImpl;
import pl.derleta.nebula.domain.entity.GameEntity;
import pl.derleta.nebula.domain.model.Game;

/**
 * Utility class for mapping a Game object to a GameEntity object.
 * This class provides a method to convert a business model object (Game) into
 * its corresponding database entity representation (GameEntity).
 * <p>
 * The class is designed to be non-instantiable and provides only static methods
 * for mapping operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameEntityMapper {

    /**
     * Converts a Game object to a GameEntity object.
     * This method maps the relevant properties of the Game record to the corresponding fields
     * of a GameEntity instance.
     *
     * @param item the Game object to be converted
     * @return a GameEntity object that corresponds to the provided Game object
     */
    public static GameEntity toEntity(final Game item) {
        return new GameEntityBuilderImpl()
                .id(item.id())
                .name(item.name())
                .enable(item.enable())
                .iconUrl(item.iconUrl())
                .pageUrl(item.pageUrl())
                .build();
    }

}
