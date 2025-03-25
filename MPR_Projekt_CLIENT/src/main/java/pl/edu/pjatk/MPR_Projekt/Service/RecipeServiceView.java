package pl.edu.pjatk.MPR_Projekt.Service;

import org.openqa.selenium.NotFoundException;
import org.springframework.ui.Model;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.MPR_Projekt.Model.AnalyzedInstruction;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;

import pl.edu.pjatk.MPR_Projekt.Model.Step;
import pl.edu.pjatk.MPR_Projekt.Model.Unit;
import pl.edu.pjatk.MPR_Projekt.exception.RecipeAlreadyExistException;
import pl.edu.pjatk.MPR_Projekt.exception.RecipeNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class RecipeServiceView {

    private final RestClient restClient;


    public RecipeServiceView() {
        this.restClient = RestClient.create("http://localhost:8081/");
    }


    public List<Recipe> getApiRecipeByIngredients(List<String> ingredients) {
        try {
            List<Recipe> recipeList = restClient.get()
                    .uri("api/recipe/findByIngredients/" + ingredients)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return recipeList;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public List<Recipe> getApiRecipeBySearch(String query) {
        try {
            List<Recipe> recipeList = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/recipe/search")
                            .queryParam("query", query)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return recipeList;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public List<Recipe> getApiRecipeList() {
        try {
            List<Recipe> recipeList = restClient.get()
                    .uri("api/recipe/recipeList")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return recipeList;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }

    public List<Recipe> getRandomApiRecipe() {
        try {
            List<Recipe> recipeList = restClient.get()
                    .uri("api/recipe/random")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return recipeList;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public Recipe getApiAnalyzedInstruction(Long id) {
        try {

            List<AnalyzedInstruction> AnalyzedInstruction = restClient.get()
                    .uri("api/recipe/analyzedInstruction/" + id)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<AnalyzedInstruction>>() {
                    });

            if (AnalyzedInstruction == null || AnalyzedInstruction.isEmpty()) {

                throw new RecipeNotFoundException();
            }

            Recipe recipe = new Recipe();
            recipe.setAnalyzedInstructions(AnalyzedInstruction);

            return recipe;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public Recipe getApiRecipeInformationById(Long id) {
        try {
            Recipe recipe = restClient.get()
                    .uri("api/recipe/information/" + id)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Recipe>() {
                    });
            return recipe;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }

    public List<Unit> getMyAllUnits() {
        try {
            List<Unit> unitList = restClient.get()
                    .uri("unit/all")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return unitList;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<Recipe> getMyRecipeList() {
        try {
            List<Recipe> recipeList = restClient.get()
                    .uri("recipe/my")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return recipeList;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public void createRecipe(Recipe recipe, Model model) {

        if (recipe.getTitle() == null || recipe.getTitle().isEmpty() ||
                recipe.getImage() == null || recipe.getImage().isEmpty()) {
            model.addAttribute("error", "Nazwa lub zdjęcie nie mogą być puste");
            return;
        }
        try {
            restClient.post()
                    .uri("/recipe/add")
                    .contentType(APPLICATION_JSON)
                    .body(recipe)
                    .retrieve()
                    .toBodilessEntity();

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Nie udało się dodać przepisu. Spróbuj ponownie.");
        } catch (Exception e) {
            throw new RecipeAlreadyExistException();
        }
    }


    public List<Recipe> getRecipeByTitle(String name) {
        try {
            List<Recipe> recipeList = restClient.get()
                    .uri("recipe/name/" + name)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Recipe>>() {
                    });
            if (recipeList == null || recipeList.isEmpty()) {
                throw new RecipeNotFoundException();
            }
            return recipeList;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public void deleteRecipeById(Long id) {
        try {
            restClient.delete()
                    .uri("/recipe/id/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public Recipe getRecipeById(Long id) {
        try {
            return restClient.get()
                    .uri("recipe/{id}", id)
                    .retrieve()
                    .body(Recipe.class);
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }


    public void updateRecipe(Recipe updatedRecipe, int id) {
        try {
            restClient.put()
                    .uri("/recipe/update/{id}", id)
                    .body(updatedRecipe)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
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

