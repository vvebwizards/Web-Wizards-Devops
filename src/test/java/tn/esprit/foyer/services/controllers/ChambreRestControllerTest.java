package tn.esprit.foyer.services.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.foyer.controllers.ChambreRestController;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.services.IChambreService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Use @WebMvcTest to slice only the web layer (i.e., controllers)
@WebMvcTest(ChambreRestController.class)
 class ChambreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization/deserialization

    // Mock the service that the controller depends on.
    @MockBean
    private IChambreService chambreService;

    @Test
    @DisplayName("Test retrieve-all-chambres endpoint")
     void testRetrieveAllChambres() throws Exception {
        // Prepare mock data
        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeC(TypeChambre.SIMPLE);

        Chambre chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeC(TypeChambre.DOUBLE);

        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);
        Mockito.when(chambreService.retrieveAllChambres()).thenReturn(chambres);

        // Perform GET request and expect JSON response
        mockMvc.perform(get("/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idChambre").value(1))
                .andExpect(jsonPath("$[0].numeroChambre").value(101))
                .andExpect(jsonPath("$[0].typeC").value("SIMPLE"))
                .andExpect(jsonPath("$[1].idChambre").value(2));
    }

    @Test
    @DisplayName("Test retrieve-chambre by ID endpoint")
     void testRetrieveChambreById() throws Exception {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(8L);
        chambre.setNumeroChambre(108L);
        chambre.setTypeC(TypeChambre.DOUBLE);

        Mockito.when(chambreService.retrieveChambre(8L)).thenReturn(chambre);

        mockMvc.perform(get("/chambre/retrieve-chambre/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(8))
                .andExpect(jsonPath("$.numeroChambre").value(108))
                .andExpect(jsonPath("$.typeC").value("DOUBLE"));
    }

    @Test
    @DisplayName("Test add-chambre endpoint")
     void testAddChambre() throws Exception {
        Chambre newChambre = new Chambre();
        newChambre.setNumeroChambre(109L);
        newChambre.setTypeC(TypeChambre.SIMPLE);
        // Mock the service to return the same chambre with an ID
        newChambre.setIdChambre(9L);
        Mockito.when(chambreService.addChambre(any(Chambre.class))).thenReturn(newChambre);

        mockMvc.perform(post("/chambre/add-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newChambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(9))
                .andExpect(jsonPath("$.numeroChambre").value(109));
    }

    @Test
    @DisplayName("Test update-chambre endpoint")
     void testUpdateChambre() throws Exception {
        Chambre updatedChambre = new Chambre();
        updatedChambre.setIdChambre(10L);
        updatedChambre.setNumeroChambre(110L);
        updatedChambre.setTypeC(TypeChambre.DOUBLE);
        Mockito.when(chambreService.updateChambre(any(Chambre.class))).thenReturn(updatedChambre);

        mockMvc.perform(put("/chambre/update-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedChambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(10))
                .andExpect(jsonPath("$.numeroChambre").value(110));
    }

    @Test
    @DisplayName("Test removeChambre endpoint")
     void testRemoveChambre() throws Exception {
        // You can simply simulate a successful deletion by returning nothing.
        mockMvc.perform(delete("/chambre/removeChambre/11"))
                .andExpect(status().isOk());
        // Optionally you can verify that the service delete method was called.
        Mockito.verify(chambreService).removeChambre(11L);
    }

    @Test
    @DisplayName("Test findByTypeCAndBlocIdBloc endpoint")
     void testFindByTypeCAndBlocIdBloc() throws Exception {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(12L);
        chambre.setNumeroChambre(112L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        List<Chambre> chambres = Collections.singletonList(chambre);
        Mockito.when(chambreService.findByTypeCAndBlocIdBloc(TypeChambre.DOUBLE, 1L)).thenReturn(chambres);

        mockMvc.perform(get("/chambre/findByTypeCAndBlocIdBloc/DOUBLE/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChambre").value(12));
    }

    // Additional tests for other endpoints follow a similar pattern:

    @Test
    @DisplayName("Test findByReservationsEstValid endpoint")
     void testFindByReservationsEstValid() throws Exception {
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.findByReservationsEstValid(true)).thenReturn(chambres);

        mockMvc.perform(get("/chambre/findByReservationsEstValid/true"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test findByBlocIdBlocAndBlocCapaciteBloc endpoint")
     void testFindByBlocIdBlocAndBlocCapaciteBloc() throws Exception {
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(1L, 100L)).thenReturn(chambres);

        mockMvc.perform(get("/chambre/findByBlocIdBlocAndBlocCapaciteBloc/1/100"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getChambresParNomBloc endpoint")
     void testGetChambresParNomBloc() throws Exception {
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.getChambresParNomBloc("A")).thenReturn(chambres);

        mockMvc.perform(get("/chambre/getChambresParNomBloc/A"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test nbChambreParTypeEtBloc endpoint")
     void testNbChambreParTypeEtBloc() throws Exception {
        Mockito.when(chambreService.nbChambreParTypeEtBloc(TypeChambre.DOUBLE, 1L)).thenReturn(5L);

        mockMvc.perform(get("/chambre/nbChambreParTypeEtBloc/DOUBLE/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));  // expecting the long value as a string
    }

    @Test
    @DisplayName("Test getChambresNonReserveParNomFoyerEtTypeChambre endpoint")
     void testGetChambresNonReserveParNomFoyerEtTypeChambre() throws Exception {
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("esprit foyer", TypeChambre.SIMPLE))
                .thenReturn(chambres);

        mockMvc.perform(get("/chambre/getChambresNonReserveParNomFoyerEtTypeChambre/esprit foyer/SIMPLE"))
                .andExpect(status().isOk());
    }
}
