package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.configuration.EntityNotFoundExceptionById;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final FoyerRepository foyerRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        log.info("Retrieving all etudiants");
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant addEtudiant(Etudiant e) {
        log.info("Starting addEtudiant");
        // Additional processing (e.g., tranche Age calculation) can be added here.
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        log.info("Starting updateEtudiant");
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant retrieveEtudiant(Long idEtudiant) {
        log.info("Retrieving etudiant with id {}", idEtudiant);
        // Using orElseThrow to handle cases where the etudiant is not found.
        return etudiantRepository.findById(idEtudiant)
                .orElseThrow(() ->
                        new EntityNotFoundExceptionById("No Etudiant found with id " + idEtudiant));
    }

    @Override
    public void removeEtudiant(Long idEtudiant) {
        if (!etudiantRepository.existsById(idEtudiant)) {
            throw new EntityNotFoundExceptionById("Invalid Id Etudiant was provided");
        }
        log.info("Removing etudiant with id {}", idEtudiant);
        etudiantRepository.deleteById(idEtudiant);
    }

    @Override
    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) {
        log.info("Starting addEtudiants");
        List<Etudiant> savedEtudiants = etudiantRepository.saveAll(etudiants);
        log.info("Finished addEtudiants");
        return savedEtudiants;
    }

    @Override
    public Etudiant affecterEtudiantAReservation(String nomEt, String prenomEt, String idReservation) {
        // Retrieve the etudiant and validate; throw exception if not found.
        Etudiant etudiant = etudiantRepository.findByNomEtAndPrenomEt(nomEt, prenomEt);
        if (etudiant == null) {
            throw new EntityNotFoundExceptionById("No Etudiant found with name: " + nomEt + " " + prenomEt);
        }

        // Retrieve reservation and throw exception if not found.
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() ->
                        new EntityNotFoundExceptionById("No Reservation found with id " + idReservation));

        // Prepare the list to avoid null pointer issues.
        List<Etudiant> etudiantsList = reservation.getEtudiants();
        if (etudiantsList == null) {
            etudiantsList = new ArrayList<>();
        }
        etudiantsList.add(etudiant);
        reservation.setEtudiants(etudiantsList);

        reservationRepository.save(reservation);
        log.info("Etudiant {} {} affected to reservation {}", nomEt, prenomEt, idReservation);
        return etudiant;
    }
}
