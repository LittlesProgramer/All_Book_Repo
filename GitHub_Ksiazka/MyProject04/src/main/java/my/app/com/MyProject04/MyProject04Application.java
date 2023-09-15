package my.app.com.MyProject04;

import my.app.com.MyProject04.data.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static my.app.com.MyProject04.domain.Ingredient.Type.*;
import my.app.com.MyProject04.domain.Ingredient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyProject04Application{

	public static void main(String[] args) {
		SpringApplication.run(MyProject04Application.class, args);
	}

	@Bean
	public CommandLineRunner prepareDataForController(IngredientRepository repo){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				repo.save(new Ingredient("FLTO", "Flour Tortilla", WRAP));
				repo.save(new Ingredient("COTO", "Corn Tortilla", WRAP));
				repo.save(new Ingredient("GRBF", "Ground Beef", PROTEIN));
				repo.save(new Ingredient("CARN", "Carnitas", PROTEIN));
				repo.save(new Ingredient("TMTO", "Diced Tomatoes", VEGGIES));
				repo.save(new Ingredient("LETC", "Lettuce", VEGGIES));
				repo.save(new Ingredient("CHED", "Cheddar", CHEESE));
				repo.save(new Ingredient("JACK", "Monterrey Jack", CHEESE));
				repo.save(new Ingredient("SLSA", "Salsa", SAUCE));
				repo.save(new Ingredient("SRCR", "Sour Cream", SAUCE));
			}
		};
	}
}