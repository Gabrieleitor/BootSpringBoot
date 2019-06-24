package com.training.service.converter;

import com.training.controller.dto.ActorDTO;
import com.training.model.Actor;
import com.training.model.PersonName;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ActorDTOToActorConverter implements Converter<ActorDTO, Actor> {

    @Override
    public Actor convert(ActorDTO actorDTO) {
        Actor actor = new Actor();
        BeanUtils.copyProperties(actorDTO, actor);

        PersonName personName = new PersonName(actorDTO.getName(), actorDTO.getLastName());
        actor.setName(personName);

        return actor;
    }
}
