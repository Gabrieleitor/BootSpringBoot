package com.training.controller;

import com.training.model.Actor;
import com.training.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The Actor Controller.
 */
@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    /**
     * Returns an actor given an id.
     *
     * @param id the actor id
     * @return the actor
     */
    @GetMapping(value = "/{id}")
    public Actor getActor(@PathVariable Long id) {
        return actorService.findById(id);
    }

    /**
     * Returns all actors.
     *
     * @return a list of actors
     */
    @GetMapping
    public List<Actor> getActors() {
        return actorService.findAll();
    }

    /**
     * Saves a new actor
     *
     * @param actor the actor
     * @return the saved actor
     */
    @PostMapping
    public Actor saveActor(@RequestBody Actor actor) {
        return actorService.save(actor);
    }

    /**
     * Updates an actor given an id.
     *
     * @param id    the actor id
     * @param actor the actor
     * @return the updated actor
     */
    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        actor.setId(id);
        return actorService.save(actor);
    }

    /**
     * Deletes an actor given an id.
     *
     * @param id the actor id
     */
    @DeleteMapping(value = "/{id}")
    public void deleteActor(@PathVariable Long id) {
        actorService.deleteById(id);
    }

}
