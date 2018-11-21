import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class TestToDoWebApp {

    private ToDoWebApp app;
    private WebDriver driver;
    Random rand = new Random();
    private String randomToDo = Integer.toString(100000000 + rand.nextInt(900000000));

	@BeforeTest
	public void setup(){
        if(OS.isFamilyWindows()){
            System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs.exe");
//            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        }else if(OS.isFamilyUnix()){
            System.setProperty("phantomjs.binary.path", "src/test/resources/phantomjs");
        }
        if(System.getProperty("phantomjs.binary.path") != null)
            driver = new PhantomJSDriver();
        else if(System.getProperty("webdriver.chrome.driver") != null){
            driver = new ChromeDriver();
        }
        else
            throw new RuntimeException("Unknown web driver specified.");

        app = new ToDoWebApp(driver);
		app.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		app.driver.manage().deleteAllCookies();
		app.driver.get("http://todomvc.com/examples/react/#/");
	}


	@Test(priority=0)
	public void test_mark_all_to_do_as_completed(){
        app.homePage().addToDo(randomToDo + "0");
        Assert.assertTrue(app.homePage().getToDoCount() == 1);
        app.homePage().addToDo(randomToDo + "1");
        Assert.assertTrue(app.homePage().getToDoCount() == 2);
        app.homePage().toggleEach();
        Assert.assertTrue(app.homePage().getToDoCount() == 0);
        app.homePage().clearCompleted();
    }

    @Test(priority=0)
    public void test_toggle_all(){
        app.homePage().addToDo(randomToDo + "0");
        Assert.assertTrue(app.homePage().getToDoCount() == 3);
        app.homePage().addToDo(randomToDo + "1");
        app.homePage().toggleEach();
        Assert.assertTrue(app.homePage().getToDoCount() == 0);
        app.homePage().toggleAll();
        Assert.assertTrue(app.homePage().getToDoCount() == 4);
    }

    @Test(priority=0)
    public void test_mark_some_to_do_as_completed(){
        app.homePage().addToDo(randomToDo + "0");
        app.homePage().addToDo(randomToDo + "1");
        app.homePage().addToDo(randomToDo + "2");
        Assert.assertTrue(app.homePage().getToDoCount() == 3);
        app.homePage().toggleToDo(randomToDo + "1");
        Assert.assertTrue(app.homePage().getToDoCount() == 2);
        app.homePage().clearCompleted();
        Assert.assertTrue(app.homePage().getToDoCount() == 2);
	}


	@AfterTest
	public void tearDown(){
        app.driver.manage().deleteAllCookies();
        app.driver.quit();
	}

}
