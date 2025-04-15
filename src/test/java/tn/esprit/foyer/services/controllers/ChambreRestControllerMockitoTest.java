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
import tn.esprit.foyer.controllers.ChambreRestController;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.services.IChambreService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChambreRestController.class)
 class ChambreRestControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IChambreService chambreService;

    @Test
    @DisplayName("Test GET /chambre/retrieve-all-chambres")
     void testGetChambres() throws Exception {
        // Arrange
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

        // Act & Assert
        mockMvc.perform(get("/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idChambre").value(1))
                .andExpect(jsonPath("$[0].numeroChambre").value(101))
                .andExpect(jsonPath("$[0].typeC").value("SIMPLE"))
                .andExpect(jsonPath("$[1].idChambre").value(2))
                .andExpect(jsonPath("$[1].typeC").value("DOUBLE"));
    }

    @Test
    @DisplayName("Test GET /chambre/retrieve-chambre/{chambreId}")
     void testRetrieveChambre() throws Exception {
        // Arrange
        Chambre chambre = new Chambre();
        chambre.setIdChambre(8L);
        chambre.setNumeroChambre(108L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        Mockito.when(chambreService.retrieveChambre(8L)).thenReturn(chambre);

        // Act & Assert
        mockMvc.perform(get("/chambre/retrieve-chambre/{chambreId}", 8L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(8))
                .andExpect(jsonPath("$.numeroChambre").value(108))
                .andExpect(jsonPath("$.typeC").value("DOUBLE"));
    }

    @Test
    @DisplayName("Test POST /chambre/add-chambre")
     void testAddChambre() throws Exception {
        // Arrange
        Chambre newChambre = new Chambre();
        newChambre.setNumeroChambre(109L);
        newChambre.setTypeC(TypeChambre.SIMPLE);
        // Mocking service to return the same chambre with an ID assigned
        newChambre.setIdChambre(9L);
        Mockito.when(chambreService.addChambre(ArgumentMatchers.any(Chambre.class)))
                .thenReturn(newChambre);

        // Act & Assert
        mockMvc.perform(post("/chambre/add-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newChambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(9))
                .andExpect(jsonPath("$.numeroChambre").value(109));
    }

    @Test
    @DisplayName("Test PUT /chambre/update-chambre")
     void testUpdateChambre() throws Exception {
        // Arrange
        Chambre updatedChambre = new Chambre();
        updatedChambre.setIdChambre(10L);
        updatedChambre.setNumeroChambre(110L);
        updatedChambre.setTypeC(TypeChambre.DOUBLE);
        Mockito.when(chambreService.updateChambre(ArgumentMatchers.any(Chambre.class)))
                .thenReturn(updatedChambre);

        // Act & Assert
        mockMvc.perform(put("/chambre/update-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedChambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(10))
                .andExpect(jsonPath("$.numeroChambre").value(110));
    }

    @Test
    @DisplayName("Test DELETE /chambre/removeChambre/{idChambre}")
     void testRemoveChambre() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/chambre/removeChambre/{idChambre}", 11L))
                .andExpect(status().isOk());

        // Verify interaction
        Mockito.verify(chambreService).removeChambre(11L);
    }

    @Test
    @DisplayName("Test GET /chambre/findByTypeCAndBlocIdBloc/{typeChambre}/{idBloc}")
     void testFindByTypeCAndBlocIdBloc() throws Exception {
        // Arrange
        Chambre chambre = new Chambre();
        chambre.setIdChambre(12L);
        chambre.setNumeroChambre(112L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        List<Chambre> chambres = Collections.singletonList(chambre);
        Mockito.when(chambreService.findByTypeCAndBlocIdBloc(TypeChambre.DOUBLE, 1L))
                .thenReturn(chambres);

        // Act & Assert
        mockMvc.perform(get("/chambre/findByTypeCAndBlocIdBloc/{typeChambre}/{idBloc}", "DOUBLE", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChambre").value(12));
    }

    @Test
    @DisplayName("Test GET /chambre/findByReservationsEstValid/{estValid}")
     void testFindByReservationsEstValid() throws Exception {
        // Arrange
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.findByReservationsEstValid(true))
                .thenReturn(chambres);

        // Act & Assert
        mockMvc.perform(get("/chambre/findByReservationsEstValid/{estValid}", true))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test GET /chambre/findByBlocIdBlocAndBlocCapaciteBloc/{idBloc}/{capaciteBloc}")
     void testFindByBlocIdBlocAndBlocCapaciteBloc() throws Exception {
        // Arrange
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(1L, 100L))
                .thenReturn(chambres);

        // Act & Assert
        mockMvc.perform(get("/chambre/findByBlocIdBlocAndBlocCapaciteBloc/{idBloc}/{capaciteBloc}", 1L, 100L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test GET /chambre/getChambresParNomBloc/{nomBloc}")
     void testGetChambresParNomBloc() throws Exception {
        // Arrange
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.getChambresParNomBloc("A"))
                .thenReturn(chambres);

        // Act & Assert
        mockMvc.perform(get("/chambre/getChambresParNomBloc/{nomBloc}", "A"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test GET /chambre/nbChambreParTypeEtBloc/{type}/{idBloc}")
     void testNbChambreParTypeEtBloc() throws Exception {
        // Arrange
        Mockito.when(chambreService.nbChambreParTypeEtBloc(TypeChambre.DOUBLE, 1L))
                .thenReturn(5L);

        // Act & Assert
        mockMvc.perform(get("/chambre/nbChambreParTypeEtBloc/{type}/{idBloc}", "DOUBLE", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    @DisplayName("Test GET /chambre/getChambresNonReserveParNomFoyerEtTypeChambre/{nomFoyer}/{type}")
     void testGetChambresNonReserveParNomFoyerEtTypeChambre() throws Exception {
        // Arrange
        List<Chambre> chambres = Collections.singletonList(new Chambre());
        Mockito.when(chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("esprit foyer", TypeChambre.SIMPLE))
                .thenReturn(chambres);

        // Act & Assert
        mockMvc.perform(get("/chambre/getChambresNonReserveParNomFoyerEtTypeChambre/{nomFoyer}/{type}", "esprit foyer", "SIMPLE"))
                .andExpect(status().isOk());
    }
}