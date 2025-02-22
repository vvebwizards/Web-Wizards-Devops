package tn.esprit.foyer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.EtatTache;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.TypeTache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;
import tn.esprit.foyer.services.EtudiantServiceImpl;
import tn.esprit.foyer.services.TacheServiceImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class TacheServiceImpMockitoTest {


    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private TacheServiceImpl tacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStudentsPerformanceRanking() {
        // Arrange
        LocalDate dateDebut = LocalDate.of(2023, 1, 1);
        LocalDate dateFin = LocalDate.of(2026, 12, 31);

        Etudiant etudiant1 = new Etudiant("John", "Doe", "School1");
        Etudiant etudiant2 = new Etudiant("Jane", "Doe", "School2");

        Tache tache1 = new Tache(1L, LocalDate.of(2024, 6, 1), 5, 10.0f, TypeTache.MENAGERE, etudiant1, EtatTache.TERMINE);
        Tache tache2 = new Tache(2L, LocalDate.of(2024, 7, 1), 3, 15.0f, TypeTache.JARDINAGE, etudiant2, EtatTache.PLANIFIE);

        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        when(tacheRepository.findAllByEtatTacheAndEtudiant(etudiant1, EtatTache.TERMINE)).thenReturn(Arrays.asList(tache1));
        when(tacheRepository.findAllByEtatTacheAndEtudiant(etudiant1, EtatTache.PLANIFIE)).thenReturn(Arrays.asList(tache1));
        when(tacheRepository.findAllByEtatTacheAndEtudiant(etudiant2, EtatTache.TERMINE)).thenReturn(Arrays.asList(tache2));
        when(tacheRepository.findAllByEtatTacheAndEtudiant(etudiant2, EtatTache.PLANIFIE)).thenReturn(Arrays.asList(tache2));

        LinkedHashMap<Float, List<Etudiant>> result = tacheService.studentsPerformanceRanking(dateDebut, dateFin);

        assertNotNull(result);
        assertEquals(2, result.size());
        List<Etudiant> topPerformers = result.values().iterator().next();
        assertEquals(etudiant1, topPerformers.get(0));
    }
}