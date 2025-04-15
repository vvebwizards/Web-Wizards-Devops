package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.FoyerRepository;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FoyerService implements IFoyerService{

    private static final Logger logger = LoggerFactory.getLogger(FoyerService.class);
    private final FoyerRepository foyerRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        logger.info("Entering retrieveAllFoyers method");
        return foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer retrieveFoyer(Long idFoyer) {
        return foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new NoSuchElementException("No Foyer found with id " + idFoyer));
    }

    @Override
    public void removeFoyer(Long idFoyer) {
        foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer addFoyerWithBloc(Foyer f) {
        throw new UnsupportedOperationException("addFoyerWithBloc method is not yet implemented");
    }
}
