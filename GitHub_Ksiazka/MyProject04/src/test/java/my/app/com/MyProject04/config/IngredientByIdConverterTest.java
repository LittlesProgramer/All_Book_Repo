package my.app.com.MyProject04.config;

import my.app.com.MyProject04.data.IngredientRepository;
import my.app.com.MyProject04.domain.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class IngredientByIdConverterTest {

    private IngredientByIdConverter converter;

    @BeforeEach
    public void performBeforeEveryTest(){
        IngredientRepository ingredientRepo = mock(IngredientRepository.class);
        when(ingredientRepo.findById("AAAA")).thenReturn(Optional.of(new Ingredient("AAAA","My AAAA test", Ingredient.Type.PROTEIN)));
        when(ingredientRepo.findById("ZZZZ")).thenReturn(Optional.empty());

        this.converter = new IngredientByIdConverter(ingredientRepo);
    }

    @Test
    public void shouldReturnValueWhenPresent(){
        assertThat(converter.convert("AAAA")).isEqualTo(new Ingredient("AAAA","My AAAA test", Ingredient.Type.PROTEIN));
    }

    @Test
    public void shouldReturnNullWhenMissing(){
        assertThat(converter.convert("ZZZZ")).isNull();
    }

}