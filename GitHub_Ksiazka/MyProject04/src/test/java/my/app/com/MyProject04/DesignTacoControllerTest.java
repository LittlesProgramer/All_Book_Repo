package my.app.com.MyProject04;

import my.app.com.MyProject04.controller.DesignTacoController;
import my.app.com.MyProject04.data.IngredientRepository;
import my.app.com.MyProject04.data.TacoRepository;
import my.app.com.MyProject04.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import my.app.com.MyProject04.domain.Ingredient.Type;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {DesignTacoController.class})
public class DesignTacoControllerTest {

    private List<Ingredient> ingredientList;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepo;

    @MockBean
    private TacoRepository tacoRepo;

    @Before
    public void prepareData(){
        ingredientList = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        when(ingredientRepo.findAll()).thenReturn(ingredientList);
        when(ingredientRepo.findById("XXXX")).thenReturn(Optional.of(new Ingredient("Test_XXX","name_of_XXX", Type.SAUCE)));
        when(ingredientRepo.findById("GRBF")).thenReturn(Optional.of(new Ingredient("Test_GRBF","name_of_FLTO_XXX", Type.SAUCE)));
        when(ingredientRepo.findById("CHED")).thenReturn(Optional.of(new Ingredient("Test_CHED","name_of_CHED_XXX", Type.SAUCE)));
    }

    @Test
    public void showDesignFormTest() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(view().name("design"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("wrap",ingredientList.subList(0,2)))
                .andExpect(model().attribute("protein",ingredientList.subList(2,4)))
                .andExpect(model().attribute("veggies",ingredientList.subList(4,6)))
                .andExpect(model().attribute("cheese",ingredientList.subList(6,8)))
                .andExpect(model().attribute("sauce",ingredientList.subList(8,10)));
    }

    @Test
    public void redirectToHomeTest() throws Exception {
        mockMvc.perform(post("/design").content("name=Magda+Piotrek&ingredients=XXXX,GRBF,CHED").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/current"));
    }
}