package pl.derleta.authorization.controller.assembler;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.derleta.authorization.controller.mapper.UserApiMapper;
import pl.derleta.authorization.controller.JwtTokenController;
import pl.derleta.authorization.domain.model.JwtToken;
import pl.derleta.authorization.domain.response.JwtTokenResponse;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * The JwtTokenModelAssembler class is a Spring HATEOAS assembler that facilitates the mapping of
 * {@link JwtToken} entities to their corresponding {@link JwtTokenResponse} REST models. It also
 * incorporates HATEOAS-compliant links into the response to follow RESTful standards.
 * <p>
 * This class extends {@link RepresentationModelAssemblerSupport} and provides methods for converting
 * {@link JwtToken} entities into fully-formed {@link JwtTokenResponse} models with HATEOAS links.
 * <p>
 * JwtTokenModelAssembler ensures consistency in the structure and representation of JWT Token responses
 * across the application, allowing easy navigation to associated resources via HATEOAS links.
 */
@Component
public class JwtTokenModelAssembler extends RepresentationModelAssemblerSupport<JwtToken, JwtTokenResponse> {

    public JwtTokenModelAssembler() {
        super(JwtTokenController.class, JwtTokenResponse.class);
    }

    /**
     * Converts a {@link JwtToken} entity into a {@link JwtTokenResponse} model, copying properties
     * from the entity and adding a self-referential HATEOAS link to the model.
     *
     * @param entity the {@link JwtToken} entity to be converted into a {@link JwtTokenResponse} model
     * @return the {@link JwtTokenResponse} model populated with copied properties and an added self-referential link
     */
    @Override
    public JwtTokenResponse toModel(JwtToken entity) {
        JwtTokenResponse model = new JwtTokenResponse();
        BeanUtils.copyProperties(entity, model);
        model.setUser(UserApiMapper.toUserResponse(entity.user()));
        Link selfLink = linkTo(JwtTokenController.class).slash(JwtTokenController.DEFAULT_PATH).slash(model.getTokenId()).withSelfRel();
        model.add(selfLink);
        return model;
    }

    /**
     * Converts a {@link JwtToken} entity into a {@link JwtTokenResponse} model, copying all relevant
     * properties from the entity and adding a self-referential HATEOAS link to the model.
     *
     * @param entity the {@link JwtToken} entity to be converted into a {@link JwtTokenResponse} model
     * @param path   the base path used to construct the self-referential HATEOAS link
     * @return the {@link JwtTokenResponse} model populated with copied properties and an added self-referential link
     */
    public JwtTokenResponse toModel(JwtToken entity, String path) {
        JwtTokenResponse model = new JwtTokenResponse();
        BeanUtils.copyProperties(entity, model);
        Link selfLink = linkTo(JwtTokenController.class).slash(path).slash(model.getTokenId()).withSelfRel();
        model.add(selfLink);
        return model;
    }

}
