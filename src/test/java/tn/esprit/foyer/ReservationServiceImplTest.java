package tn.esprit.foyer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.services.ChambreServiceImpl;
import tn.esprit.foyer.services.ReservationServicImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ReservationServiceImplTest {
    @Autowired
    private ReservationServicImpl resarvationService;

    @Autowired
    private ChambreServiceImpl chambreService;

    @Test
    void testNbPlacesDisponibleParChambreAnneeEnCours() {
        //Creation des reservations
        Reservation r1 = Reservation.builder().idReservation("1").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r2 = Reservation.builder().idReservation("2").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r3 = Reservation.builder().idReservation("3").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r4 = Reservation.builder().idReservation("4").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r5 = Reservation.builder().idReservation("5").anneeUniversitaire(LocalDate.now()).estValid(true).build();
        Reservation r6 = Reservation.builder().idReservation("6").anneeUniversitaire(LocalDate.now()).estValid(true).build();

        //Sauvegarde des reservations
        Reservation savedR1 = resarvationService.addReservation(r1);
        Reservation savedR2 = resarvationService.addReservation(r2);
        Reservation savedR3 = resarvationService.addReservation(r3);
        Reservation savedR4 = resarvationService.addReservation(r4);
        Reservation savedR5 = resarvationService.addReservation(r5);
        Reservation savedR6 = resarvationService.addReservation(r6);

        //verifie que les reservations ont ete bien ajouté dans la table
        Assertions.assertNotNull(savedR1.getIdReservation());
        Assertions.assertNotNull(savedR2.getIdReservation());
        Assertions.assertNotNull(savedR3.getIdReservation());
        Assertions.assertNotNull(savedR4.getIdReservation());
        Assertions.assertNotNull(savedR5.getIdReservation());
        Assertions.assertNotNull(savedR6.getIdReservation());

        List<Reservation> reservations1 = new ArrayList<>();
        List<Reservation> reservations2 = new ArrayList<>();
        List<Reservation> reservations3 = new ArrayList<>();
        reservations1.add(savedR1); reservations1.add(savedR2);
        reservations2.add(savedR3); reservations2.add(savedR4);
        reservations3.add(savedR5); reservations3.add(savedR6);

        //Creation des chambres
        Chambre ch1 = Chambre.builder().numeroChambre(1L).typeC(TypeChambre.SIMPLE).reservations(reservations1).build();
        Chambre ch2 = Chambre.builder().numeroChambre(2L).typeC(TypeChambre.DOUBLE).reservations(reservations2).build();
        Chambre ch3 = Chambre.builder().numeroChambre(3L).typeC(TypeChambre.TRIPLE).reservations(reservations3).build();

        //Sauvegarde des chambres
        Chambre savedCh1 = chambreService.addChambre(ch1);
        Chambre savedCh2 = chambreService.addChambre(ch2);
        Chambre savedCh3 = chambreService.addChambre(ch3);

        //verifie que les chambres ont ete bien ajouté dans la table
        Assertions.assertNotNull(savedCh1.getIdChambre());
        Assertions.assertNotNull(savedCh2.getIdChambre());
        Assertions.assertNotNull(savedCh3.getIdChambre());

        //Expected Value var
        Map<Long, Integer> expectedResult = new HashMap<>();
        expectedResult.put(1L, 0);
        expectedResult.put(2L, 0);
        expectedResult.put(3L, 1);

        //Verifie que le resultat est comme il est supposé ou pas
        Assertions.assertEquals(expectedResult, resarvationService.nbPlacesDisponibleParChambreAnneeEnCours());

        //J'ai juste ajouté ces codes la pour debug
        System.out.println("---------------------------------------");
        System.out.println("Expected value: " + expectedResult);
        System.out.println("Real value: " + resarvationService.nbPlacesDisponibleParChambreAnneeEnCours());
        System.out.println("---------------------------------------");

        //Suppression de Reservations & Chambres
        chambreService.removeChambre(savedCh1.getIdChambre());
        chambreService.removeChambre(savedCh2.getIdChambre());
        chambreService.removeChambre(savedCh3.getIdChambre());

        resarvationService.removeReservation(savedR1.getIdReservation());
        resarvationService.removeReservation(savedR2.getIdReservation());
        resarvationService.removeReservation(savedR3.getIdReservation());
        resarvationService.removeReservation(savedR4.getIdReservation());
        resarvationService.removeReservation(savedR5.getIdReservation());
        resarvationService.removeReservation(savedR6.getIdReservation());
    }
}
