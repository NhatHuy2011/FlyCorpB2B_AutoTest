package org.example.test;

import org.example.pages.SearchPage;
import org.example.utils.ConfigReader;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class SearchPageTest {
    private WebDriver driver;
    private static final String FILE_PATH = ConfigReader.getExcelPath();

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(ConfigReader.getUrl());

        Cookie agencySession = new Cookie(ConfigReader.getCookiesAgencyName(), ConfigReader.getCookiesAgencyValue());
        Cookie XSRFToken = new Cookie(ConfigReader.getCookiesXSRFTokenName(), ConfigReader.getCookiesXSRFTokenValue());

        driver.manage().addCookie(agencySession);
        driver.manage().addCookie(XSRFToken);

        driver.navigate().refresh();
    }

    @Test
    public void testSearchFlight() throws Exception {
        List<String[]> testData = ExcelUtils.readExcel(FILE_PATH, ConfigReader.getSheetSearchFlight());
        int rowIndex = 0;
        for (String[] row : testData){
            runTestCase(row, rowIndex + 2);
            driver.get(ConfigReader.getUrl());
            rowIndex++;
        }
    }

    @Test
    public void testOneSearchFlight() throws Exception {
        int targerRowIndex = 4;
        List<String[]> testData = ExcelUtils.readExcel(FILE_PATH, ConfigReader.getSheetSearchFlight());
        String[] row = testData.get(targerRowIndex);
        runTestCase(row, targerRowIndex + 2);
    }

    private void runTestCase(String[] row, int rowIndex) throws Exception {
        String direction = row[4];
        String inputFrom = row[5];
        String inputTo = row[6];
        String departureDate = row[7];
        String returnDate = direction.equals("round-trip") ? row[8] : "";
        String inputAirline = row[9];
        String adult = row[10];
        String child = row[11];
        String infant = row[12];
        String travelClass = row[13];
        String expected = row[14];

        SearchPage searchPage = new SearchPage(driver);
        String actualMessage;
        boolean isTestPassed;

        try {
            searchPage.selectDirection(direction);
            searchPage.selectFromCity(inputFrom);
            searchPage.selectToCity(inputTo);

            if (handleDepartureDateError(searchPage, departureDate, expected, rowIndex)) {
                return;
            }

            if (direction.equals("round-trip")) {
                if (handleReturnDateError(searchPage, returnDate, expected, rowIndex)) {
                    return;
                }
            }

            searchPage.openPassengerDropdown();
            searchPage.addAdult(Integer.parseInt(adult));
            searchPage.addChild(Integer.parseInt(child));
            searchPage.addInfant(Integer.parseInt(infant));
            searchPage.selectTravelClass(travelClass);

            boolean click = searchPage.clickSearchButton(direction);
            isTestPassed = handleSearchErrors(searchPage, expected, click, rowIndex);

        } catch (Exception e) {
            actualMessage = e.getMessage();
            isTestPassed = expected.trim().equalsIgnoreCase(actualMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, actualMessage, 15, isTestPassed ? "Pass" : "Fail", 16);
        }
    }

    // Xử lý lỗi ngày đi
    private boolean handleDepartureDateError(SearchPage searchPage, String departureDate, String expected, int rowIndex) {
        String invalidMessage = searchPage.selectDepartureDate(departureDate);
        if (!invalidMessage.isEmpty()) {
            boolean isTestPassed = expected.trim().equalsIgnoreCase(invalidMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, invalidMessage, 15, isTestPassed ? "Pass" : "Fail", 16);
            return true;
        }
        return false;
    }

    // Xử lý lỗi ngày về
    private boolean handleReturnDateError(SearchPage searchPage, String returnDate, String expected, int rowIndex) {
        String invalidReturnMessage = searchPage.selectReturnDate(returnDate);
        if (!invalidReturnMessage.isEmpty()) {
            boolean isTestPassed = expected.trim().equalsIgnoreCase(invalidReturnMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, invalidReturnMessage, 15, isTestPassed ? "Pass" : "Fail", 16);
            return true;
        }
        return false;
    }

    // Xử lý lỗi sau khi nhấn nút tìm kiếm
    private boolean handleSearchErrors(SearchPage searchPage, String expected, boolean click, int rowIndex) {
        List<String> actualErrors = searchPage.getAllErrorMessages();
        String actualMessage;
        boolean isTestPassed;

        if (expected.contains(";")) {
            List<String> expectedErrors = Arrays.stream(expected.split(";"))
                    .map(String::trim)
                    .toList();
            isTestPassed = actualErrors.containsAll(expectedErrors) && expectedErrors.containsAll(actualErrors);
            actualMessage = String.join(";", actualErrors);
        } else {
            if (click && actualErrors.isEmpty()) {
                actualMessage = "Hiện form danh sách chuyến bay";
            } else {
                actualMessage = actualErrors.isEmpty() ? "" : actualErrors.get(0);
            }
            isTestPassed = expected.trim().equalsIgnoreCase(actualMessage);
        }

        ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, actualMessage, 15, isTestPassed ? "Pass" : "Fail", 16);
        return isTestPassed;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
