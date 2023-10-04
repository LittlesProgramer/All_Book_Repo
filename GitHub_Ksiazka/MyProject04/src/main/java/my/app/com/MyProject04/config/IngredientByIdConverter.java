package my.app.com.MyProject04.config;

import my.app.com.MyProject04.data.IngredientRepository;
import my.app.com.MyProject04.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientByIdConverter implements Converter<String,Ingredient> {

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> optional = ingredientRepo.findById(id);
        return optional.orElse(null);
    }
}
