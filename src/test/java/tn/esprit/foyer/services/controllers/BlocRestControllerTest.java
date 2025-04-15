package tn.esprit.foyer.services.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.foyer.controllers.BlocRestController;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.services.IBlocService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlocRestController.class)
public class BlocRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBlocService blocService;

    @Test
    @DisplayName("Test GET /bloc/retrieve-all-blocs")
    public void testGetBlocs() throws Exception {
        // Arrange
        Bloc bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("A");
        Bloc bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("B");

        List<Bloc> blocs = Arrays.asList(bloc1, bloc2);
        Mockito.when(blocService.retrieveAllBlocs()).thenReturn(blocs);

        // Act & Assert
        mockMvc.perform(get("/bloc/retrieve-all-blocs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idBloc").value(1))
                .andExpect(jsonPath("$[0].nomBloc").value("A"))
                .andExpect(jsonPath("$[1].idBloc").value(2))
                .andExpect(jsonPath("$[1].nomBloc").value("B"));
    }

    @Test
    @DisplayName("Test GET /bloc/retrieve-bloc/{blocId}")
    public void testRetrieveBloc() throws Exception {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(8L);
        bloc.setNomBloc("TestBloc");
        Mockito.when(blocService.retrieveBloc(8L)).thenReturn(bloc);

        // Act & Assert
        mockMvc.perform(get("/bloc/retrieve-bloc/{blocId}", 8L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(8))
                .andExpect(jsonPath("$.nomBloc").value("TestBloc"));
    }

    @Test
    @DisplayName("Test POST /bloc/add-bloc")
    public void testAddBloc() throws Exception {
        // Arrange
        Bloc newBloc = new Bloc();
        newBloc.setNomBloc("New Bloc");

        // simulate that the service returns a bloc with an assigned id
        Bloc savedBloc = new Bloc();
        savedBloc.setIdBloc(9L);
        savedBloc.setNomBloc("New Bloc");

        Mockito.when(blocService.addBloc(ArgumentMatchers.any(Bloc.class)))
                .thenReturn(savedBloc);

        // Act & Assert
        mockMvc.perform(post("/bloc/add-bloc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(9))
                .andExpect(jsonPath("$.nomBloc").value("New Bloc"));
    }

    @Test
    @DisplayName("Test PUT /bloc/update-bloc")
    public void testUpdateBloc() throws Exception {
        // Arrange
        Bloc updatedBloc = new Bloc();
        updatedBloc.setIdBloc(10L);
        updatedBloc.setNomBloc("Updated Bloc");

        Mockito.when(blocService.updateBloc(ArgumentMatchers.any(Bloc.class)))
                .thenReturn(updatedBloc);

        // Act & Assert
        mockMvc.perform(put("/bloc/update-bloc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(10))
                .andExpect(jsonPath("$.nomBloc").value("Updated Bloc"));
    }

    @Test
    @DisplayName("Test DELETE /bloc/removeBloc/{idBloc}")
    public void testRemoveBloc() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/bloc/removeBloc/{idBloc}", 11L))
                .andExpect(status().isOk());

        // Verify that the service method was called
        Mockito.verify(blocService).removeBloc(11L);
    }

    @Test
    @DisplayName("Test GET /bloc/findByFoyerUniversiteIdUniversite/{idUniversite}")
    public void testFindByFoyerUniversiteIdUniversite() throws Exception {
        // Arrange
        Bloc bloc = new Bloc();
        bloc.setIdBloc(12L);
        bloc.setNomBloc("Bloc Universite");
        List<Bloc> blocs = Collections.singletonList(bloc);
        Mockito.when(blocService.findByFoyerUniversiteIdUniversite(1L)).thenReturn(blocs);

        // Act & Assert
        mockMvc.perform(get("/bloc/findByFoyerUniversiteIdUniversite/{idUniversite}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idBloc").value(12))
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc Universite"));
    }

    @Test
    @DisplayName("Test PUT /bloc/affecterChambresABloc/{nomBloc}")
    public void testAffecterChambresABloc() throws Exception {
        // Arrange
        String nomBloc = "C";
        List<Long> numChambres = Arrays.asList(101L, 102L);

        Bloc bloc = new Bloc();
        bloc.setIdBloc(15L);
        bloc.setNomBloc(nomBloc);

        Mockito.when(blocService.affecterChambresABloc(ArgumentMatchers.anyList(), ArgumentMatchers.eq(nomBloc)))
                .thenReturn(bloc);

        // Act & Assert
        mockMvc.perform(put("/bloc/affecterChambresABloc/{nomBloc}", nomBloc)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(numChambres)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(15))
                .andExpect(jsonPath("$.nomBloc").value(nomBloc));
    }
}