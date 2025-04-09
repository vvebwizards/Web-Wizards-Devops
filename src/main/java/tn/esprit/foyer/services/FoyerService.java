package tn.esprit.foyer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.FoyerRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Service
@AllArgsConstructor
@Slf4j
public class FoyerService implements IFoyerService{

    FoyerRepository foyerRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {

        log.info("in method retrieveAllFoyers");
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
        return foyerRepository.findById(idFoyer).orElseThrow(() -> new EntityNotFoundException("Foyer not found with id: " + idFoyer));
    }
    @Override
    public void removeFoyer(Long idFoyer) {
           foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer addFoyerWithBloc(Foyer f) {
        return null;
    }
}
