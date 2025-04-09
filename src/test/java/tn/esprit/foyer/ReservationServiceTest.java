package tn.esprit.foyer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
 class ReservationServiceTest {

    @Autowired
    private ReservationServicImpl reservationService;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_MySQL() {
        // Préparation des données
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        chambre = chambreRepository.save(chambre);

        Reservation reservation = new Reservation(LocalDate.now(), true);
        reservation.setChambre(chambre);
        reservationRepository.save(reservation);


        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();


        assertTrue(result.stream().anyMatch(s -> s.contains("101") && s.contains("1")));
    }
    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_MultipleReservations() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(202L);
        chambre.setTypeC(TypeChambre.DOUBLE);
        chambre = chambreRepository.save(chambre);

        Reservation reservation1 = new Reservation(LocalDate.now(), true);
        reservation1.setChambre(chambre);
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation(LocalDate.now(), true);
        reservation2.setChambre(chambre);
        reservationRepository.save(reservation2);

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();


        assertTrue(result.stream().anyMatch(s -> s.contains("202") && s.contains("0")));
    }

    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_ReservationsOutsideCurrentYear() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(303L);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre = chambreRepository.save(chambre);


        Reservation reservation = new Reservation(LocalDate.of(2023, 12, 31), true);
        reservation.setChambre(chambre);
        reservationRepository.save(reservation);

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();


        assertTrue(result.stream().anyMatch(s -> s.contains("303") && s.contains("1")));
    }

    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_TripleRoomPartialOccupation() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(404L);
        chambre.setTypeC(TypeChambre.TRIPLE);
        chambre = chambreRepository.save(chambre);

        Reservation reservation = new Reservation(LocalDate.now(), true);
        reservation.setChambre(chambre);
        reservationRepository.save(reservation);

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();

        assertTrue(result.stream().anyMatch(s -> s.contains("404") && s.contains("2")));
    }

    @Test
     void testNbPlacesDisponibleParChambreAnneeEnCours_NoReservations() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(505L);
        chambre.setTypeC(TypeChambre.DOUBLE);

        List<String> result = reservationService.nbPlacesDisponibleParChambreAnneeEnCours();
        
        assertTrue(result.stream().anyMatch(s -> s.contains("505") && s.contains("2")));
    }

}

