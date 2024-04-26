import org.testng.annotations.*;
import static org.testng.Assert.*;

public class HomepageTests extends BaseTest {

    @Test(description = "Verify page title")
    public void testPageTile() {
        String pageTitle = homePage.getPageTitle();
        assertEquals(pageTitle, "WebstaurantStore: Restaurant Supplies & Foodservice Equipment", "The page title did not match!");
    }

    @Test(description = "Searches for stainless work table")
    public void testSearch() {
        boolean searchSuccess;
        boolean cartSuccess;

        searchSuccess = homePage.search("stainless work table","Table");
        assertTrue(searchSuccess, "Found search result");
        
        cartSuccess = homePage.addCart();
        assertTrue(cartSuccess, "Add cart");
        
        cartSuccess = homePage.checkCart();
        //assertTrue(cartSuccess, "Check cart");
        
        cartSuccess = homePage.emptyCart();
        assertTrue(cartSuccess, "Empty cart!!");
    }

}
