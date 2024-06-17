package com.datagaurd.superhero.management.service.api.v1;

import com.datagaurd.superhero.management.service.api.v1.model.Superhero;
import com.datagaurd.superhero.management.service.service.SuperheroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

