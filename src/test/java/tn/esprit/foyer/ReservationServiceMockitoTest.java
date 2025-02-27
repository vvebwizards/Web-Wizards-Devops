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
import tn.esprit.foyer.repository.ReservationRepository;
import tn.esprit.foyer.services.ReservationServicImpl;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceMockitoTest {

    @InjectMocks
    private ReservationServicImpl reservationService;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    public void testNbPlacesDisponibleParChambreAnneeEnCours_Mockito() {
        // Préparation des données simulées
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.DOUBLE);

        Reservation reservation = new Reservation(LocalDate.now(), true);
        reservation.setChambre(chambre);
        chambre.setReservations(Arrays.asList(reservation));

        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        // Exécution de la méthode à tester
        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();

        // Vérification des résultats
        assertTrue(result.stream().anyMatch(s -> s.contains("101") && s.contains("1")));
    }
}
