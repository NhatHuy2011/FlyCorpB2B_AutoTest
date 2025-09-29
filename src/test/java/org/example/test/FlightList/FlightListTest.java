package org.example.test.FlightList;

import org.example.constant.Constant;
import org.example.pages.FlightList.FlightListPage;
import org.example.pages.SearchFlight.SearchFlightPage;
import org.example.utils.ConfigReader;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class FlightListTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(ConfigReader.getUrl());

        Cookie agencySession = new Cookie(ConfigReader.getCookiesAgencySessionName(), ConfigReader.getCookiesAgencySessionValue());
        Cookie XSRFToken = new Cookie(ConfigReader.getCookiesXSRFTokenName(), ConfigReader.getCookiesXSRFTokenValue());

        driver.manage().addCookie(agencySession);
        driver.manage().addCookie(XSRFToken);

        driver.navigate().refresh();
    }

    @Test
    public void testFlightList() throws Exception {
        int targetRowIndex = 1;
        List<String[]> testData = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_FLIGHTLIST_SHEET);
        String[] row = testData.get(targetRowIndex);

        // --- Thực hiện search flight ---
        SearchFlightPage searchFlightPage = new SearchFlightPage(driver);
        searchFlightPage.selectDirection(row[4]);
        searchFlightPage.selectFromCity(row[5]);
        searchFlightPage.selectToCity(row[6]);

        // Ngày đi
        searchFlightPage.selectDepartureDate(1, java.time.Month.DECEMBER, 2025);

        // Passengers + class
        searchFlightPage.openPassengerDropdown();
        searchFlightPage.addAdult(1);
        searchFlightPage.selectTravelClass("economy");

        // Click search
        searchFlightPage.clickSearchButton(row[4]);

        FlightListPage resultPage = new FlightListPage(driver);
        resultPage.waitForResult();

        if (resultPage.hasNoFlights()) {
            System.out.println("No flights found: " + resultPage.getNoFlightMessage());
        } else {
            List<WebElement> cabins = resultPage.getCabinClassButtons();
            if (!cabins.isEmpty()) {
                cabins.get(0).click();
                Thread.sleep(1000);
            }

            List<WebElement> continueBtns = resultPage.getContinueButtons();
            if (!continueBtns.isEmpty()) {
                continueBtns.get(0).click();
                System.out.println("Clicked continue → chuyển sang trang offer");
            }
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
