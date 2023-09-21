package my.app.com.MyProject04;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyProject04Browser_HomeControllerTest {

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
    public void homeControllerBrowserTest(){
        browser.get(getHomeUrl());

        String title = browser.getTitle();
        Assert.assertEquals("Login Form",title);

        String h1Text = browser.findElement(By.tagName("h1")).getText();
        Assert.assertEquals("Welcome to...",h1Text);

        String srcPath = browser.findElement(By.tagName("img")).getAttribute("src");
        Assert.assertEquals(getHomeUrl()+"images/TacoCloud.png",srcPath);
    }

    //Help url methods :
    private String getHomeUrl(){return "http://localhost:"+port+"/"; }
}
