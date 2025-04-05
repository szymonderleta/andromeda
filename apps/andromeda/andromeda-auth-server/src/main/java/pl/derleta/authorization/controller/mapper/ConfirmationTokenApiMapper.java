package pl.derleta.authorization.controller.mapper;

import pl.derleta.authorization.domain.builder.impl.ConfirmationTokenBuilderImpl;
import pl.derleta.authorization.domain.entity.ConfirmationTokenEntity;
import pl.derleta.authorization.domain.model.ConfirmationToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides utility methods for converting between {@link ConfirmationTokenEntity},
 * {@link ConfirmationToken}, and related representations.
 * This class is a non-instantiable utility class.
 */
public final class ConfirmationTokenApiMapper {

    private ConfirmationTokenApiMapper() {
    }

    /**
     * Converts a list of {@link ConfirmationTokenEntity} objects to a list of {@link ConfirmationToken} objects.
     *
     * @param entities the list of {@link ConfirmationTokenEntity} objects to be converted
     * @return a list of {@link ConfirmationToken} objects corresponding to the input entities
     */
    public static List<ConfirmationToken> toConfirmationTokens(final List<ConfirmationTokenEntity> entities) {
        return entities.stream().map(ConfirmationTokenApiMapper::toConfirmationToken).collect(Collectors.toList());
    }

    /**
     * Maps a {@link ConfirmationTokenEntity} object to a {@link ConfirmationToken} object.
     *
     * @param entity the {@link ConfirmationTokenEntity} to be converted
     * @return a {@link ConfirmationToken} object corresponding to the input entity
     */
    public static ConfirmationToken toConfirmationToken(final ConfirmationTokenEntity entity) {
        return new ConfirmationTokenBuilderImpl()
                .tokenId(entity.getTokenId())
                .user(UserApiMapper.toUser(entity.getUser()))
                .token(entity.getToken())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

}
