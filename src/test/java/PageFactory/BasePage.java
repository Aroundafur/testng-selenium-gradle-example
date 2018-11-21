package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;


public class BasePage {

    WebDriver driver;
    @FindBy(className="new-todo")
    WebElement toDoInput;

    @FindBy(xpath="//*[@class='todo-list']")
    WebElement toDoToList;

    @FindBy(xpath="//*[@class='clear-completed']")
    WebElement clearCompletedButton;

    @FindBy(xpath="//label[@for='toggle-all']")
    WebElement toggleAllButton;

    @FindBy(xpath="//*[@class='todo-count']//strong")
    WebElement toDoCount;

    @FindBy(xpath="//*[@class='selected']")
    WebElement showAllToDosButton;

    @FindBy(xpath="//*[@href='#/active']")
    WebElement showAllActiveToDosButton;

    @FindBy(xpath="//*[href='#/completed']")
    WebElement showAllCompletedToDosButton;




    WebDriverWait wait;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        //This initElements method will create  all WebElements
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);

    }

    public void addToDo(String s){
        toDoInput.click();
        toDoInput.sendKeys(s);
        toDoInput.sendKeys(Keys.ENTER);
    }

    public void toggleEach(){
        List<WebElement> todos = toDoToList.findElements(By.xpath("//*[@class='toggle']"));
        for (Iterator<WebElement> i = todos.iterator(); i.hasNext();) {
            WebElement item = i.next();
            item.click();
        }
    }

    public void toggleAll(){
        toggleAllButton.click();
    }

    public int getToDoCount(){
        return Integer.parseInt(toDoCount.getText());
    }
    public void toggleToDo(String text){
        List<WebElement> todos = toDoToList.findElements(By.xpath("//li"));
        for (Iterator<WebElement> i = todos.iterator(); i.hasNext();) {
            WebElement item = i.next();
            WebElement toggle = item.findElement(By.xpath("//*[@class='toggle']"));
            if(item.getText().equals(text)) {
                toggle.click();
            }
        }
    }

    public void clearCompleted(){
        clearCompletedButton.click();
    }
}
