package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UniversiteServiceImpl implements IUniversiteService{
    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;
    @Override
    public List<Universite> retrieveAllUniversites() {
        log.info("debut methode retrieveAllUniversites");
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {

        log.info("debut methode updateUniversite");
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(Long idUniversite) {
        return universiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public void removeUniversite(Long idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        // récupérer les objets à partir des primitifs
        Foyer f = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new RuntimeException("Foyer not found with id: " + idFoyer));
        var universite = universiteRepository.findByNomUniversite(nomUniversite);
       // identifier le parent du child
        // parent.setChild()
        f.setUniversite(universite);
        // sauvegarde l'objet modifié
        foyerRepository.save(f);
        log.info("fin methode affecterFoyerAUniversite");
        return universite;
    }

    @Override
    public Long desaffecterFoyerAUniversite(long idFoyer) {
        Optional<Foyer> optionalFoyer = foyerRepository.findById(idFoyer);

        if (optionalFoyer.isPresent()) {
            var f = optionalFoyer.get();
            f.setUniversite(null);
            foyerRepository.save(f);
            return f.getIdFoyer();
        } else {
            throw new NoSuchElementException("Foyer not found with ID: " + idFoyer);
        }
    }

}
