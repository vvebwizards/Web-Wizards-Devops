package tn.esprit.foyer.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.time.LocalDate;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.UUIDGenerator.class)
    private String idReservation;
   // @Temporal(TemporalType.DATE)
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy")
    LocalDate anneeUniversitaire;
    Boolean estValid;
    @ManyToMany()
    @JsonIgnore
    private List<Etudiant> etudiants;
    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;

    public Reservation(LocalDate anneeUniversitaire, boolean estValid) {
        this.anneeUniversitaire = anneeUniversitaire;
        this.estValid = estValid; // Initialize the estValid field
    }
}

