package pl.wwsi.przepis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wwsi.przepis.model.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
