package pl.derleta.authorization.controller.mapper;

import pl.derleta.authorization.domain.builder.impl.JwtTokenBuilderImpl;
import pl.derleta.authorization.domain.entity.JwtTokenEntity;
import pl.derleta.authorization.domain.model.JwtToken;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing methods for mapping JwtToken-related entities and models.
 * This class contains methods to transform JwtTokenEntity objects into JwtToken
 * objects and related representations.
 */
public final class JwtTokenApiMapper {

    private JwtTokenApiMapper() {
    }

    /**
     * Converts a list of {@link JwtTokenEntity} objects to a list of {@link JwtToken} objects.
     *
     * @param entities the list of {@link JwtTokenEntity} objects to be converted
     * @return a list of {@link JwtToken} objects corresponding to the input entities
     */
    public static List<JwtToken> toJwtTokens(final List<JwtTokenEntity> entities) {
        return entities.stream().map(JwtTokenApiMapper::toJwtToken).collect(Collectors.toList());
    }

    /**
     * Converts a given {@link JwtTokenEntity} to a {@link JwtToken}.
     *
     * @param entity the {@link JwtTokenEntity} containing data to be converted to a {@link JwtToken}
     * @return a {@link JwtToken} instance containing the mapped data
     */
    public static JwtToken toJwtToken(final JwtTokenEntity entity) {
        return new JwtTokenBuilderImpl()
                .tokenId(entity.getTokenId())
                .user(UserApiMapper.toUser(entity.getUser()))
                .token(entity.getToken())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

}
