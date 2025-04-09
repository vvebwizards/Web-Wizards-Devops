package tn.esprit.foyer.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.services.IFoyerService;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/foyer")

public class FoyerRestController {

    IFoyerService foyerService;
    // http://localhost:8089/foyer/foyer/retrieve-all-foyers
    @GetMapping("/retrieve-all-foyers")
    
    public List<Foyer> getFoyers() {
        return foyerService.retrieveAllFoyers();
    }

    // http://localhost:8089/foyer/foyer/retrieve-foyer/8
    @GetMapping("/retrieve-foyer/{foyerId}")
    
    public Foyer retrieveFoyer(@PathVariable("foyerId") Long foyerId) {
        return foyerService.retrieveFoyer(foyerId);
    }

    // http://localhost:8089/foyer/foyer/add-foyer
    @PostMapping("/add-foyer")
    
    public Foyer addFoyer(@RequestBody Foyer f) {
        return foyerService.addFoyer(f);
    }

    // http://localhost:8089/foyer/foyer/add-foyer-with-bloc
    @PostMapping("/add-foyer-with-bloc")
    
    public Foyer addFoyerWithBloc(@RequestBody Foyer f) {
        return foyerService.addFoyerWithBloc(f);
    }

    // http://localhost:8089/foyer/foyer/update-foyer
    @PutMapping("/update-foyer")
    
    public Foyer updateFoyer(@RequestBody Foyer f) {
        return foyerService.updateFoyer(f);
    }
    // http://localhost:8089/foyer/foyer/removeFoyer
    @DeleteMapping("/removeFoyer/{idFoyer}")
    
    public void removeFoyer(@PathVariable("idFoyer") Long idFoyer) {
        foyerService.removeFoyer(idFoyer);
    }
}
