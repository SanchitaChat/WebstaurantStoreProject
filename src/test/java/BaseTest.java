
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest extends BasePage {

    protected HomePage homePage;

    @BeforeClass
    public void setup() {
        Assert.assertTrue(goToHomepage(), "An error occurred while navigating to the the homepage");
        homePage = new HomePage();
    }

    @AfterClass
    public void tearDown() {
        closeBrowser();
    }
}
