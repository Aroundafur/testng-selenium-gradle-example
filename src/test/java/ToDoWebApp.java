import PageFactory.BasePage;
import org.openqa.selenium.WebDriver;

public class ToDoWebApp {

    public WebDriver driver;

    public ToDoWebApp(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage homePage() { return new BasePage(driver); }

}