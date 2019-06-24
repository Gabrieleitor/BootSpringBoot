package com.training.service;

import com.training.controller.dto.ActorDTO;
import com.training.model.Actor;
import com.training.model.PersonName;
import com.training.repository.ActorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Actor service.
 */
@Service
public class ActorService {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ActorRepository actorRepository;

    /**
     * Retrieves an {@link Actor} given an id.
     *
     * @param id the actor id. It cannot be null
     * @return the actor
     * @throws IllegalArgumentException if <code>id</code> is null
     */
    public ActorDTO findById(Long id) {
        Assert.notNull(id, "The actor id cannot be null");

        Actor actor = actorRepository.findById(id)
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Actor not found"));

        return conversionService.convert(actor, ActorDTO.class);
    }

    /**
     * Returns all the actors.
     * @return a list with actors.
     */
    public Page<ActorDTO> findAll(final Pageable pageable ) {
        return actorRepository.findAll(pageable)
            .map( actor -> conversionService.convert(actor, ActorDTO.class));
    }

    /**
     * Saves a new actor or update if it exists.
     *
     * @param actorDTO the actor to be saved or updated.
     * @return the actor
     * @throws IllegalArgumentException if <code>actor<code/> is null
     */
    public ActorDTO save(ActorDTO actorDTO) {
        Assert.notNull(actorDTO, "The actor cannot be null");

        Actor actor = conversionService.convert(actorDTO, Actor.class);
        actor = actorRepository.save(actor);

        return conversionService.convert(actor, ActorDTO.class);
    }

    /**
     * Removes the given actor.
     *
     * @param id the actor id
     * @throws IllegalArgumentException if <code>actor</code> is null
     */
    public void deleteById(Long id) {
        Assert.notNull(id, "The actor id cannot be null");
        actorRepository.deleteById(id);
    }
}
