package tn.esprit.foyer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.services.IUniversiteService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/universite")
public class UniversiteRestController {
    IUniversiteService universiteService;
    // http://localhost:8089/foyer/universite/retrieve-all-universites
    @GetMapping("/retrieve-all-universites")

    public List<Universite> getUniversites() {
        return universiteService.retrieveAllUniversites();
    }

    // http://localhost:8089/foyer/universite/retrieve-universite/8
    @GetMapping("/retrieve-universite/{universiteId}")

    public Universite retrieveUniversite(@PathVariable("universiteId") Long universiteId) {
        return universiteService.retrieveUniversite(universiteId);
    }

    // http://localhost:8089/foyer/universite/add-universite
    @PostMapping("/add-universite")

    public Universite addUniversite(@RequestBody Universite u) {
        return universiteService.addUniversite(u);
    }

    // http://localhost:8089/foyer/universite/update-universite
    @PutMapping("/update-universite")

    public Universite updateUniversite(@RequestBody Universite u) {
        return universiteService.updateUniversite(u);
    }
    // http://localhost:8089/foyer/universite/removeUniversite
    @DeleteMapping("/removeUniversite/{idUniversite}")

    public void removeUniversite(@PathVariable("idUniversite") Long idUniversite) {
        universiteService.removeUniversite(idUniversite);
    }

    // http://localhost:8089/foyer/universite/affecterFoyerAUniversite/1/esprit
    @PutMapping("/affecterFoyerAUniversite/{idFoyer}/{nomUniversite}")

    public Universite affecterFoyerAUniversite (@PathVariable("idFoyer") long idFoyer,
                                                @PathVariable("nomUniversite") String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer,nomUniversite);
    }

    // http://localhost:8089/foyer/universite/desaffecterFoyerAUniversite/1/1
    @PutMapping("/desaffecterFoyerAUniversite/{idFoyer}")
    public Universite desaffecterFoyerAUniversite (@PathVariable("idFoyer") long idFoyer) {
        universiteService.desaffecterFoyerAUniversite(idFoyer);
        return null;
    }

}