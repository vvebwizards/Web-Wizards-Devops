package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface ITacheService {

    List<Tache> retrieveAllTaches();
    Tache addTache(Tache t);
    Tache updateTache(Tache t);
    Tache retrieveTache(Long idTache);
    void removeTache(Long idTache);
    void removeTachesByEtudiant(String nom , String prenom);
     List<Tache>  addTachesAndAffectToEtudiant (List<Tache> taches, String nomEt, String prenomEt ) ;

    HashMap<String,Float > calculNouveauMontantInscriptionDesEtudiants() ;

    void updateNouveauMontantInscriptionDesEtudiants();
    float studentsEfficacity(Etudiant etudiant,LocalDate dateDebut,LocalDate dateFin);
    float studentRevenu(Etudiant etudiant,LocalDate dateDebut,LocalDate dateFin);
    float studentVersatility(Etudiant etudiant,LocalDate dateDebut,LocalDate dateFin);
    public LinkedHashMap<Float, List<Etudiant>> studentsPerformanceRanking(LocalDate dateDebut, LocalDate dateFin);
    Integer findAllStudents(LocalDate dateDebut, LocalDate dateFin);

}
