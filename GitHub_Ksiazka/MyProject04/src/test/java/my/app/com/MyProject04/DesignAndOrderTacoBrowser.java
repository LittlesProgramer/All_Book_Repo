package my.app.com.MyProject04;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesignAndOrderTacoBrowser {

    @LocalServerPort
    private int port;

    private static HtmlUnitDriver browser;

    @BeforeClass
    public static void prepareBrowser(){
        browser = new HtmlUnitDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public static void quitBrowser(){
        browser.quit();
    }

    @Test
    public void testDesignATacoPage_HappyPath(){
        browser.get(getHomeUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic_Taco","FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        clickBuildAnotherTaco();
        buildAndSubmitATaco("Another Taco", "COTO", "CARN", "JACK", "LETC", "SRCR");
        fillInAndSubmitOrderForm();
        Assert.assertEquals(browser.getCurrentUrl(),getHomeUrl());
    }
    @Test
    public void testDesignATacoPage_EmptyOrderInfo(){
        browser.get(getHomeUrl());
        clickDesignATaco();
        assertDesignPageElements();
        buildAndSubmitATaco("Basic Taco", "FLTO", "GRBF", "CHED", "TMTO", "SLSA");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(getHomeUrl());
    }

    private void submitEmptyOrderForm() {
        Assert.assertEquals(browser.getCurrentUrl(),getOrderUrl());
        browser.findElement(By.id("deliveryName")).sendKeys("");
        browser.findElement(By.id("deliveryStreet")).sendKeys("");
        browser.findElement(By.id("deliveryCity")).sendKeys("");
        browser.findElement(By.id("deliveryState")).sendKeys("");
        browser.findElement(By.id("deliveryZip")).sendKeys("");
        browser.findElement(By.id("ccNumber")).sendKeys("");
        browser.findElement(By.id("ccExpiration")).sendKeys("");
        browser.findElement(By.id("ccCVV")).sendKeys("");

        browser.findElement(By.tagName("form")).submit();
        assertThat(browser.getCurrentUrl()).isEqualTo(getErrorOrder());

        List<String> listOfAllValidationErrors = getAllValidationErrorInString();
        listOfAllValidationErrors.stream().forEach((el)->{
            System.out.println("el = "+el);
        });
        assertThat(listOfAllValidationErrors.size()).isEqualTo(9);
        Assert.assertEquals(listOfAllValidationErrors.size(),9);

        assertThat(listOfAllValidationErrors).containsExactlyInAnyOrderElementsOf(listOfAllValidationErrors);
        assertThat(listOfAllValidationErrors).containsAll(listOfAllValidationErrors);
        assertThat(listOfAllValidationErrors).containsExactlyElementsOf(listOfAllValidationErrors);
        assertThat(listOfAllValidationErrors).containsAnyElementsOf(listOfAllValidationErrors);
    }

    private List<String> getAllValidationErrorInString() {
        List<WebElement> validationErrorList = browser.findElements(By.className("validationError"));
        List<String> allValidationStringErrors = validationErrorList.stream().map((webEl)->{
            return webEl.getText();
        }).collect(Collectors.toList());
        return allValidationStringErrors;
    }

    private void fillInAndSubmitOrderForm() {

        Assert.assertEquals(browser.getCurrentUrl(),getOrderUrl());
        assertThat(browser.getCurrentUrl()).isEqualTo(getOrderUrl());
        assertThat(browser.getCurrentUrl()).startsWith(getErrorOrder());

        fillField("input#deliveryName","nazwa");
        fillField("input#deliveryStreet","ulicaPostawna");
        fillField("input#deliveryCity","wawa");
        fillField("input#deliveryState","CO");
        fillField("input#deliveryZip","81019");
        fillField("input#ccNumber","4111111111111111");
        fillField("input#ccExpiration","12/12");
        fillField("input#ccCVV","123");

        browser.findElement(By.cssSelector("input[type='submit']")).submit();
    }

    private void fillField(String cssSel, String cssSelVal) {
        WebElement fillingElement = browser.findElement(By.cssSelector(cssSel));
        fillingElement.clear();
        fillingElement.sendKeys(cssSelVal);
    }

    private void clickBuildAnotherTaco() {
        Assert.assertEquals(browser.getCurrentUrl(),getOrderUrl());
        browser.findElement(By.cssSelector("a[href='/design']")).click();
        Assert.assertEquals(browser.getCurrentUrl(),getDesignUrl());
    }

    private void buildAndSubmitATaco(String tacoName, String... ingredientArgs) {
        assertDesignPageElements();

        for(String el: ingredientArgs){
            browser.findElement(By.cssSelector("input[value='"+el+"']")).click();
        }

        browser.findElement(By.cssSelector("input[type='text']")).sendKeys(tacoName);
        browser.findElement(By.tagName("button")).submit();
    }

    private void assertDesignPageElements() {
        Assert.assertEquals(browser.getCurrentUrl(),getDesignUrl());
        assertThat(browser.getCurrentUrl()).isEqualTo(getDesignUrl());

        List<WebElement> groups = browser.findElements(By.className("ingredient-group"));
        Assert.assertEquals(groups.size(),5);

        WebElement wraps = browser.findElement(By.cssSelector("div.ingredient-group#wraps"));
        List<WebElement> wrapDivs = wraps.findElements(By.tagName("div"));
        assertThat(wrapDivs.size()).isEqualTo(2);
        Assert.assertEquals(wrapDivs.size(),2);
        assertIngredient(wraps,0,"FLTO","Flour Tortilla");
        assertIngredient(wraps,1,"COTO","Corn Tortilla");

        WebElement proteins = browser.findElement(By.cssSelector("div.ingredient-group#proteins"));
        List<WebElement> proteinDivs = proteins.findElements(By.tagName("div"));
        assertThat(proteinDivs.size()).isEqualTo(2);
        Assert.assertEquals(proteinDivs.size(),2);
        assertIngredient(proteins,0,"GRBF","Ground Beef");
        assertIngredient(proteins,1,"CARN","Carnitas");

        WebElement veggies = browser.findElement(By.cssSelector("div.ingredient-group#veggies"));
        List<WebElement> veggiesDivs = veggies.findElements(By.tagName("div"));
        assertThat(veggiesDivs.size()).isEqualTo(2);
        Assert.assertEquals(veggiesDivs.size(),2);
        assertIngredient(veggies,0,"TMTO","Diced Tomatoes");
        assertIngredient(veggies,1,"LETC","Lettuce");

        WebElement cheeses = browser.findElement(By.cssSelector("div.ingredient-group#cheeses"));
        List<WebElement> cheesesDivs = cheeses.findElements(By.tagName("div"));
        assertThat(cheesesDivs.size()).isEqualTo(2);
        Assert.assertEquals(cheesesDivs.size(),2);
        assertIngredient(cheeses,0,"CHED","Cheddar");
        assertIngredient(cheeses,1,"JACK","Monterrey Jack");

        WebElement sauces = browser.findElement(By.cssSelector("div.ingredient-group#sauces"));
        List<WebElement> saucesDivs = sauces.findElements(By.tagName("div"));
        assertThat(saucesDivs.size()).isEqualTo(2);
        Assert.assertEquals(saucesDivs.size(),2);
        assertIngredient(sauces,0,"SLSA","Salsa");
        assertIngredient(sauces,1,"SRCR","Sour Cream");
    }

    private void assertIngredient(WebElement groupElement, int index, String ingredientId, String ingredientName) {
        List<WebElement> divGroupElements = groupElement.findElements(By.tagName("div"));
        Assert.assertEquals(divGroupElements.size(),2);
        assertThat(divGroupElements.size()).isEqualTo(2);

        WebElement element = divGroupElements.get(index);
        Assert.assertEquals(element.findElement(By.tagName("input")).getAttribute("value"),ingredientId);
        assertThat(element.findElement(By.tagName("input")).getAttribute("value")).isEqualTo(ingredientId);
        Assert.assertEquals(element.findElement(By.tagName("span")).getText(),ingredientName);
        assertThat(element.findElement(By.tagName("span")).getText()).isEqualTo(ingredientName);
    }

    private void clickDesignATaco() {
        Assert.assertEquals(browser.getCurrentUrl(),getHomeUrl());
        assertThat(browser.getCurrentUrl()).isEqualTo(getHomeUrl());
        browser.findElement(By.cssSelector("a[id='design']")).click();

    }

    //help url methods
    public String getHomeUrl(){ return "http://localhost:"+port+"/"; }
    public String getDesignUrl(){ return getHomeUrl()+"design"; }

    public String getOrderUrl(){ return getHomeUrl()+"orders/current"; }
    public String getErrorOrder(){ return getHomeUrl()+"orders"; }
}
