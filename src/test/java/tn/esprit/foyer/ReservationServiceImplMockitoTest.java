package tn.esprit.foyer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.ReservationRepository;
import tn.esprit.foyer.services.ChambreServiceImpl;
import tn.esprit.foyer.services.ReservationServicImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ReservationServiceImplMockitoTest {

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServicImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNbPlacesDisponibleParChambreAnneeEnCours() {
        // Mock Reservations using Builder
        Reservation r1 = Reservation.builder().idReservation("1").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r2 = Reservation.builder().idReservation("2").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r3 = Reservation.builder().idReservation("3").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r4 = Reservation.builder().idReservation("4").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r5 = Reservation.builder().idReservation("5").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r6 = Reservation.builder().idReservation("6").anneeUniversitaire(LocalDate.now()).estValid(true).build();

        // Mock Chambres
        List<Reservation> reservations1 = Arrays.asList(r1, r2);
        List<Reservation> reservations2 = Arrays.asList(r3, r4);
        List<Reservation> reservations3 = Arrays.asList(r5, r6);

        Chambre ch1 = Chambre.builder().numeroChambre(1L).typeC(TypeChambre.SIMPLE).reservations(reservations1).build();
        Chambre ch2 = Chambre.builder().numeroChambre(2L).typeC(TypeChambre.DOUBLE).reservations(reservations2).build();
        Chambre ch3 = Chambre.builder().numeroChambre(3L).typeC(TypeChambre.TRIPLE).reservations(reservations3).build();

        // Initialisation of the variables
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(r1);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(r2);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(r3);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(r4);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(r5);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(r6);
        Mockito.when(chambreRepository.findById(1L)).thenReturn(Optional.ofNullable(ch1));
        Mockito.when(chambreRepository.findById(2L)).thenReturn(Optional.ofNullable(ch2));
        Mockito.when(chambreRepository.findById(3L)).thenReturn(Optional.ofNullable(ch3));

        // Expected Results
        Map<Long, Integer> expectedResult = new HashMap<>();
        expectedResult.put(1L, 0);
        expectedResult.put(2L, 0);
        expectedResult.put(3L, 1);

        // Assertions
        assertEquals(expectedResult, reservationService.nbPlacesDisponibleParChambreAnneeEnCours());

        // Verify Calls
        verify(reservationService, times(1)).nbPlacesDisponibleParChambreAnneeEnCours();
    }
}
