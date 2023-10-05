package my.app.com.MyProject04.data;

import my.app.com.MyProject04.domain.Ingredient;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepo;

    @Test
    public void shouldReturnValueIfExists(){
        Optional<Ingredient> optional = ingredientRepo.findById("FLTO");
        Assert.assertEquals(optional.isPresent(),ingredientRepo.existsById("FLTO"));
        Assert.assertEquals(optional.get(),new Ingredient("FLTO","Flour Tortilla", Ingredient.Type.WRAP));
    }

    @Test
    public void shouldReturnNullIfEmpty(){
        Optional<Ingredient> optional = ingredientRepo.findById("ZZZZ");
        Assert.assertTrue(optional.isEmpty());
    }

}