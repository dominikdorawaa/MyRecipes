package pl.edu.pjatk.MPR_Projekt.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nutrient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nutrient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double amount;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "nutrition_id")
    @JsonBackReference
    private Nutrition nutrition;

}
