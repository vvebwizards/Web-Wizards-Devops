package tn.esprit.foyer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.services.ITacheService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tache")
public class TacheRestController {

    private final ITacheService tacheService;

    // http://localhost:8089/foyer/tache/retrieve-all-taches
    @GetMapping("/retrieve-all-taches")
    public List<Tache> getTaches() {
        return tacheService.retrieveAllTaches();
    }

    // http://localhost:8089/foyer/tache/retrieve-tache/8
    @GetMapping("/retrieve-tache/{tacheId}")
    public Tache retrieveTache(@PathVariable("tacheId") Long tacheId) {
        return tacheService.retrieveTache(tacheId);
    }

    // http://localhost:8089/foyer/tache/add-tache
    @PostMapping("/add-tache")
    public Tache addTache(@RequestBody Tache t) {
        return tacheService.addTache(t);
    }

    // http://localhost:8089/foyer/tache/update-tache
    @PutMapping("/update-tache")
    public Tache updateTache(@RequestBody Tache t) {
        return tacheService.updateTache(t);
    }

    // http://localhost:8089/foyer/tache/removeTache/5
    @DeleteMapping("/removeTache/{idTache}")
    public void removeTache(@PathVariable("idTache") Long idTache) {
        tacheService.removeTache(idTache);
    }

    // http://localhost:8089/foyer/tache/addTachesAndAffectToEtudiant/Ahmed/Zouari
    @PostMapping("/addTachesAndAffectToEtudiant/{nomEt}/{prenomEt}")
    public List<Tache> addTachesAndAffectToEtudiant(
            @RequestBody List<Tache> taches,
            @PathVariable("nomEt") String nomEt,
            @PathVariable("prenomEt") String prenomEt) {
        return tacheService.addTachesAndAffectToEtudiant(taches, nomEt, prenomEt);
    }

    // http://localhost:8089/foyer/tache/calculNouveauMontantInscriptionDesEtudiants
    @GetMapping("/calculNouveauMontantInscriptionDesEtudiants")
    public HashMap<String, Float> calculNouveauMontantInscriptionDesEtudiants() {
        return tacheService.calculNouveauMontantInscriptionDesEtudiants();
    }

    // http://localhost:8089/foyer/tache/studentsPerformanceRanking/2023-01-01/2023-12-31
    @GetMapping("/studentsPerformanceRanking/{dateDebut}/{dateFin}")
    public LinkedHashMap<Float, List<Etudiant>> studentsPerformanceRanking(
            @PathVariable LocalDate dateDebut,
            @PathVariable LocalDate dateFin) {
        return tacheService.studentsPerformanceRanking(dateDebut, dateFin);
    }

    // http://localhost:8089/foyer/tache/taches/2023-01-01/2023-12-31
    @GetMapping("/taches/{dateDebut}/{dateFin}")
    public Integer students(@PathVariable("dateDebut") LocalDate dateDebut,
                            @PathVariable("dateFin") LocalDate dateFin) {
        return tacheService.findAllStudents(dateDebut, dateFin);
    }

    // http://localhost:8089/foyer/tache/updateNouveauMontantInscriptionDesEtudiants
    @PostMapping("/updateNouveauMontantInscriptionDesEtudiants")
    public void updateNouveauMontantInscriptionDesEtudiants() {
        tacheService.updateNouveauMontantInscriptionDesEtudiants();
    }
}
