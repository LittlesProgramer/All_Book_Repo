package my.app.com.MyProject04.domain;

import lombok.Data;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.validation.Validator;
import javax.validation.constraints.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "name cannot be blank")
    @Size(min = 5,message = "Name must have at least 5 characters")
    private String name;

    private Date createAt;

    @NotNull(message = "Taco must have at least one ingredient")
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients = new ArrayList<>();

    @PrePersist
    private void createAt(){
        this.createAt = new Date();
    }

    public void addIngredient(Ingredient ingredient){ this.ingredients.add(ingredient); }
}

