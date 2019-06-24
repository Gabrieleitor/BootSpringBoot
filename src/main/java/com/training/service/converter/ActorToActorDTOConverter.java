package com.training.service.converter;

import com.training.controller.dto.ActorDTO;
import com.training.model.Actor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ActorToActorDTOConverter implements Converter<Actor, ActorDTO> {

    @Override
    public ActorDTO convert(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        BeanUtils.copyProperties(actor, actorDTO);

        if (Objects.nonNull(actor.getName())) {
            actorDTO.setName(actor.getName().getName());
            actorDTO.setLastName(actor.getName().getLastName());
        }

        return actorDTO;
    }

}
