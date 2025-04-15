package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class TacheServiceImpl implements ITacheService {

    private final TacheRepository tacheRepository;
    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Tache> retrieveAllTaches() {
        log.info("Retrieving all Taches");
        return tacheRepository.findAll();
    }

    @Override
    public Tache addTache(Tache t) {
        log.info("Adding new Tache");
        return tacheRepository.save(t);
    }

    @Override
    public Tache updateTache(Tache t) {
        log.info("Updating Tache with id {}", t.getIdTache());
        return tacheRepository.save(t);
    }

    @Override
    public Tache retrieveTache(Long idTache) {
        log.info("Retrieving Tache with id {}", idTache);
        // Using Optional to throw an exception if Tache not found.
        return tacheRepository.findById(idTache)
                .orElseThrow(() -> new NoSuchElementException("No Tache found with id " + idTache));
    }

    @Override
    public void removeTache(Long idTache) {
        log.info("Removing Tache with id {}", idTache);
        tacheRepository.deleteById(idTache);
    }

    @Override
    public List<Tache> addTachesAndAffectToEtudiant(List<Tache> taches, String nomEt, String prenomEt) {
        log.info("Affecting Taches to Etudiant {} {}", nomEt, prenomEt);
        Etudiant etudiant = etudiantRepository.findByNomEtAndPrenomEt(nomEt, prenomEt);
        if (etudiant == null) {
            throw new NoSuchElementException("No Etudiant found with name " + nomEt + " " + prenomEt);
        }
        // Set the etudiant for each Tache.
        taches.forEach(tache -> tache.setEtudiant(etudiant));
        List<Tache> savedTaches = tacheRepository.saveAll(taches);
        log.info("Affected {} Taches to Etudiant {} {}", savedTaches.size(), nomEt, prenomEt);
        return savedTaches;
    }

    @Override
    public HashMap<String, Float> calculNouveauMontantInscriptionDesEtudiants() {
        log.info("Calculating new registration amounts for etudiants");
        HashMap<String, Float> nouveauxMontantsInscription = new HashMap<>();

        // For each etudiant, calculate the new registration amount for the current year.
        etudiantRepository.findAll().forEach(etudiant -> {
            Float ancienMontant = etudiant.getMontantInscription();
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
            Float montantTachesAssignesAnneeEnCours = tacheRepository.sommeTacheAnneeEncours(startDate, endDate, etudiant.getIdEtudiant());

            // If there are tasks costs, subtract them from the registration fee.
            Float nouveauMontant = ancienMontant;
            if (montantTachesAssignesAnneeEnCours != null) {
                nouveauMontant = ancienMontant - montantTachesAssignesAnneeEnCours;
            }

            String etudiantFullName = etudiant.getNomEt() + " " + etudiant.getPrenomEt();
            nouveauxMontantsInscription.put(etudiantFullName, nouveauMontant);
            log.debug("Etudiant {}: ancienMontant={}, montantTaches={}, nouveauMontant={}",
                    etudiantFullName, ancienMontant, montantTachesAssignesAnneeEnCours, nouveauMontant);
        });

        return nouveauxMontantsInscription;
    }

}
