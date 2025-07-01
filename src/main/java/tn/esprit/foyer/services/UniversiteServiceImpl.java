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

@Service
@AllArgsConstructor
@Slf4j
public class UniversiteServiceImpl implements IUniversiteService {

    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversites() {
        log.info("Starting retrieveAllUniversites method");
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        log.info("Adding new Universite with name: {}", u.getNomUniversite());
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        log.info("Updating Universite with id: {}", u.getIdUniversite());
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(Long idUniversite) {
        log.info("Retrieving Universite with id: {}", idUniversite);
        // Using orElseThrow to avoid potential null-pointer issues.
        return universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new NoSuchElementException("No Universite found with id " + idUniversite));
    }

    @Override
    public void removeUniversite(Long idUniversite) {
        log.info("Removing Universite with id: {}", idUniversite);
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        log.info("Affecting Foyer with id {} to Universite named {}", idFoyer, nomUniversite);
        // Retrieve Foyer with proper error handling.
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new NoSuchElementException("No Foyer found with id " + idFoyer));

        // Retrieve Universite based on its name. Depending on your repository implementation,
        // you may want to handle the case where the Universite is not found.
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new NoSuchElementException("No Universite found with name " + nomUniversite);
        }

        // Set the association on the Foyer side and persist the change.
        foyer.setUniversite(universite);
        foyerRepository.save(foyer);
        log.info("Foyer with id {} successfully affected to Universite {}", idFoyer, nomUniversite);
        return universite;
    }

    @Override
    public void desaffecterFoyerAUniversite(long idFoyer) {
        log.info("Desaffecting Foyer with id {} from its Universite", idFoyer);
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new NoSuchElementException("No Foyer found with id " + idFoyer));

        foyer.setUniversite(null);
        foyerRepository.save(foyer);
        log.info("Foyer with id {} has been desaffecté from its Universite", idFoyer);
    }
}
