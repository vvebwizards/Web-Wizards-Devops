package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.*;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        return tacheRepository.findById(idTache)
                .orElseThrow(() -> new NoSuchElementException("No Tache found with id " + idTache));
    }

    @Override
    public void removeTache(Long idTache) {
        log.info("Removing Tache with id {}", idTache);
        tacheRepository.deleteById(idTache);
    }

    @Override
    public void removeTachesByEtudiant(String nom, String prenom) {
        List<Tache> taches = tacheRepository.findTacheByEtudiant(nom, prenom);
        tacheRepository.deleteAll(taches);
    }

    @Override
    public List<Tache> addTachesAndAffectToEtudiant(List<Tache> taches, String nomEt, String prenomEt) {
        Etudiant et = etudiantRepository.findByNomEtAndPrenomEt(nomEt, prenomEt);
        if (et == null) {
            throw new NoSuchElementException("No Etudiant found with name " + nomEt + " " + prenomEt);
        }
        taches.forEach(tache -> tache.setEtudiant(et));
        return tacheRepository.saveAll(taches);
    }

    @Override
    public HashMap<String, Float> calculNouveauMontantInscriptionDesEtudiants() {
        log.info("Calculating new registration amounts for etudiants");
        HashMap<String, Float> nouveauxMontants = new HashMap<>();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        etudiantRepository.findAll().forEach(etudiant -> {
            Float ancienMontant = etudiant.getMontantInscription();
            Float sommeTaches = tacheRepository.sommeTacheAnneeEncours(startDate, endDate, etudiant.getIdEtudiant());
            Float nouveauMontant = (sommeTaches != null) ? ancienMontant - sommeTaches : ancienMontant;

            String nomComplet = etudiant.getNomEt() + " " + etudiant.getPrenomEt();
            nouveauxMontants.put(nomComplet, nouveauMontant);
            log.debug("Étudiant {}: ancien={}, tâches={}, nouveau={}", nomComplet, ancienMontant, sommeTaches, nouveauMontant);
        });

        return nouveauxMontants;
    }

    @Override
    public void updateNouveauMontantInscriptionDesEtudiants() {
        calculNouveauMontantInscriptionDesEtudiants().forEach((nom, montant) -> {
            String[] split = nom.split(" ");
            Etudiant et = etudiantRepository.findByNomEtAndPrenomEt(split[0], split[1]);
            if (et != null) {
                et.setMontantInscription(montant);
                etudiantRepository.save(et);
            }
        });
    }

    public Integer findAllStudents(LocalDate dateDebut, LocalDate dateFin) {
        return (int) tacheRepository.findAll().stream()
                .filter(t -> {
                    LocalDate fin = t.getDateTache().plusDays(t.getDuree());
                    return !t.getDateTache().isBefore(dateDebut) && !fin.isAfter(dateFin);
                }).count();
    }

    @Override
    public float studentsEfficacity(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        List<Tache> terminees = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.TERMINE);
        List<Tache> planifiees = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.PLANIFIE);

        long total = planifiees.stream()
                .filter(t -> !t.getDateTache().isBefore(dateDebut) && !t.getDateTache().plusDays(t.getDuree()).isAfter(dateFin))
                .count();

        long completes = terminees.stream()
                .filter(t -> !t.getDateTache().isBefore(dateDebut) && !t.getDateTache().plusDays(t.getDuree()).isAfter(dateFin))
                .count();

        return total == 0 ? 0 : (completes * 100f / total);
    }

    @Override
    public float studentRevenu(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        return (float) tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.TERMINE).stream()
                .filter(t -> !t.getDateTache().isBefore(dateDebut) && !t.getDateTache().plusDays(t.getDuree()).isAfter(dateFin))
                .mapToDouble(t -> t.getTarifHoraire() * t.getDuree())
                .sum();
    }

    @Override
    public float studentVersatility(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        Set<TypeTache> typesFaits = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.TERMINE).stream()
                .filter(t -> !t.getDateTache().isBefore(dateDebut) && !t.getDateTache().plusDays(t.getDuree()).isAfter(dateFin))
                .map(Tache::getTypeTache)
                .collect(Collectors.toSet());

        return (typesFaits.size() / 3.0f) * 100;
    }

    private float studentPerformance(Etudiant e, LocalDate start, LocalDate end) {
        float e1 = studentsEfficacity(e, start, end);
        float e2 = studentRevenu(e, start, end);
        float e3 = studentVersatility(e, start, end);
        return e1 + e2 + e3;
    }

    @Override
    public LinkedHashMap<Float, List<Etudiant>> studentsPerformanceRanking(LocalDate dateDebut, LocalDate dateFin) {
        Map<Float, List<Etudiant>> performanceMap = new HashMap<>();

        for (Etudiant etudiant : etudiantRepository.findAll()) {
            float performance = studentPerformance(etudiant, dateDebut, dateFin);
            performanceMap.computeIfAbsent(performance, k -> new ArrayList<>()).add(etudiant);
        }

        return performanceMap.entrySet().stream()
                .sorted(Map.Entry.<Float, List<Etudiant>>comparingByKey().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new
                ));
    }

    public Tache findById(Long id) {
        return tacheRepository.findById(id).orElse(null);
    }
}
