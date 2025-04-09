package tn.esprit.foyer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.services.IReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRestController {
    IReservationService reservationService;
    // http://localhost:8089/foyer/reservation/retrieve-all-reservations
    @GetMapping("/retrieve-all-reservations")
    
    public List<Reservation> getReservations() {
        return reservationService.retrieveAllReservations();
    }

    // http://localhost:8089/foyer/reservation/retrieve-reservation/8
    @GetMapping("/retrieve-reservation/{reservationId}")
    
    public Reservation retrieveReservation(@PathVariable("reservationId") String reservationId) {
        return reservationService.retrieveReservation(reservationId);
    }

    // http://localhost:8089/foyer/reservation/add-reservation
    @PostMapping("/add-reservation")
    
    public Reservation addReservation(@RequestBody Reservation r) {
        return reservationService.addReservation(r);
    }

    // http://localhost:8089/foyer/reservation/update-reservation
    @PutMapping("/update-reservation")
    
    public Reservation updateReservation(@RequestBody Reservation r) {
        return reservationService.updateReservation(r);
    }
    // http://localhost:8089/foyer/reservation/removeReservation
    @DeleteMapping("/removeReservation/{idReservation}")
    
    public void removeReservation(@PathVariable("idReservation") String idReservation) {
        reservationService.removeReservation(idReservation);
    }

         // http://localhost:8089/foyer/reservation/ajouterReservationEtAssignerAChambreEtAEtudiant/15/8453621
         @PostMapping("/ajouterReservationEtAssignerAChambreEtAEtudiant/{numChambre}/{cin}")
         
         public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(@RequestBody Reservation r,@PathVariable("numChambre") Long numChambre,@PathVariable("cin") long cin) {
             return reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(r,numChambre,cin);
         }
    // http://localhost:8089/foyer/reservation/getReservationParAnneeUniversitaire/2021-01-01/2021-12-31
    @GetMapping("/getReservationParAnneeUniversitaire/{dateDebut}/{dateFin}")
    
    public List<Reservation> getReservationParAnneeUniversitaire(@PathVariable("dateDebut") LocalDate dateDebut,@PathVariable("dateFin") LocalDate dateFin) {
        return reservationService.getReservationParAnneeUniversitaire(dateDebut,dateFin);
    }
    @GetMapping("/places-disponibles")
    public List<String> getNbPlacesDisponiblesParChambreAnneeEnCours() {
        return reservationService.nbPlacesDisponibleParChambreAnneeEnCours();
    }
}
