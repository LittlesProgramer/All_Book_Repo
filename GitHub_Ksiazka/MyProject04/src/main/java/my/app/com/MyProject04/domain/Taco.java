package my.app.com.MyProject04.domain;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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

    @Size(min = 1,message = "Taco must have at least one ingredient")
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    @PrePersist
    private void createAt(){
        this.createAt = new Date();
    }
}

