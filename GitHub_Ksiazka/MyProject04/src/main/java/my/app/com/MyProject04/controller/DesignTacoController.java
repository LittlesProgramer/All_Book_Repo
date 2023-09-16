package my.app.com.MyProject04.controller;

import lombok.extern.slf4j.Slf4j;
import my.app.com.MyProject04.data.IngredientRepository;
import my.app.com.MyProject04.data.TacoRepository;
import my.app.com.MyProject04.domain.Ingredient;
import my.app.com.MyProject04.domain.Order;
import my.app.com.MyProject04.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(path = "/design")
public class DesignTacoController {

    private IngredientRepository ingredientRepo;
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    @ModelAttribute(name = "design")
    public Taco taco(){
        return new Taco();
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientRepo.findAll().forEach((i)->{
            ingredientList.add(i);
        });
        Ingredient.Type types[] = Ingredient.Type.values();

        for(int t = 0 ; t < types.length ; t++){
            model.addAttribute(types[t].name().toLowerCase(),filterByType(types[t],ingredientList));
        }

        return "design";
    }

    @PostMapping
    public String redirectToHome(Taco taco,Order order){
        Taco savedTaco = tacoRepo.save(taco);
        order.addDesignTacoToOrder(savedTaco);

        log.info("taco = "+taco+" order = "+order);

        return "redirect:/";
    }

    private List<Ingredient> filterByType(Ingredient.Type type, List<Ingredient> ingredientList) {
        return ingredientList.stream().filter((t)->{
            return t.getType().name().equals(type.name());
        }).collect(Collectors.toList());
    }
}
