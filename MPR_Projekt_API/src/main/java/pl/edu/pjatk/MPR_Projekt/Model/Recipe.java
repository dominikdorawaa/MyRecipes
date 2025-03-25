package pl.edu.pjatk.MPR_Projekt.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String image;

    @Column(name = "ready_in_minutes")
    private Integer readyInMinutes;

    private String servings;

    private String summary;


    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Nutrition nutrition;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Ingredient> extendedIngredients;





    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AnalyzedInstruction> analyzedInstructions;


    //    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Step> steps;
}
