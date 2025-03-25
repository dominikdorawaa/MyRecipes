package pl.edu.pjatk.MPR_Projekt.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;
import pl.edu.pjatk.MPR_Projekt.Service.RecipeService;
import pl.edu.pjatk.MPR_Projekt.api.RecipeApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class MyRestController {
    private final RecipeService recipeService;



    @Autowired
    public MyRestController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }


    @GetMapping("recipe/my")
    public ResponseEntity<List<Recipe>> getMyRecipes() {
        return new ResponseEntity<>(this.recipeService.getMyRecipeList(), HttpStatus.OK);
    }

    @GetMapping("recipe/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id) {
        return new ResponseEntity<>(this.recipeService.getRecipeById(id), HttpStatus.OK);
    }

    @GetMapping("recipe/title/{title}")
    public ResponseEntity<List<Recipe>> getByTitle(@PathVariable String title) {
        return new ResponseEntity<>(this.recipeService.getRecipeByTitle(title), HttpStatus.OK);
    }


    @PostMapping("recipe/add")
    public ResponseEntity<Recipe> create(@RequestBody Recipe recipe) {
        this.recipeService.createRecipe(recipe);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @DeleteMapping("recipe/id/{id}")
    public ResponseEntity<Recipe> delete(@PathVariable Long id) {
        this.recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("recipe/title/{title}")
    public ResponseEntity<Recipe> delete(@PathVariable String title) {
        this.recipeService.deleteRecipeByTitle(title);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("recipe2/id/{id}")
    public ResponseEntity<ByteArrayResource> getPdf(@PathVariable Long id) throws IOException {
        Recipe recipe = recipeService.getRecipeById(id);


        ByteArrayResource pdfResource = recipeService.generatePdf(recipe);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recipe_" + id + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(pdfResource, headers, HttpStatus.OK);
    }


    @PutMapping("recipe/update/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) {
        this.recipeService.updateRecipe(id, recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
