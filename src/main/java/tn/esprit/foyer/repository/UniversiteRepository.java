package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.Universite;




@Repository
public interface UniversiteRepository extends JpaRepository<Universite,Long> {
    //List<Universite> findByFoyerAnd();
    Universite findByNomUniversite(String nomUniversite);

}
