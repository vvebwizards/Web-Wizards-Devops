package tn.esprit.foyer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.foyer.entities.EtatTache;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;
import tn.esprit.foyer.repository.TacheRepository;
import tn.esprit.foyer.services.EtudiantServiceImpl;
import tn.esprit.foyer.services.TacheServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class TacheServiceImplTest {

@Autowired
    private TacheServiceImpl tacheService ;

@Autowired
    private EtudiantServiceImpl etudiantService;
    @Test
    void testStudentsPerformanceRanking() {
        LocalDate dateDebut = LocalDate.of(2023, 4, 1);
        LocalDate dateFin = LocalDate.now();

        Etudiant etudiant1 = new Etudiant("wiem", "bm", "esprit");
        Etudiant etudiant2 = new Etudiant("rihab", "ben mansour", "esprit");
        etudiantService.addEtudiant(etudiant1);
        etudiantService.addEtudiant(etudiant2);
        Tache tache1 = new Tache(
                1L,
                LocalDate.of(2023, 4, 1),
                10,
                10.0f,
                TypeTache.MENAGERE,
                etudiant1,
                EtatTache.TERMINE
        );
        Tache tache2 = new Tache(
                2L,
                LocalDate.of(2023, 4, 1),
                10,
                10.0f,
                TypeTache.MENAGERE,
                etudiant1,
                EtatTache.PLANIFIE
        );
        Tache tache3 = new Tache(
                3L,
                LocalDate.of(2023, 4, 1),
                10,
                10.0f,
                TypeTache.MENAGERE,
                etudiant1,
                EtatTache.PLANIFIE
        );
        List<Tache> tacheList = new ArrayList<>();
        tacheList.add(tache1);
        tacheList.add(tache2);
        tacheService.addTachesAndAffectToEtudiant(tacheList,  etudiant1.getNomEt(), etudiant1.getPrenomEt());
        List<Tache> tacheList2 = new ArrayList<>();
        tacheList2.add(tache3);
        tacheService.addTachesAndAffectToEtudiant(tacheList2, etudiant2.getNomEt(), etudiant2.getPrenomEt());

        LinkedHashMap<Float, List<Etudiant>> result = tacheService.studentsPerformanceRanking(dateDebut, dateFin);
        assertNotNull(result);
        assertEquals(2,result.keySet().size());
        List<Float> scores = new ArrayList<>(result.keySet());
        for (int i = 1; i < scores.size(); i++) {
            assertTrue(scores.get(i - 1) >= scores.get(i), "Students are not sorted in ascending order.");
        }
        tacheService.removeTachesByEtudiant(etudiant1.getNomEt(), etudiant1.getPrenomEt());
        etudiantService.removeEtudiant(etudiant1.getNomEt(), etudiant1.getPrenomEt());
        tacheService.removeTachesByEtudiant( etudiant2.getNomEt(), etudiant2.getPrenomEt());
        etudiantService.removeEtudiant( etudiant2.getNomEt(), etudiant2.getPrenomEt());
    }


}
