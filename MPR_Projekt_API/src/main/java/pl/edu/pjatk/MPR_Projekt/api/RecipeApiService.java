package pl.edu.pjatk.MPR_Projekt.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pjatk.MPR_Projekt.Model.AnalyzedInstruction;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;
import pl.edu.pjatk.MPR_Projekt.Model.Step;
import pl.edu.pjatk.MPR_Projekt.Repository.RecipeRepository;
import pl.edu.pjatk.MPR_Projekt.responses.RecipeResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class RecipeApiService {


    private final RestTemplate restTemplate;

    @Value("${spoonacular.api.key}")
    private String apiKey;


    private final String baseUrl = "https://api.spoonacular.com/recipes";


    @Autowired
    public RecipeApiService(RestTemplate restTemplate, @Value("${spoonacular.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }


    public List<AnalyzedInstruction> getAnalyzedInstructions(int id) {
        String url = String.format("%s/%d/analyzedInstructions?apiKey=%s", baseUrl, id, apiKey);
        AnalyzedInstruction[] recipeInstruction = restTemplate.getForObject(url, AnalyzedInstruction[].class);
        assert recipeInstruction != null;
        return Arrays.asList(recipeInstruction);
    }

    public List<Recipe> findRecipesByIngredients(List<String> ingredients, int number) {
        String ingredientsStr = String.join(",", ingredients);
        String url = String.format("%s/findByIngredients?ingredients=%s&number=%d&apiKey=%s", baseUrl, ingredientsStr, number, apiKey);
        Recipe[] response = restTemplate.getForObject(url, Recipe[].class);
        return Arrays.asList(response);
    }

    public List<Recipe> getApiRecipeList(int number) {
        String url = String.format("%s/random?number=%d&apiKey=%s", baseUrl, number, apiKey);
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        return response != null ? response.getRecipes() : Collections.emptyList();
    }


    public Recipe getApiRecipeInformation(int id, String includeNutrition) {
        String url = String.format("%s/%d/information?includeNutrition=%s&apiKey=%s", baseUrl, id, includeNutrition, apiKey);
        Recipe recipe = restTemplate.getForObject(url, Recipe.class);
        return recipe;
    }



    public List<Recipe> getRecipesBySearch(String query, int number, Integer maxFat) {
        StringBuilder urlBuilder = new StringBuilder(
                String.format("%s/complexSearch?apiKey=%s&query=%s&number=%d", baseUrl, apiKey, query, number)
        );

        if (maxFat != null) {
            urlBuilder.append("&maxFat=").append(maxFat);
        }

        String url = urlBuilder.toString();
        RecipeResponse response = restTemplate.getForObject(url, RecipeResponse.class);
        return response != null ? response.getRecipes() : Collections.emptyList();
    }














//    public List<NutrientRecipeDTO> findByCalories(int calories, Integer carbs, int number) {
//        StringBuilder urlBuilder = new StringBuilder(String.format("%s/findByNutrients?apiKey=%s&maxCalories=%d&number=%d", baseUrl, apiKey, calories, number));
//
//        if (carbs != null) {
//            urlBuilder.append("&maxCarbs=").append(carbs);
//        }
//
//        String url = urlBuilder.toString();
//        NutrientRecipeDTO[] response = restTemplate.getForObject(url, NutrientRecipeDTO[].class);
//        return Arrays.asList(response);
//    }


}





