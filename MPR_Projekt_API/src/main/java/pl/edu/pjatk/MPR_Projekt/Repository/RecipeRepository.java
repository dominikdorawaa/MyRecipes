package pl.edu.pjatk.MPR_Projekt.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.MPR_Projekt.Model.Recipe;

import java.util.List;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    public List<Recipe> findByTitle(String title);

}
