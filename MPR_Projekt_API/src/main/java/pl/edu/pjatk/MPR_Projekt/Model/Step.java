package pl.edu.pjatk.MPR_Projekt.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    private String step;

    private String title;

    @ManyToOne
    @JoinColumn(name = "analyzed_instruction_id", nullable = false)
    @JsonBackReference
    private AnalyzedInstruction analyzedInstruction;


//    @ManyToOne
//    @JoinColumn(name = "recipe_id", nullable = false)
//    @JsonBackReference
//    private Recipe recipe;

}
