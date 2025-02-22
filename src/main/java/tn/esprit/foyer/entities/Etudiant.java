package tn.esprit.foyer.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Etudiant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant; // Clé primaire
    @NonNull
    private String nomEt;
    @NonNull
     String prenomEt;
     Long cin;
     String ecole;
     LocalDate dateNaissance;



    @OneToMany(mappedBy = "etudiant")
    @JsonIgnore
    Set<Tache> tacheSet;

    Float montantInscription;
    @Enumerated(EnumType.STRING)
    TypeEtudiant typeEtudiant;

    @ManyToMany(mappedBy = "etudiants",fetch = FetchType.EAGER)
    @JsonIgnore
    List<Reservation> reservations;

    public Etudiant(String nomEt, String prenomEt, String ecole ) {
        this.nomEt = nomEt;
        this.prenomEt = prenomEt;
        this.ecole = ecole;
    }





}

