package pl.edu.pjatk.MPR_Projekt.exception;

public class RecipeAlreadyExistException extends RuntimeException {

    public RecipeAlreadyExistException() {
        super("Recipe already exist");
    }
}
