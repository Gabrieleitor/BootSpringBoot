package com.training.controller;

import com.training.controller.dto.ActorDTO;
import com.training.model.Actor;
import com.training.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ActorDTO getActor(@PathVariable Long id) {
        return actorService.findById(id);
    }

    /**
     * Returns all actors.
     *
     * @return a list of actors
     */
    @GetMapping
    public Page<ActorDTO> getActors( final Pageable pageable ) {
        return actorService.findAll( pageable );
    }

    /**
     * Saves a new actor
     *
     * @param actorDTO the actor
     * @return the saved actor
     */
    @PostMapping
    public ActorDTO saveActor(@RequestBody ActorDTO actorDTO) {
        return actorService.save(actorDTO);
    }

    /**
     * Updates an actor given an id.
     *
     * @param id    the actor id
     * @param actorDTO the actor
     * @return the updated actor
     */
    @PutMapping("/{id}")
    public ActorDTO updateActor(@PathVariable Long id, @RequestBody ActorDTO actorDTO) {
        actorDTO.setId(id);
        return actorService.save(actorDTO);
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
