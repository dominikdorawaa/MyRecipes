package pl.edu.pjatk.MPR_Projekt.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.edu.pjatk.MPR_Projekt.Model.AnalyzedInstruction;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;
import pl.edu.pjatk.MPR_Projekt.Model.Step;
import pl.edu.pjatk.MPR_Projekt.Model.Unit;
import pl.edu.pjatk.MPR_Projekt.Repository.UnitRepository;
import pl.edu.pjatk.MPR_Projekt.Service.RecipeServiceView;
import pl.edu.pjatk.MPR_Projekt.exception.PiesekNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MyViewController {

    private final RecipeServiceView recipeServiceView;



    public MyViewController(RecipeServiceView recipeServiceView) {
        this.recipeServiceView = recipeServiceView;

    }

    @GetMapping("findByIngredients/{ingredients}")
    public String findRecipesByIngredients(@PathVariable List<String> ingredients,Model model) {
        List<Recipe> recipes = recipeServiceView.getApiRecipeByIngredients(ingredients);
        model.addAttribute("recipes", recipes);
        model.addAttribute("ingredients",ingredients);
        return "viewAll";
    }

    @GetMapping("/view/search")
    public String displayApiRecipesBySearch(
            @RequestParam(name = "query", required = false) String query, Model model) {
        List<Recipe> recipes = recipeServiceView.getApiRecipeBySearch(query);
        model.addAttribute("recipes", recipes);
        model.addAttribute("query", query);
        return "viewAll";
    }


    @GetMapping("/view/analyzedInstruction/{id}")
    public String displayApiRecipeInstruction(@PathVariable Long id, Model model) {
        Recipe recipe = recipeServiceView.getApiAnalyzedInstruction(id);
        model.addAttribute("recipe", recipe);
        return "recipeAPIDetail";
    }

@GetMapping("/view/recipeInfo/{id}")
public String displayApiRecipeTitleAndImage(@PathVariable Long id, Model model) {
        Recipe recipe = recipeServiceView.getApiRecipeInformationById(id);
    model.addAttribute("recipe", recipe);
    return "recipeAPIDetail";
}


    @GetMapping("/view/recipe/{id}")
    public String displayFullRecipe(@PathVariable Long id, Model model) {
        Recipe recipeInfo = recipeServiceView.getApiRecipeInformationById(id);
        Recipe analyzedInstruction = recipeServiceView.getApiAnalyzedInstruction(id);

        if (recipeInfo != null && analyzedInstruction != null) {
            recipeInfo.setAnalyzedInstructions(analyzedInstruction.getAnalyzedInstructions());
        }

        model.addAttribute("recipe", recipeInfo);
        return "recipeAPIDetail";
    }


    @GetMapping("/view/all")
    public String displayAllRecipes(Model model) {
        try {
            List<Recipe> apiRecipes = this.recipeServiceView.getApiRecipeList();
//            List<Recipe> localRecipes = this.recipeServiceView.getMyRecipeList();
            List<Recipe> allRecipes = new ArrayList<>();
            allRecipes.addAll(apiRecipes);
//            allRecipes.addAll(localRecipes);

            if (allRecipes.isEmpty()) {
                model.addAttribute("error", "Brak przepisów z api do wyświetlenia.");
            } else {
                model.addAttribute("recipes", allRecipes);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Wystąpił błąd podczas ładowania przepisów.");
        }
        return "viewAll";
    }

    @GetMapping("/view/my")
    public String displayLocalRecipes(Model model) {
        try {

            List<Recipe> localRecipes = this.recipeServiceView.getMyRecipeList();

            if (localRecipes.isEmpty()) {
                model.addAttribute("error", "Brak lokalnych przepisów do wyświetlenia.");
            } else {
                model.addAttribute("recipes", localRecipes);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Wystąpił błąd podczas ładowania lokalnych przepisów.");
        }
        return "myRecipes";
    }


    @GetMapping("/view/random")
    public String displayRandomRecipe(Model model) {
        model.addAttribute("recipes", this.recipeServiceView.getRandomApiRecipe());
        return "randomRecipe";
    }




    @GetMapping("/admin")
    public String displayAdminPanel(Model model) {
        model.addAttribute("recipes", recipeServiceView.getMyRecipeList());
        return "adminPanel";
    }




    @GetMapping("/addForm")
    public String displayAddForm(Model model) {
        List<Unit> units = recipeServiceView.getMyAllUnits();
        model.addAttribute("units", units);

        model.addAttribute("recipe", new Recipe());
        return "addForm";
    }

    @PostMapping("/addForm")
    public String submitForm(@ModelAttribute("recipe") Recipe recipe
                            , Model model) {


        this.recipeServiceView.createRecipe(recipe,model);
        if (model.containsAttribute("error")) {
            return "addForm";
        }
        return "redirect:/view/all";
    }


    @GetMapping("/view/title/{title}")
    public String displayByTitle(@PathVariable String title, Model model) {
        try {
            List<Recipe> recipeList = this.recipeServiceView.getRecipeByTitle(title);
            if (recipeList == null || recipeList.isEmpty()) {
                model.addAttribute("error", "Brak przepisow do wyświetlenia o nazwie: " + title);
            } else {
                model.addAttribute("recipe", recipeList);
            }
        } catch (PiesekNotFoundException e) {
        }
        return "viewAll";
    }

    @GetMapping("/view/{id}")
    public String displayById(@PathVariable Long id, Model model) {
        try {
            Recipe recipe = this.recipeServiceView.getRecipeById(id);
            if (recipe == null) {
                model.addAttribute("error", "Brak przepisów do wyświetlenia o id: " + id);
            } else {
                model.addAttribute("recipe", recipe);
            }
        } catch (PiesekNotFoundException e) {
        }
        return "recipeDetail";
    }


    @GetMapping("/editForm/{id}")
    public String displayEditForm(@PathVariable Long id, Model model) {
        Recipe recipe = this.recipeServiceView.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "editForm";
    }


    @PostMapping("/editForm/{id}")
    public String confirmEdit(@PathVariable int id, @ModelAttribute Recipe recipe) {
        this.recipeServiceView.updateRecipe(recipe,id);
        return "redirect:/view/all";
    }


    @GetMapping("/deleteForm/{id}")
    public String displayDeleteForm(@PathVariable Long id, Model model) {
        Recipe recipe = (Recipe) this.recipeServiceView.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "deleteForm";
    }

    @PostMapping("/deleteForm/{id}")
    public String confirmDelete(@PathVariable Long id) {
        this.recipeServiceView.deleteRecipeById(id);
        return "redirect:/admin";
    }


}
