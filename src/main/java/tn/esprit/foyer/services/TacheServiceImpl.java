package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.EtatTache;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TacheServiceImpl implements ITacheService{

    TacheRepository tacheRepository;
    EtudiantRepository etudiantRepository;
    @Override
    public List<Tache> retrieveAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache addTache(Tache t) {
        return tacheRepository.save(t);
    }

    @Override
    public Tache updateTache(Tache t) {
        return tacheRepository.save(t);
    }

    @Override
    public Tache retrieveTache(Long idTache) {
        return tacheRepository.findById(idTache).get();
    }

    @Override
    public void removeTache(Long idTache) {
        tacheRepository.deleteById(idTache);
    }

    @Override
    public void removeTachesByEtudiant(String nom, String prenom) {
       List<Tache> taches = tacheRepository.findTacheByEtudiant(nom,prenom);
        tacheRepository.deleteAll(taches);
    }


    public Integer findAllStudents(LocalDate dateDebut,LocalDate dateFin) {
        List<Tache> tacheList= tacheRepository.findAll();
        for (Tache tache : tacheList) {
            LocalDate dateFinTache = tache.getDateTache().plusDays(tache.getDuree());

            if (!tache.getDateTache().isBefore(dateDebut) && !dateFinTache.isAfter(dateFin)) {
                log.info("dateeeeeeeeeeeeeeeeeeeeeeeeeeeeee compar",tache.getDateTache().isBefore(dateDebut));
                return 1;

            }
        }
        return 0;
    }
    @Override
    public List<Tache> addTachesAndAffectToEtudiant(List<Tache> taches, String nomEt, String prenomEt) {
        Etudiant et = etudiantRepository.findByNomEtAndPrenomEt(nomEt,prenomEt);
        taches.forEach(tache -> {
            tache.setEtudiant(et);
          //  tacheRepository.save(tache);
        });
        tacheRepository.saveAll(taches);
        return taches;
    }

    @Override
    public HashMap<String, Float> calculNouveauMontantInscriptionDesEtudiants() {
        HashMap<String, Float> nouveauxMontantsInscription = new HashMap<>();
        etudiantRepository.findAll().forEach(etudiant -> {
            Float ancienMontant= etudiant.getMontantInscription();
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1,1);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12,31);
            Float montantTachesAssignesAnneeEnCours = tacheRepository.
                    sommeTacheAnneeEncours(startDate,endDate,etudiant.getIdEtudiant());
            Float nouveauMontant = ancienMontant;
            if (montantTachesAssignesAnneeEnCours!=null) {
                 nouveauMontant = ancienMontant - montantTachesAssignesAnneeEnCours;
            }
            nouveauxMontantsInscription.put(etudiant.getNomEt()+" "+etudiant.getPrenomEt(),
                    nouveauMontant);
        });
        return nouveauxMontantsInscription;
    }

    //@Scheduled(cron = "0 30 14 09 09 *")
    public void updateNouveauMontantInscriptionDesEtudiants() {
        etudiantRepository.findAll().forEach(etudiant -> {
            Float montantInscription= etudiant.getMontantInscription();
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1,1);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12,31);
            Float montantTachesAssignesAnneeEnCours = tacheRepository.sommeTacheAnneeEncours(startDate,endDate,etudiant.getIdEtudiant());
            if (montantTachesAssignesAnneeEnCours!=null) {
                montantInscription = montantInscription - montantTachesAssignesAnneeEnCours;
                etudiant.setMontantInscription(montantInscription);
                etudiantRepository.save(etudiant);
            }

        });
    }
    @Override
    public float studentsEfficacity(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        List<Tache> tacheListTerminé = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.TERMINE);
        List<Tache> tacheListPlanifie = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.PLANIFIE);

        float totalTachesPlanifie = 0;
        float tachesCompletees = 0;

        for (Tache task : tacheListPlanifie) {
            LocalDate dateFinTache = task.getDateTache().plusDays(task.getDuree());
            if (!task.getDateTache().isBefore(dateDebut) && !dateFinTache.isAfter(dateFin)) {
                totalTachesPlanifie++;
            }
        }
        for (Tache task : tacheListTerminé) {
            LocalDate dateFinTache = task.getDateTache().plusDays(task.getDuree());
            if (!task.getDateTache().isBefore(dateDebut) && !dateFinTache.isAfter(dateFin)) {
                tachesCompletees++;
            }
        }
        if (totalTachesPlanifie == 0) {
            return 0;
        }

        return (tachesCompletees / totalTachesPlanifie) * 100;
    }

    @Override
    public float studentRevenu(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        List<Tache> tasks = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.TERMINE);
        float totalRevenue = 0;

        for (Tache task : tasks) {
            LocalDate dateFinTache = task.getDateTache().atStartOfDay().plusHours(task.getDuree()).toLocalDate();
            if (!task.getDateTache().isBefore(dateDebut) && !dateFinTache.isAfter(dateFin) ) {
                totalRevenue += task.getTarifHoraire() * task.getDuree();
                log.info("revenu resultttttttt: {}", totalRevenue);
            }
        }

        return totalRevenue;
    }

    @Override
    public float studentVersatility(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        List<Tache> tacheList = tacheRepository.findAllByEtatTacheAndEtudiant(etudiant, EtatTache.TERMINE);

        float nbTacheMenagere = 0;
        float nbTacheJardinage = 0;
        float nbTacheBricolage = 0;

        for (Tache tache : tacheList) {
            LocalDate dateFinTache = tache.getDateTache().atStartOfDay().plusHours(tache.getDuree()).toLocalDate();
            if (!tache.getDateTache().isBefore(dateDebut) && !dateFinTache.isAfter(dateFin)) {
                if (tache.getTypeTache() == TypeTache.MENAGERE) {
                    nbTacheMenagere++;
                } else if (tache.getTypeTache() == TypeTache.JARDINAGE) {
                    nbTacheJardinage++;
                } else if (tache.getTypeTache() == TypeTache.BRICOLAGE) {
                    nbTacheBricolage++;
                }
            }
        }

        float totalTypesPerformed = 0;
        if (nbTacheMenagere > 0) totalTypesPerformed++;
        if (nbTacheJardinage > 0) totalTypesPerformed++;
        if (nbTacheBricolage > 0) totalTypesPerformed++;

        return (totalTypesPerformed / 3.0f) * 100;
    }

    public float studentPerformance(Etudiant etudiant, LocalDate dateDebut, LocalDate dateFin) {
        float efficacity = this.studentsEfficacity(etudiant, dateDebut, dateFin);
        float revenu = this.studentRevenu(etudiant, dateDebut, dateFin);
        float versatility = this.studentVersatility(etudiant, dateDebut, dateFin);

        log.info("Efficiency result: {}", efficacity);

        log.info("revenu result: {}", revenu);
        float result=efficacity + revenu + versatility;
        log.info("Versatility result: {}", result);
        return result;
    }


    @Override
    public LinkedHashMap<Float, List<Etudiant>> studentsPerformanceRanking(LocalDate dateDebut, LocalDate dateFin) {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        Map<Float, List<Etudiant>> performanceMap = new HashMap<>();

        for (Etudiant etudiant : etudiants) {
            float performance = this.studentPerformance(etudiant, dateDebut, dateFin);
            performanceMap.computeIfAbsent(performance, k -> new ArrayList<>())
                    .add(etudiant);
        }

        return performanceMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Float, List<Etudiant>>comparingByKey().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


}

