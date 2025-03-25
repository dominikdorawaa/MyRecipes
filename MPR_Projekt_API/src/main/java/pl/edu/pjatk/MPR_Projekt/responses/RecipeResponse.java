package pl.edu.pjatk.MPR_Projekt.responses;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeResponse {
    @JsonProperty("recipes")
    @JsonAlias("results")
    private List<Recipe> recipes;



    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
