import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PdfDownloadPage{

    @FindBy(className = ("file"))
    public static List<WebElement> fileUploadField;

    @FindBy(id = ("btnSubmit"))
    public  static WebElement joinPdfsButton;

}
