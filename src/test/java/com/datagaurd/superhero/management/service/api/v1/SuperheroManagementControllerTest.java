package com.datagaurd.superhero.management.service.api.v1;

import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuperheroManagementController.class)
public class SuperheroManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void createSuperHero() throws Exception {
        Superhero superheroRequestMock = getSuperheroRequestMock();

        mockMvc.perform(post("/api/v1/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superheroRequestMock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("Iron Man"))
                .andExpect(jsonPath("$.name").value("Tony Stark"));
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

