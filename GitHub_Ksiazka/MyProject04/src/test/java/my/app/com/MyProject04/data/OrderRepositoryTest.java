package my.app.com.MyProject04.data;

import my.app.com.MyProject04.domain.Ingredient;
import my.app.com.MyProject04.domain.Order;
import my.app.com.MyProject04.domain.Taco;
import org.assertj.core.matcher.AssertionMatcher;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepo;

    @Test
    public void testOrderRepositoryDataObject(){
        Order order = new Order();
        order.setDeliveryName("Pasta concita");
        order.setDeliveryStreet("Pazia");
        order.setDeliveryCity("Lodz");
        order.setDeliveryState("pl");
        order.setDeliveryZip("125");
        order.setCcNumber("125");
        order.setCcExpiration("12/12");
        order.setCcCVV("362");
        Taco taco1 = new Taco();
        taco1.setName("Taco name 1");
        taco1.setIngredients(Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CHED", "Shredded Cheddar", Ingredient.Type.CHEESE)
        ));
        order.addDesignTacoToOrder(taco1);

        Taco taco2 = new Taco();
        taco2.setName("Taco name 2");
        taco2.addIngredient(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        taco2.addIngredient(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        taco2.addIngredient(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        order.addDesignTacoToOrder(taco2);

        IsNull<Long> isNull = new org.hamcrest.core.IsNull();
        AssertionMatcher<Long> assertionMatcher = new AssertionMatcher<Long>() {
            @Override
            public void assertion(Long aLong) throws AssertionError {
                isNull.matches(aLong);
            }
        };
        Order orderSave = orderRepo.save(order);
        Assert.assertTrue(orderSave.getId() != null);
        Assert.assertThat(orderSave.getId(),org.hamcrest.Matchers.notNullValue());

        Order orderFind = orderRepo.findById(orderSave.getId()).get();
        Assert.assertEquals(orderFind.getDeliveryName(),"Pasta concita");
        Assert.assertEquals(orderFind.getDeliveryStreet(),"Pazia");
        Assert.assertEquals(orderFind.getDeliveryCity(),"Lodz");
        Assert.assertEquals(orderFind.getDeliveryState(),"pl");
        Assert.assertEquals(orderFind.getDeliveryZip(),"125");
        Assert.assertEquals(orderFind.getCcNumber(),"125");
        Assert.assertEquals(orderFind.getCcExpiration(),"12/12");
        Assert.assertEquals(orderFind.getCcCVV(),"362");
        Assert.assertEquals(orderFind.getPlaceAt().getTime(),orderSave.getPlaceAt().getTime());

        List<Taco> tacoList = orderFind.getTacos();
        Assert.assertEquals(tacoList.size(),2);
        Taco[] tab1 = Arrays.asList(taco1,taco2).toArray(new Taco[tacoList.size()]);
        Taco[] tab2 = orderSave.getTacos().toArray(new Taco[tacoList.size()]);

        Assert.assertEquals(tab1.length,2);
        Assert.assertEquals(tab2.length,2);
        Assert.assertArrayEquals(tab1,tab2);
    }

}