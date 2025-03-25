package pl.edu.pjatk.MPR_Projekt.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;
import pl.edu.pjatk.MPR_Projekt.Repository.RecipeRepository;
import pl.edu.pjatk.MPR_Projekt.exception.RecipeAlreadyExistException;
import pl.edu.pjatk.MPR_Projekt.exception.RecipeNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final StringUtilsService stringUtilsService;


    @Autowired
    public RecipeService(RecipeRepository recipeRepository, StringUtilsService stringUtilsService) {
        this.recipeRepository = recipeRepository;
        this.stringUtilsService = stringUtilsService;
    }


    public List<Recipe> getMyRecipeList() {
        List<Recipe> recipeList = (List<Recipe>) this.recipeRepository.findAll();
        if (recipeList.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        return recipeList;
    }


    public List<Recipe> getRecipeByTitle(String title) {
        List<Recipe> recipe = this.recipeRepository.findByTitle(title);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        return recipeRepository.findByTitle(title);
    }



    public void createRecipe(Recipe recipe) {
       recipe.setTitle(stringUtilsService.bigFirstLetter(recipe.getTitle()));
        List<Recipe> recipes = this.recipeRepository.findByTitle(recipe.getTitle());
        if (!recipes.isEmpty()) {
            throw new RecipeAlreadyExistException();
        }
        recipeRepository.save(recipe);
    }

    public void deleteRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        recipeRepository.deleteById(id);
    }



    public void deleteRecipeByTitle(String title) {
        List<Recipe> recipes = recipeRepository.findByTitle(title);
        if (recipes.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        for (Recipe recipe : recipes) {
            recipeRepository.delete(recipe);
        }
    }


    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipe = this.recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        return recipe.get();
    }




    public void updateRecipe(Long id, Recipe updatedRecipe) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if (existingRecipe.isPresent()) {
            Recipe recipe = existingRecipe.get();
            recipe.setTitle(updatedRecipe.getTitle());
            recipe.setDescription(updatedRecipe.getDescription());
            recipeRepository.save(recipe);
        } else {
            throw new RecipeNotFoundException();
        }
    }



    public ByteArrayResource generatePdf(Recipe recipe) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.setLeading(14.5f);

        contentStream.beginText();

        contentStream.newLineAtOffset(50, 750);

        contentStream.showText("Name: " + recipe.getTitle());
        contentStream.newLine();

        contentStream.showText("Color: " + recipe.getDescription());
        contentStream.newLine();

        contentStream.endText();
        contentStream.close();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }


}

