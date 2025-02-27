package tn.esprit.foyer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.ReservationRepository;
import tn.esprit.foyer.services.ReservationServicImpl;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationServicImpl reservationService;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testNbPlacesDisponibleParChambreAnneeEnCours_MySQL() {
        // Préparation des données
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        chambre = chambreRepository.save(chambre);

        Reservation reservation = new Reservation(LocalDate.now(), true);
        reservation.setChambre(chambre);
        reservationRepository.save(reservation);

        // Exécution de la méthode à tester
        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();

        // Vérification des résultats
        assertTrue(result.stream().anyMatch(s -> s.contains("101") && s.contains("1")));
    }
}

