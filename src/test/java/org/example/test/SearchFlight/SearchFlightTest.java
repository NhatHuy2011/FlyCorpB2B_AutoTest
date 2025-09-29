package org.example.test.SearchFlight;

import org.example.constant.Constant;
import org.example.runner.SearchFlight.SearchFlightResultHandler;
import org.example.runner.SearchFlight.SearchFlightRunTestcase;
import org.example.utils.ConfigReader;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class SearchFlightTest {
    private WebDriver driver;;

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
    public void testSearchFlightOneWay() throws Exception {
        List<String[]> testData = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        for (int i = 1; i <= 5; i++) {
            String[] row = testData.get(i);
            var result = SearchFlightRunTestcase.validateSearch(driver, row);
            SearchFlightResultHandler.writeResult( i + 2, result);
            driver.get(ConfigReader.getUrl());
        }
    }

    @Test
    public void testSearchFlightRoundTrip() throws Exception {
        List<String[]> testData = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        for (int i = 7; i <= 9; i++) {
            String[] row = testData.get(i);
            var result = SearchFlightRunTestcase.validateSearch(driver, row);
            SearchFlightResultHandler.writeResult(i + 2, result);
            driver.get(ConfigReader.getUrl());
        }
    }

    @Test
    public void testSearchFlightMultiCity() throws Exception {
        List<String[]> testData = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        for (int i = 12; i <= 12; i++) {
            String[] row = testData.get(i);
            var result = SearchFlightRunTestcase.validateSearch(driver, row);
            SearchFlightResultHandler.writeResult(i + 2, result);
            driver.get(ConfigReader.getUrl());
        }
    }

    @Test
    public void testOneSearchFlight() throws Exception {
        int targetRowIndex = 12;
        List<String[]> testData = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        String[] row = testData.get(targetRowIndex);
        var result = SearchFlightRunTestcase.validateSearch(driver, row);
        SearchFlightResultHandler.writeResult( targetRowIndex + 2, result);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
