package com.training.service;

import com.training.converter.PersonNameConverter;
import com.training.model.Actor;
import com.training.model.PersonName;
import com.training.repository.ActorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link ActorService} unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorService actorService;

    private static final Long ACTOR_ID = 1L;

    @Test
    public void givenAValidIdWhenFindByIdThenReturnActor() {
        //given
        Actor expectedActor = Actor.builder()
            .id(ACTOR_ID)
            .build();

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(expectedActor));

        //when
        Actor actor = actorService.findById(ACTOR_ID);

        //then
        Assertions.assertNotNull(actor);
        Assertions.assertEquals(actor.getId(), expectedActor.getId());
    }

    @Test
    public void givenANullIdWhenFindByIdThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            actorService.findById(null));
    }

    @Test
    public void testFindAllShouldRunOk() {
        //given
        List<Actor> expectedActors = Arrays.asList(Actor.builder()
            .id(ACTOR_ID)
            .build());

        when(actorRepository.findAll()).thenReturn(expectedActors);

        //when
        List<Actor> actors = actorService.findAll();

        //then
        Assertions.assertNotNull(actors);
        Assertions.assertEquals(1, actors.size());
        Assertions.assertSame(expectedActors, actors);
    }

    @Test
    public void givenAActorWhenSaveThenReturnActor() {
        //given
        Actor expectedActor = Actor.builder()
            .id(ACTOR_ID)
            .build();

        Actor actorToBeSaved = Actor.builder()
            .build();

        when(actorRepository.save(any(Actor.class))).thenReturn(expectedActor);

        //when
        Actor actor = actorService.save(actorToBeSaved);

        //then
        Assertions.assertNotNull(actor);
        Assertions.assertNotNull(actor.getId());
        Assertions.assertEquals(actor.getName(), expectedActor.getName());
    }

    @Test
    public void givenANullActorWhenSaveThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            actorService.save(null));
    }

    @Test
    public void givenAActorIdWhenDeleteByIdThenRunOk() {
        //when
        actorService.deleteById(ACTOR_ID);

        //then
        verify(actorRepository).deleteById(ACTOR_ID);
    }

    @Test
    public void givenANullIdWhenDeleteByIdThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            actorService.deleteById(null));
    }
}
