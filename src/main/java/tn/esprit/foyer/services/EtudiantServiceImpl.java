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
        log.info("Adding new etudiant");
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        log.info("Updating etudiant");
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant retrieveEtudiant(Long idEtudiant) {
        log.info("Retrieving etudiant with id {}", idEtudiant);
        return etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new EntityNotFoundExceptionById("No Etudiant found with id " + idEtudiant));
    }

    public Etudiant findById(Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    @Override
    public void removeEtudiant(Long idEtudiant) {
        if (!etudiantRepository.existsById(idEtudiant)) {
            throw new EntityNotFoundExceptionById("Invalid Id Etudiant was provided");
        }
        log.info("Removing etudiant with id {}", idEtudiant);
        etudiantRepository.deleteById(idEtudiant);
    }

    public void removeEtudiant(String nom, String prenom) {
        Etudiant etudiant = etudiantRepository.findByNomEtAndPrenomEt(nom, prenom);
        if (etudiant != null) {
            etudiantRepository.deleteById(etudiant.getIdEtudiant());
        }
    }

    @Override
    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) {
        log.info("Adding list of etudiants");
        return etudiantRepository.saveAll(etudiants);
    }

    @Override
    public Etudiant affecterEtudiantAReservation(String nomEt, String prenomEt, String idReservation) {
        // Vérifie si l'étudiant existe
        Etudiant etudiant = etudiantRepository.findByNomEtAndPrenomEt(nomEt, prenomEt);
        if (etudiant == null) {
            throw new EntityNotFoundExceptionById("No Etudiant found with name: " + nomEt + " " + prenomEt);
        }

        // Vérifie si la réservation existe
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() ->
                        new EntityNotFoundExceptionById("No Reservation found with id " + idReservation));

        // Évite les nulls sur la liste
        List<Etudiant> etudiantsList = reservation.getEtudiants();
        if (etudiantsList == null) {
            etudiantsList = new ArrayList<>();
        }

        etudiantsList.add(etudiant);
        reservation.setEtudiants(etudiantsList);
        reservationRepository.save(reservation);

        log.info("Etudiant {} {} assigned to reservation {}", nomEt, prenomEt, idReservation);
        return etudiant;
    }
}
