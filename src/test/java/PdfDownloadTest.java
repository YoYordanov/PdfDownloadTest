import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.HashMap;

public class PdfDownloadTest {

    static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePref = new HashMap<>();
        chromePref.put("download.default_directory", System.getProperty("java.io.tmpdir"));
        options.setExperimentalOption("prefs", chromePref);

        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\tools\\chromedriver\\chromedriver.exe");

        driver = new ChromeDriver(options);
        PageFactory.initElements(driver, PdfDownloadPage.class);
    }

    @AfterClass
    public static void tearDown(){
        driver.quit();
    }

    @Test
    public void pdfDownloadTest(){
        //Checking if the file exist in temp folder and delete it.
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String tmpFolderPath = System.getProperty("java.io.tmpdir");
        String expectedFileName = "Download File.pdf";
        File file = new File(tmpFolderPath + expectedFileName);
        if (file.exists())
            file.delete();

        // Start downloading the file.
        driver.navigate().to("https://www.pdfjoin.com/");
        Assert.assertTrue("Expected page title must be: PDF Join - Join & Merge PDF Files Online for Free but actual it is: "+driver.getTitle(),
                driver.getTitle().contains("PDF Join - Join & Merge PDF Files Online for Free"));

        // Page interaction
        PdfDownloadPage pdfDownloadPage = new PdfDownloadPage();
        pdfDownloadPage.fileUploadField.get(0).sendKeys(System.getProperty("user.dir")+"\\testfiles\\Pdf 1.pdf");
        pdfDownloadPage.fileUploadField.get(1).sendKeys(System.getProperty("user.dir")+"\\testfiles\\Pdf 2.pdf");
        pdfDownloadPage.joinPdfsButton.click();

        // Validating the pdf download
        wait.until((ExpectedCondition<Boolean>) webDriver -> file.exists());
    }
}
