package tn.esprit.foyer;
/*
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;
import tn.esprit.foyer.services.EtudiantServiceImpl;
import tn.esprit.foyer.services.TacheServiceImpl;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TacheServiceImplTest {

    @Autowired
    private EtudiantServiceImpl etudiantService;

    @Autowired
    private TacheServiceImpl tacheService;

    @Test
    @Order(1)
    void testCalculNouveauMontantInscriptionDesEtudiants() {
        // Étape 1 : Ajouter un étudiant avec un montant d'inscription initial
        Etudiant etudiant = new Etudiant("Nour", "Tr", "Esprit", 100.0f);
        etudiantService.addEtudiant(etudiant);

        // Vérifier que l'étudiant est bien enregistré
        Etudiant etudiantSaved = etudiantService.findById(etudiant.getIdEtudiant());
        assertNotNull(etudiantSaved, "L'étudiant n'est pas enregistré !");
        assertEquals(100.0f, etudiantSaved.getMontantInscription(), "Le montant initial est incorrect"); // ✅ Correction

        // Étape 2 : Ajouter une tâche pour cet étudiant
        Tache tache = new Tache(
                null,
                LocalDate.of(LocalDate.now().getYear(), 4, 1), // Date de la tâche
                3, // Durée en heures
                10.0f, // Tarif horaire
                TypeTache.MENAGERE,
                etudiantSaved,
                null
        );
        tacheService.addTache(tache);

        // Vérifier que la tâche est bien enregistrée
        Tache tacheSaved = tacheService.findById(tache.getIdTache());
        assertNotNull(tacheSaved, "La tâche n'est pas enregistrée !");
        assertEquals(10.0f, tacheSaved.getTarifHoraire(), "Le tarif horaire est incorrect");

        // Étape 3 : Exécuter le calcul du nouveau montant d'inscription
        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();
        assertNotNull(result, "Le résultat ne doit pas être nul");

        //  Calcul attendu : 100 - (3h * 10) = 100 - 30 = 70
        Float nouveauMontant = result.get("Nour Tr");
        assertNotNull(nouveauMontant, "Le montant mis à jour ne doit pas être NULL !");
        assertEquals(70.0f, nouveauMontant, "Le montant mis à jour est incorrect"); // ✅ Validation correcte

        tacheService.removeTache(tacheSaved.getIdTache());
        etudiantService.removeEtudiant(etudiantSaved.getIdEtudiant());
        System.out.println("✅ Test réussi : Nouveau montant d'inscription = " + nouveauMontant);
    }
}*/
    /*void testCalculNouveauMontantInscriptionDesEtudiants() {
        // Étape 1 : Ajouter un étudiant avec un montant d'inscription initial
        Etudiant etudiant = new Etudiant("Nour", "Tr", "Esprit", 50.0f);
        etudiantService.addEtudiant(etudiant);

        // Vérifier que l'étudiant est bien enregistré
        Etudiant etudiantSaved = etudiantService.findById(etudiant.getIdEtudiant());
        assertNotNull(etudiantSaved, "L'étudiant n'est pas enregistré !");
        assertEquals(50.0f, etudiantSaved.getMontantInscription(),  "Le montant initial est incorrect");

        // Étape 2 : Ajouter une tâche pour cet étudiant
        Tache tache = new Tache(
                null,
                LocalDate.of(LocalDate.now().getYear(), 4, 1), // Date de la tâche
                5, // Durée en heures
                10.0f, // Tarif horaire
                TypeTache.MENAGERE,
                etudiantSaved,
                null
        );
        tacheService.addTache(tache);

        // Vérifier que la tâche est bien enregistrée
        Tache tacheSaved = tacheService.findById(tache.getIdTache());
        assertNotNull(tacheSaved, "La tâche n'est pas enregistrée !");
        assertEquals(10.0f, tacheSaved.getTarifHoraire(), "Le tarif horaire est incorrect");

        // Étape 3 : Vérifier le nombre d'étudiants et de tâches en base
      //  System.out.println("Nombre d'étudiants en base : " + etudiantService.getAllEtudiants().size());
      //  System.out.println("Nombre de tâches en base : " + tacheService.getAllTaches().size());

        // Étape 4 : Exécuter le calcul du nouveau montant d'inscription
        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();
        assertNotNull(result, "Le résultat ne doit pas être nul");

        // Calcul attendu : 50 - (5h * 10) = 50 - 50 = 0
        Float nouveauMontant = result.get("Nour Tr");
        assertNotNull(nouveauMontant, "Le montant mis à jour ne doit pas être NULL !");
        assertEquals(0.0f, nouveauMontant, "Le montant mis à jour est incorrect");

        System.out.println("✅ Test réussi : Nouveau montant d'inscription = " + nouveauMontant);
    }
}
*/
/*package tn.esprit.foyer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;

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

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TacheServiceImplTest {

    @Autowired
    private EtudiantServiceImpl etudiantService;

    @Autowired
    private TacheServiceImpl tacheService;

    @Test
    @Order(1)
    void testCalculNouveauMontantInscriptionDesEtudiants() {
        // Étape 1 : Ajouter un étudiant avec un montant d'inscription initial
        Etudiant etudiant = new Etudiant("Nour", "Tr", "Esprit", 100.0f);
        etudiantService.addEtudiant(etudiant);

        // Vérifier que l'étudiant est bien enregistré
        Etudiant etudiantSaved = etudiantService.findById(etudiant.getIdEtudiant());
        assertNotNull(etudiantSaved, "L'étudiant n'est pas enregistré !");
        assertEquals(100.0f, etudiantSaved.getMontantInscription(), "Le montant initial est incorrect"); // ✅ Correction

        // Étape 2 : Ajouter une tâche pour cet étudiant
        Tache tache = new Tache(
                null,
                LocalDate.of(LocalDate.now().getYear(), 4, 1), // Date de la tâche
                3, // Durée en heures
                10.0f, // Tarif horaire
                TypeTache.MENAGERE,
                etudiantSaved,
                null
        );
        tacheService.addTache(tache);

        // Vérifier que la tâche est bien enregistrée
        Tache tacheSaved = tacheService.findById(tache.getIdTache());
        assertNotNull(tacheSaved, "La tâche n'est pas enregistrée !");
        assertEquals(10.0f, tacheSaved.getTarifHoraire(), "Le tarif horaire est incorrect");

        // Étape 3 : Exécuter le calcul du nouveau montant d'inscription
        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();
        assertNotNull(result, "Le résultat ne doit pas être nul");

        //  Calcul attendu : 100 - (3h * 10) = 100 - 30 = 70
        Float nouveauMontant = result.get("Nour Tr");
        assertNotNull(nouveauMontant, "Le montant mis à jour ne doit pas être NULL !");
        assertEquals(70.0f, nouveauMontant, "Le montant mis à jour est incorrect"); // ✅ Validation correcte

        tacheService.removeTache(tacheSaved.getIdTache());
        etudiantService.removeEtudiant(etudiantSaved.getIdEtudiant());
        System.out.println("✅ Test réussi : Nouveau montant d'inscription = " + nouveauMontant);
    }
}*/
    /*void testCalculNouveauMontantInscriptionDesEtudiants() {
        // Étape 1 : Ajouter un étudiant avec un montant d'inscription initial
        Etudiant etudiant = new Etudiant("Nour", "Tr", "Esprit", 50.0f);
        etudiantService.addEtudiant(etudiant);

        // Vérifier que l'étudiant est bien enregistré
        Etudiant etudiantSaved = etudiantService.findById(etudiant.getIdEtudiant());
        assertNotNull(etudiantSaved, "L'étudiant n'est pas enregistré !");
        assertEquals(50.0f, etudiantSaved.getMontantInscription(),  "Le montant initial est incorrect");

        // Étape 2 : Ajouter une tâche pour cet étudiant
        Tache tache = new Tache(
                null,
                LocalDate.of(LocalDate.now().getYear(), 4, 1), // Date de la tâche
                5, // Durée en heures
                10.0f, // Tarif horaire
                TypeTache.MENAGERE,
                etudiantSaved,
                null
        );
        tacheService.addTache(tache);

        // Vérifier que la tâche est bien enregistrée
        Tache tacheSaved = tacheService.findById(tache.getIdTache());
        assertNotNull(tacheSaved, "La tâche n'est pas enregistrée !");
        assertEquals(10.0f, tacheSaved.getTarifHoraire(), "Le tarif horaire est incorrect");

        // Étape 3 : Vérifier le nombre d'étudiants et de tâches en base
      //  System.out.println("Nombre d'étudiants en base : " + etudiantService.getAllEtudiants().size());
      //  System.out.println("Nombre de tâches en base : " + tacheService.getAllTaches().size());

        // Étape 4 : Exécuter le calcul du nouveau montant d'inscription
        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();
        assertNotNull(result, "Le résultat ne doit pas être nul");

        // Calcul attendu : 50 - (5h * 10) = 50 - 50 = 0
        Float nouveauMontant = result.get("Nour Tr");
        assertNotNull(nouveauMontant, "Le montant mis à jour ne doit pas être NULL !");
        assertEquals(0.0f, nouveauMontant, "Le montant mis à jour est incorrect");

        System.out.println("✅ Test réussi : Nouveau montant d'inscription = " + nouveauMontant);
    }
*/

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
import tn.esprit.foyer.services.EtudiantServiceImpl;
import tn.esprit.foyer.services.TacheServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

