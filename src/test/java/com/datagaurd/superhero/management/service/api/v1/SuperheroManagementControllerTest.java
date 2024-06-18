package com.datagaurd.superhero.management.service.api.v1;

import com.datagaurd.superhero.management.service.api.exception.ResourceNotFoundException;
import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.service.SuperheroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SuperheroManagementController.class)
public class SuperheroManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private AutoCloseable closeable;
    @MockBean
    private SuperheroService superheroService;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @SneakyThrows
    public void createSuperHero() {
        // mocks
        Superhero superheroRequestMock = getSuperheroRequestMock();
        when(superheroService.createSuperhero(any(Superhero.class))).thenReturn(1L);

        // request
        var resultActions =
                mockMvc.perform(post("/api/v1/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superheroRequestMock)));

        // Verify
        var response = resultActions.andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        String location = response.getHeader("Location");
        assertNotNull(location);
        assertTrue(location.contains("/api/v1/superheroes/1"));
    }
    @Test
    public void createSuperheroWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/v1/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Name is mandatory\"}"));
    }
    @Test
    public void testGetSuperheroes() throws Exception {
        Superhero Superhero = getSuperheroRequestMock();

        List<Superhero> SuperheroList = Collections.singletonList(Superhero);

        when(superheroService.getSuperheroes()).thenReturn(SuperheroList);

        mockMvc.perform(get("/api/v1/superheroes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].alias").value("Iron Man"))
                .andExpect(jsonPath("$[0].name").value("Tony Stark"));
    }

    @Test
    public void testGetSuperheroById() throws Exception {
        Superhero Superhero = getSuperheroRequestMock();

        when(superheroService.getSuperheroById(1L)).thenReturn(Superhero);

        mockMvc.perform(get("/api/v1/superheroes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("Iron Man"))
                .andExpect(jsonPath("$.name").value("Tony Stark"));
    }
    @Test
    public void testGetSuperheroByIdNotFound() throws Exception {
        when(superheroService.getSuperheroById(1L)).thenThrow(new ResourceNotFoundException("Superhero not found with id: 1"));

        mockMvc.perform(get("/api/v1/superheroes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Superhero not found with id: 1"));
    }

    @Test
    public void testCreateSuperheroWithError() throws Exception {
        Superhero superhero = getSuperheroRequestMock();

        when(superheroService.createSuperhero(any(Superhero.class))).thenThrow(new RuntimeException("An unexpected error occurred"));

        mockMvc.perform(post("/api/v1/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superhero)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("An unexpected error occurred"));
    }
    private static Superhero getSuperheroRequestMock() {
        return Superhero.builder()
                .alias("Iron Man").name("Tony Stark")
                .powers(Arrays.asList("genius-intelligence", "wealth"))
                .weapons(Arrays.asList("arc-reactor", "iron-man-suit", "iron-legion"))
                .origin("Kidnapped in Afghanistan, created the first iron-man suit to escape.")
                .associations(Arrays.asList("war-machine", "avengers", "jarvis", "thanos", "pepper-potts"))
                .build();
    }
}

