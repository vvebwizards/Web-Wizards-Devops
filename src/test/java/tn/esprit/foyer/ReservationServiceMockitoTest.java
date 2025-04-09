package tn.esprit.foyer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.services.ReservationServicImpl;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockitoTest {

    @InjectMocks
    private ReservationServicImpl reservationService;

    @Mock
    private ChambreRepository chambreRepository;


    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_Mockito() {

        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.DOUBLE);

        Reservation reservation = new Reservation(LocalDate.now(), true);
        reservation.setChambre(chambre);
        chambre.setReservations(Arrays.asList(reservation));

        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));


        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();


        assertTrue(result.stream().anyMatch(s -> s.contains("101") && s.contains("1")));
    }
    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_MultipleReservations_Mockito() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(606L);
        chambre.setTypeC(TypeChambre.TRIPLE);

        Reservation reservation1 = new Reservation(LocalDate.now(), true);
        Reservation reservation2 = new Reservation(LocalDate.now(), true);
        chambre.setReservations(Arrays.asList(reservation1, reservation2));

        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();

        assertTrue(result.stream().anyMatch(s -> s.contains("606") && s.contains("1")));
    }

    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_InvalidReservations_Mockito() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(707L);
        chambre.setTypeC(TypeChambre.SIMPLE);

        Reservation reservation = new Reservation(LocalDate.now(), false);
        chambre.setReservations(Arrays.asList(reservation));

        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();

        assertTrue(result.stream().anyMatch(s -> s.contains("707") && s.contains("1")));
    }

    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_OldReservations_Mockito() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(808L);
        chambre.setTypeC(TypeChambre.DOUBLE);

        Reservation reservation = new Reservation(LocalDate.of(2023, 5, 15), true);
        chambre.setReservations(Arrays.asList(reservation));

        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();


        assertTrue(result.stream().anyMatch(s -> s.contains("808") && s.contains("2")));
    }


}
