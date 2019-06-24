package com.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.controller.dto.ActorDTO;
import com.training.service.ActorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ActorController.class)
public class ActorControllerTest {

    private static final Long ACTOR_ID = 1L;
    private static final String ACTOR_NAME = "The Actor name";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

    @Test
    public void testGetActor() throws Exception {
        //when
        this.mockMvc.perform(get("/actors/" + ACTOR_ID))
            .andExpect(status().isOk());

        //then
        verify(actorService).findById(ACTOR_ID);
    }

    @Test
    public void testGetActors() throws Exception {
        //when
        this.mockMvc.perform(get("/actors"))
            .andExpect(status().isOk());

        //then
        verify(actorService).findAll(any(Pageable.class));
    }

    @Test
    public void testSaveActor() throws Exception {
        //given
        ActorDTO actor = ActorDTO.builder()
            .build();

        //when
        this.mockMvc.perform(post("/actors")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(new ObjectMapper().writeValueAsString(actor)))
            .andExpect(status().isOk());

        //then
        verify(actorService).save(actor);
    }

    @Test
    public void testUpdateActor() throws Exception {
        ActorDTO actor = ActorDTO.builder()
            .build();

        this.mockMvc.perform(put("/actors/" + ACTOR_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(new ObjectMapper().writeValueAsString(actor)))
            .andExpect(status().isOk());

        actor.setId(ACTOR_ID);
        verify(actorService).save(actor);
    }

    @Test
    public void testDeleteActor() throws Exception {
        //when
        this.mockMvc.perform(delete("/actors/" + ACTOR_ID))
            .andExpect(status().isOk());

        //then
        verify(actorService).deleteById(ACTOR_ID);
    }

}
