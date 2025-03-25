package pl.edu.pjatk.MPR_Projekt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_Projekt.Model.AnalyzedInstruction;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;
import pl.edu.pjatk.MPR_Projekt.Model.Step;

import java.util.List;


@RestController
@RequestMapping("api/recipe")
public class RecipeApiController {

    private final RecipeApiService recipeApiService;

    @Autowired
    public RecipeApiController(RecipeApiService recipeApiService) {
        this.recipeApiService = recipeApiService;
    }


    @GetMapping("analyzedInstruction/{id}")
    public ResponseEntity <List<AnalyzedInstruction>> getApiAnalyzedInstruction(@PathVariable int id) {

        List<AnalyzedInstruction> analyzedInstruction = recipeApiService.getAnalyzedInstructions(id);
        if (analyzedInstruction.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(analyzedInstruction);
    }


    @GetMapping("findByIngredients/{ingredients}")
    public ResponseEntity<List<Recipe>> findRecipesByIngredients(@PathVariable List<String> ingredients,
                                                                 @RequestParam(defaultValue = "10") int number) {
        List<Recipe> recipes = recipeApiService.findRecipesByIngredients(ingredients, number);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("recipeList")
    public ResponseEntity<List<Recipe>> recipeApiList(@RequestParam(defaultValue = "10") Integer number) {
        List<Recipe> recipes = recipeApiService.getApiRecipeList(number);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("random")
    public ResponseEntity<List<Recipe>> getRandomRecipeApi(@RequestParam(defaultValue = "1") Integer number) {
        List<Recipe> recipes = recipeApiService.getApiRecipeList(number);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }


    @GetMapping("information/{id}")
    public ResponseEntity<Recipe> getApiRecipeInformation(@PathVariable int id, @RequestParam(defaultValue = "true") String includeNutrition) {
        Recipe recipe = recipeApiService.getApiRecipeInformation(id, includeNutrition);
        if (recipe == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipe);
    }



    @GetMapping("search")
    public ResponseEntity<List<Recipe>> recipeBySearch(@RequestParam(name = "query", required = false) String query,@RequestParam(defaultValue = "20") int number,@RequestParam(required = false) Integer maxFat){
        List<Recipe> recipes = recipeApiService.getRecipesBySearch(query, number, maxFat);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

//    @GetMapping("/search/{query}")
//    public ResponseEntity<List<SummaryRecipeDTO>> searchRecipes(@PathVariable String query,
//                                                                @RequestParam(defaultValue = "10") int number,
//                                                                @RequestParam(required = false) Integer maxFat) {
//        List<SummaryRecipeDTO> recipes = recipeApiService.searchRecipes(query, number, maxFat);
//        return ResponseEntity.ok(recipes);
//    }


//
//    @GetMapping("/random")
//    public ResponseEntity<List<Recipe>> getRandomRecipes(@RequestParam(defaultValue = "1") int number,
//                                                         @RequestParam(required = false) String includeTags,
//                                                         @RequestParam(required = false) String excludeTags) {
//        List<Recipe> recipes = recipeApiService.getRandomRecipes(number, includeTags, excludeTags);
//        return ResponseEntity.ok(recipes);
//    }
//
//
//
//
//
//    @GetMapping("/findByCalories/{calories}")
//    public ResponseEntity<List<NutrientRecipeDTO>> findRecipesByCalories(@PathVariable int calories,
//                                                                         @RequestParam(required = false) Integer carbs,
//                                                                         @RequestParam(defaultValue = "5") int number) {
//        List<NutrientRecipeDTO> recipes = recipeApiService.findByCalories(calories, carbs, number);
//        return ResponseEntity.ok(recipes);
//    }
//
//
//



//    @GetMapping("/search/{query}")
//    public String searchRecipes(@PathVariable String query,
//                                @RequestParam(defaultValue = "1") int number,
//                                @RequestParam(required = false) Integer maxFat,
//                                Model model) {
//        List<Recipe> recipes = recipeService.searchRecipes(query, number, maxFat);
//        model.addAttribute("recipes", recipes);  // Dodajemy listę przepisów do modelu
//        model.addAttribute("query", query);      // Dodajemy wyszukiwaną frazę do modelu
//        return "searchResults";  // Widok HTML
//    }







}













