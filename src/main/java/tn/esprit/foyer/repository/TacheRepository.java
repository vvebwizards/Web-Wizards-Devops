package tn.esprit.foyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.EtatTache;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface TacheRepository extends JpaRepository<Tache,Long> {
    @Query("select sum(t.tarifHoraire*t.duree) from Tache t where t.dateTache " +
            "between :t1 and :t2 and t.etudiant.idEtudiant=:idEtudiant")
    Float sommeTacheAnneeEncours(@Param("t1") LocalDate t1,
                                 @Param("t2")LocalDate t2,
                                 @Param("idEtudiant")Long idEtudiant);
    @Query("SELECT t FROM Tache t WHERE t.etatTache = :etatTache and t.etudiant=:etudiant")
    List<Tache> findAllByEtatTacheAndEtudiant(@Param("etudiant") Etudiant etudiant, @Param("etatTache") EtatTache etatTache);

    @Query("SELECT t FROM Tache t WHERE t.etudiant.nomEt = :nom and t.etudiant.prenomEt=:prenom")
    List<Tache> findTacheByEtudiant(@Param("nom") String nom , @Param("prenom") String prenom);

    List<Tache> findAllByDateTacheBetween(LocalDate startDate, LocalDate endDate);
}
