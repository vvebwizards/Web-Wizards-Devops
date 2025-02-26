package tn.esprit.foyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;
import tn.esprit.foyer.services.TacheServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TacheServiceMockitoImpl {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private TacheRepository tacheRepository;

    @InjectMocks
    private TacheServiceImpl tacheService;

    private Etudiant etudiant;
    private Tache tache;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant("Nour", "Tr", "Esprit", 110.0f);
        etudiant.setIdEtudiant(1L);

        tache = new Tache(
                1L,
                LocalDate.of(LocalDate.now().getYear(), 4, 1),
                3,
                10.0f,
                TypeTache.MENAGERE,
                etudiant,
                null
        );
    }

    @Test
    void testCalculNouveauMontantInscriptionDesEtudiants() {
        // récupération de l'étudiant depuis le repository
        when(etudiantRepository.findAll()).thenReturn(List.of(etudiant));

        // calcul de la somme des tâches
        when(tacheRepository.sommeTacheAnneeEncours(any(), any(), eq(etudiant.getIdEtudiant())))
                .thenReturn(30.0f); // 3h * 10 = 30

        // Exécuter la méthode de service
        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();

        // Vérifier que le résultat est bien généré
        assertNotNull(result, "Le résultat ne doit pas être nul");
        assertTrue(result.containsKey("Nour Tr"), "L'étudiant doit être dans le résultat");

        // Vérifier que le calcul est correct : 100 - 30 = 70€
        Float nouveauMontant = result.get("Nour Tr");
        assertNotNull(nouveauMontant, "Le montant mis à jour ne doit pas être NULL !");
        assertEquals(80.0f, nouveauMontant, "Le montant mis à jour est incorrect");
        

        System.out.println("✅ Test réussi avec Mockito : Nouveau montant d'inscription = " + nouveauMontant);
    }
}




