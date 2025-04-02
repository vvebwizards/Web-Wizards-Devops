package tn.esprit.foyer.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@RequiredArgsConstructor
public class Tache implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache; // Clé primaire
    @NonNull
    private LocalDate dateTache;
    @NonNull
     Integer duree;
     Float tarifHoraire;
     @Enumerated(EnumType.STRING)
     TypeTache typeTache;

    @ManyToOne()
    @JsonIgnore
    Etudiant etudiant;

    @Enumerated(EnumType.STRING)
    EtatTache etatTache;


    public Tache(Long idTache,  LocalDate dateTache,  Integer duree, Float tarifHoraire, TypeTache typeTache, Etudiant etudiant, EtatTache etatTache) {
        this.idTache = idTache;
        this.dateTache = dateTache;
        this.duree = duree;
        this.tarifHoraire = tarifHoraire;
        this.typeTache = typeTache;
        this.etudiant = etudiant;
        this.etatTache = etatTache;
    }
}

