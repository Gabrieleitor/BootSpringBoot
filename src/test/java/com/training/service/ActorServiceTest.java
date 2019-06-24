package com.training.service;

import com.training.controller.dto.ActorDTO;
import com.training.model.Actor;
import com.training.model.PersonName;
import com.training.repository.ActorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link ActorService} unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private ActorService actorService;

    private static final Long ACTOR_ID = 1L;

    private static final String ACTOR_NAME = "Firstname";

    private static final String ACTOR_LASTNAME = "Lastname";

    @Test
    public void givenAValidIdWhenFindByIdThenReturnActor() {
        //given
        Actor expectedActor = Actor.builder()
            .id(ACTOR_ID)
            .build();

        ActorDTO returnActorDTO = ActorDTO.builder()
            .id(ACTOR_ID)
            .build();

        when(actorRepository.findById(anyLong())).thenReturn(Optional.of(expectedActor));

        doReturn(returnActorDTO).when(conversionService).convert(expectedActor, ActorDTO.class);

        //when
        ActorDTO actor = actorService.findById(ACTOR_ID);

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

        Page<Actor> pagedActors = new PageImpl(expectedActors);

        when(actorRepository.findAll(isA(Pageable.class))).thenReturn(pagedActors);

        //when
        Page<ActorDTO> actors = actorService.findAll(pageable);

        //then
        Assertions.assertNotNull(actors);
        Assertions.assertEquals(1, actors.getTotalElements());
    }

    @Test
    public void givenAActorWhenSaveThenReturnActor() {
        //given
        Actor expectedActor = Actor.builder()
            .id(ACTOR_ID)
            .name(new PersonName(ACTOR_NAME, ACTOR_LASTNAME))
            .build();

        ActorDTO actorToBeSaved = ActorDTO.builder()
            .name(ACTOR_NAME)
            .lastName(ACTOR_LASTNAME)
            .build();

        Actor actorConverter = Actor.builder()
            .name(new PersonName(ACTOR_NAME, ACTOR_LASTNAME))
            .build();

        ActorDTO actorToBeReturned = ActorDTO.builder()
            .id(ACTOR_ID)
            .name(ACTOR_NAME)
            .lastName(ACTOR_LASTNAME)
            .build();

        doReturn(actorConverter).when(conversionService).convert(actorToBeSaved, Actor.class);

        doReturn(actorToBeReturned).when(conversionService).convert(expectedActor, ActorDTO.class);

        when(actorRepository.save(actorConverter)).thenReturn(expectedActor);

        //when
        ActorDTO actor = actorService.save(actorToBeSaved);

        //then
        Assertions.assertNotNull(actor);
        Assertions.assertNotNull(actor.getId());
        Assertions.assertEquals(actor.getId(), expectedActor.getId());
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
