package my.app.com.MyProject04.data;

import my.app.com.MyProject04.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,String> {
}
