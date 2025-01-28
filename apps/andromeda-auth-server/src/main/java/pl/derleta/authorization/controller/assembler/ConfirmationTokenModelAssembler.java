package pl.derleta.authorization.controller.assembler;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.derleta.authorization.controller.mapper.UserApiMapper;
import pl.derleta.authorization.controller.ConfirmationTokenController;
import pl.derleta.authorization.domain.model.ConfirmationToken;
import pl.derleta.authorization.domain.response.ConfirmationTokenResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * This class is responsible for converting {@link ConfirmationToken} entities into
 * {@link ConfirmationTokenResponse} models while adding HATEOAS links for RESTful API responses.
 * It extends {@link RepresentationModelAssemblerSupport} to provide specific implementation details
 * for assembling representation models of confirmation tokens.
 * <p>
 * The class handles two scenarios:
 * 1. Converting an entity into a response model and automatically adding a default self-link.
 * 2. Converting an entity into a response model while allowing customization of the HATEOAS link base path.
 * <p>
 * This assembler is typically used in conjunction with the Spring HATEOAS library to facilitate
 * the creation of hypermedia-driven REST APIs.
 * <p>
 * The returned {@link ConfirmationTokenResponse} contains:
 * - The token details
 * - Details of the associated user
 * - A self-referential link for the token resource
 * <p>
 * The HATEOAS links are generated using Spring's {@code linkTo} and {@code slash} methods, enabling
 * seamless navigation to resource endpoints.
 */
@Component
public class ConfirmationTokenModelAssembler extends RepresentationModelAssemblerSupport<ConfirmationToken, ConfirmationTokenResponse> {

    public ConfirmationTokenModelAssembler() {
        super(ConfirmationTokenController.class, ConfirmationTokenResponse.class);
    }

    /**
     * Converts a {@link ConfirmationToken} entity into a {@link ConfirmationTokenResponse} model,
     * copying properties from the entity, including the user details, and adds a HATEOAS self-referential link.
     *
     * @param entity the {@link ConfirmationToken} entity to be converted
     * @return the resulting {@link ConfirmationTokenResponse} model with copied properties and a self-link added
     */
    @Override
    public ConfirmationTokenResponse toModel(ConfirmationToken entity) {
        ConfirmationTokenResponse model = new ConfirmationTokenResponse();
        BeanUtils.copyProperties(entity, model);
        model.setUser(UserApiMapper.toUserResponse(entity.user()));
        Link selfLink = linkTo(ConfirmationTokenController.class).slash(ConfirmationTokenController.DEFAULT_PATH).slash(model.getTokenId()).withSelfRel();
        model.add(selfLink);
        return model;
    }

    /**
     * Converts a {@link ConfirmationToken} entity into a {@link ConfirmationTokenResponse} model,
     * copying all properties from the entity, and adds a self-referential HATEOAS link to the model.
     *
     * @param entity the {@link ConfirmationToken} entity to be converted into a {@link ConfirmationTokenResponse} model
     * @param path   the base path used to construct the self-referential HATEOAS link
     * @return the {@link ConfirmationTokenResponse} model with copied properties and an added self-referential link
     */
    public ConfirmationTokenResponse toModel(ConfirmationToken entity, String path) {
        ConfirmationTokenResponse model = new ConfirmationTokenResponse();
        BeanUtils.copyProperties(entity, model);
        Link selfLink = linkTo(ConfirmationTokenController.class).slash(path).slash(model.getTokenId()).withSelfRel();
        model.add(selfLink);
        return model;
    }

}
