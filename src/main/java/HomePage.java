import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private By searchBox = By.id("searchval");
    private By searchBtn = By.xpath("//button[@value='Search']");
    private By noSearchResults = By.xpath("//h2[@text()[contains(.,'find any matches')]]");
    private By resultsTable = By.id("product_listing");
    private By itemDesc = By.xpath ("//span[@data-testid='itemDescription']");
    private By pagination = By.id("paging");
    private By addItemCart = By.xpath ("//input[@data-testid='itemAddCart']");
    private By emptyCartBtn = By.xpath("//button[contains(text(),'Empty Cart')]");
    private By modalEmptyCartBtn = By.xpath("//footer/button[contains(text(),'Empty')]");
    private By modalAddedCartBtn = By.xpath(".//a[contains(text(),'View Cart')]");
    private By checkEmptyCartTxt = By.xpath(".//p[contains(text(),'Your cart is empty.')]");

    private WebElement lastElementFound;
    private String lastItemDesc;
    private String emptyCartDesc = "Your cart is empty.";

    // 2. Search for stainless work table
    public boolean search(String srchText, String validText) {
        setText(searchBox, srchText);
        click(searchBtn);

        if (isElementPresent(noSearchResults)) {
            goBack();
            return false;
        }

        // now check every element in the product_listing table and verify the word "Table"
        if (!checkTable(validText))
            return false;

        return true;
    }

    public boolean checkTable(String checkedText) {
        
        int currentPage = 1;

        checkEachTable(checkedText);
        
        // 3. Check the search result ensuring every product has the word Table in its title.
        while (!isLastPage(currentPage, checkedText)) {
            currentPage++;
            checkEachTable(checkedText);
        }

        return true;
    }

    boolean checkEachTable(String checkedText){
       //get the results table
       WebElement table =  driver.findElement(resultsTable);
       int itemCount = 1;
       //loop through results table
       List <WebElement> items = table.findElements(itemDesc); 
       for (WebElement e : items) {
           System.out.print(itemCount++ + ": ");
           System.out.println(e.getText());
           lastItemDesc = e.getText();
           if (!e.getText().contains(checkedText) ) {
               return false;
           }
       }
        return true;
    }

    public boolean isLastPage (int currentPage, String checkedText) {
        
        //get the pagination element
        WebElement pageControl =  driver.findElement(pagination);

        //loop through results table
        List <WebElement> items = pageControl.findElements(By.xpath ("//nav/ul/li")); 
            for (WebElement e : items) {
            System.out.println(e.getText());

            if (e.getText().contains(Integer.toString(currentPage+1)) ) {
                
                e.click();
                waitFor2Secs();
                return false;
            }
        }
        return true;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // 4. Add the last of found items to Cart.
    public boolean addCart(){
        //get the results table
        WebElement table =  driver.findElement(resultsTable);
 
        //loop through results table
        List <WebElement> items = table.findElements(addItemCart); 
        for (WebElement e : items) {
            System.out.println(e.getText());
            lastElementFound = e;
        }
        lastElementFound.click();
        //waitFor2Secs();

        //close popup if present
        WebElement modalViewCart = driver.findElement(modalAddedCartBtn);
        if (modalViewCart.isDisplayed())
            modalViewCart.click();
        waitFor2Secs();
        waitFor2Secs();

        return true;
     }
     
    // 4. Check Cart after adding to cart.
    public boolean checkCart() {
        
        waitFor2Secs();
        if (driver.getPageSource().contains(lastItemDesc.substring(0, 100))) {
            System.out.println("Successfully checked in cart.");
            return true;
        }
        else 
            return false;

    }

    // 5. Empty Cart.
    public boolean emptyCart() {
       waitFor2Secs();
       waitForElementText(emptyCartBtn, "Empty Cart");
       //click the empty cart
       WebElement cart =  driver.findElement(emptyCartBtn);
       cart.click();
       waitFor2Secs();
       
       //close modal
       WebElement modalEmptyCart = driver.findElement(modalEmptyCartBtn);
       modalEmptyCart.click();
       waitFor2Secs();

       WebElement emptyCart = driver.findElement(checkEmptyCartTxt);
       if (emptyCart.getText().contains(emptyCartDesc)) {
           System.out.println("Successfully emptied cart");
           return true;
       }
       else 
           return false;
    }

    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

}
