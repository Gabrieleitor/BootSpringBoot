package com.training.service;

import com.training.model.Actor;
import com.training.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The Actor service.
 */
@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    /**
     * Retrieves an {@link Actor} given an id.
     *
     * @param id the actor id. It cannot be null
     * @return the actor
     * @throws IllegalArgumentException if <code>id</code> is null
     */
    public Actor findById(Long id) {
        Assert.notNull(id, "The actor id cannot be null");
        return actorRepository.findById(id).orElse(null);
    }

    /**
     * Returns all the actors.
     * @return a list with actors.
     */
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    /**
     * Saves a new actor or update if it exists.
     *
     * @param actor the actor to be saved or updated.
     * @return the actor
     * @throws IllegalArgumentException if <code>actor<code/> is null
     */
    public Actor save(Actor actor) {
        Assert.notNull(actor, "The actor cannot be null");
        return actorRepository.save(actor);
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
