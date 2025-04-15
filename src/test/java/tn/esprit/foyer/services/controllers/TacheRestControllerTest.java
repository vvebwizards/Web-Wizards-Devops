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
import tn.esprit.foyer.controllers.TacheRestController;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.services.ITacheService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TacheRestController.class)
public class TacheRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ITacheService tacheService;

    @Test
    @DisplayName("Test GET /tache/retrieve-all-taches")
    public void testGetAllTaches() throws Exception {
        // Arrange
        Tache tache1 = new Tache();
        tache1.setIdTache(1L);
        // add other tache1 properties as needed

        Tache tache2 = new Tache();
        tache2.setIdTache(2L);
        // add other tache2 properties as needed

        List<Tache> taches = Arrays.asList(tache1, tache2);
        Mockito.when(tacheService.retrieveAllTaches()).thenReturn(taches);

        // Act & Assert
        mockMvc.perform(get("/tache/retrieve-all-taches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idTache").value(1))
                .andExpect(jsonPath("$[1].idTache").value(2));
    }

    @Test
    @DisplayName("Test GET /tache/retrieve-tache/{tacheId}")
    public void testRetrieveTache() throws Exception {
        // Arrange
        Tache tache = new Tache();
        tache.setIdTache(8L);
        // set additional properties if available

        Mockito.when(tacheService.retrieveTache(8L)).thenReturn(tache);

        // Act & Assert
        mockMvc.perform(get("/tache/retrieve-tache/{tacheId}", 8L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTache").value(8));
    }

    
    @Test
    @DisplayName("Test DELETE /tache/removeTache/{idTache}")
    public void testRemoveTache() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/tache/removeTache/{idTache}", 11L))
                .andExpect(status().isOk());

        Mockito.verify(tacheService).removeTache(11L);
    }


    @Test
    @DisplayName("Test GET /tache/calculNouveauMontantInscriptionDesEtudiants")
    public void testCalculNouveauMontantInscriptionDesEtudiants() throws Exception {
        // Arrange
        HashMap<String, Float> expectedMap = new HashMap<>();
        expectedMap.put("nouvMontant", 1200.50f);
        // simulate service call
        Mockito.when(tacheService.calculNouveauMontantInscriptionDesEtudiants())
                .thenReturn(expectedMap);

        // Act & Assert
        mockMvc.perform(get("/tache/calculNouveauMontantInscriptionDesEtudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nouvMontant").value(1200.50));
    }
}