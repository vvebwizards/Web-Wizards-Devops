package tn.esprit.foyer.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationServicImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;
    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation retrieveReservation(String idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public void removeReservation(String idReservation) {
        reservationRepository.deleteById(idReservation);
    }

    @Transactional
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Reservation res, Long numChambre, long cin) {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        Etudiant e = etudiantRepository.findByCin(cin);
        Chambre c = chambreRepository.findByNumeroChambre(numChambre);

        res.setIdReservation(numChambre + e.getCin().toString() + LocalDate.now().getYear());
        res.setEstValid(true);

        List<Etudiant> etudiants = new ArrayList<>();
        if (res.getEtudiants() != null) {
            etudiants.addAll(res.getEtudiants());
        }
        etudiants.add(e);
        res.setEtudiants(etudiants);

        if (c.getReservations() != null) {
            Integer reservationSize = reservationRepository.getReservationsCurrentYear(startDate, endDate, numChambre);
            switch (reservationSize) {
                case 0 -> {
                    log.info("case reservation vide");
                    Reservation r = reservationRepository.save(res);
                    c.getReservations().add(r);
                    chambreRepository.save(c);
                }
                case 1 -> {
                    log.info("case reservation courante égale à 1");
                    if (c.getTypeC().equals(TypeChambre.DOUBLE) || c.getTypeC().equals(TypeChambre.TRIPLE)) {
                        Reservation r1 = reservationRepository.save(res);
                        c.getReservations().add(r1);
                        chambreRepository.save(c);
                    } else {
                        log.info("chambre simple déjà réservée");
                    }
                }
                case 2 -> {
                    log.info("case reservation courante égale à 2");
                    if (c.getTypeC().equals(TypeChambre.TRIPLE)) {
                        Reservation r2 = reservationRepository.save(res);
                        c.getReservations().add(r2);
                        chambreRepository.save(c);
                    } else {
                        log.info("chambre double déjà complète");
                    }
                }
                default -> log.info("Capacité chambre atteinte");
            }
        } else {
            Reservation r = reservationRepository.save(res);
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(r);
            c.setReservations(reservations);
            chambreRepository.save(c);
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaire(LocalDate dateDebut, LocalDate dateFin) {
        return reservationRepository.findByAnneeUniversitaireBetween(dateDebut, dateFin);
    }

    @Override
    public Map<Long, Integer> nbPlacesDisponibleParChambreAnneeEnCours() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateDebut = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalDate dateFin = LocalDate.of(currentDate.getYear(), 12, 31);

        List<Chambre> chambresDisponibles = chambreRepository.findAll();
        Map<Long, Integer> placesRestantes = new HashMap<>();

        for (Chambre chambre : chambresDisponibles) {
            int nbChambresOccupes = 0;

            if (chambre.getReservations() != null) {
                nbChambresOccupes = (int) chambre.getReservations().stream()
                        .filter(reservation -> reservation.getEstValid() &&
                                (reservation.getAnneeUniversitaire().isAfter(dateDebut.minusDays(1)) &&
                                 reservation.getAnneeUniversitaire().isBefore(dateFin.plusDays(1))))
                        .count();
            }

            int capacite = switch (chambre.getTypeC()) {
                case SIMPLE -> 1;
                case DOUBLE -> 2;
                case TRIPLE -> 3;
            };

            placesRestantes.put(chambre.getNumeroChambre(), Math.max(0, capacite - nbChambresOccupes));
        }

        return placesRestantes;
    }
}
