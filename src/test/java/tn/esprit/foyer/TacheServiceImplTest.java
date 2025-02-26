package tn.esprit.foyer;

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

        System.out.println("✅ Test réussi : Nouveau montant d'inscription = " + nouveauMontant);
    }
}
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