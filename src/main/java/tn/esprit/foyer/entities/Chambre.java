package tn.esprit.foyer.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long idChambre; // Clé primaire
    Long numeroChambre;
    @Enumerated(EnumType.STRING)
    TypeChambre typeC;







    @OneToMany(fetch = FetchType.EAGER)
    List<Reservation> reservations;
    @ManyToOne
    @JoinColumn(name = "bloc_id_bloc") // Ensure this matches your DB column name
    @JsonIgnoreProperties(value = {"chambres"}, allowSetters = true)
    private Bloc bloc;



}

