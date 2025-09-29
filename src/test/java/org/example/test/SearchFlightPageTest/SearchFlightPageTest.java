package org.example.test.SearchFlightPageTest;

import org.example.pages.SearchFlightPage.SearchFlightPage;
import org.example.utils.ConfigReader;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class SearchFlightPageTest {
    private WebDriver driver;
    private static final String FILE_PATH = ConfigReader.getExcelPath();

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
        List<String[]> testData = ExcelUtils.readExcel(FILE_PATH, ConfigReader.getSheetSearchFlight());
        int start = 1;
        int end = 5;
        for (int i = start; i<=end; i++){
            String[] row = testData.get(i);
            runTestCase(row, i + 2);
            driver.get(ConfigReader.getUrl());
        }
    }

    @Test
    public void testSearchFlightRoundTrip() throws Exception {
        List<String[]> testData = ExcelUtils.readExcel(FILE_PATH, ConfigReader.getSheetSearchFlight());
        int start = 7;
        int end = 9;
        for (int i = start; i<=end; i++){
            String[] row = testData.get(i);
            runTestCase(row, i + 2);
            driver.get(ConfigReader.getUrl());
        }
    }

    @Test
    public void testOneSearchFlight() throws Exception {
        int targerRowIndex = 12;
        List<String[]> testData = ExcelUtils.readExcel(FILE_PATH, ConfigReader.getSheetSearchFlight());
        String[] row = testData.get(targerRowIndex);
        runTestCase(row, targerRowIndex + 2);
    }

    private void runTestCase(String[] row, int rowIndex) throws Exception {
        String direction = row[4];
        String inputFrom = row[5];
        String inputTo = row[6];
        String multiFrom2 = row[7];
        String multiTo2 = row[8];
        String multiFrom3 = row[9];
        String multiTo3 = row[10];
        String departureDate = row[11];
        String returnDate = direction.equals("round-trip") ? row[12] : "";
        String multiDeparture2 = direction.equals("multi-city") ? row[13] : "";
        String multiDeparture3 = direction.equals("multi-city") ? row[14] : "";
        String inputAirline = row[15];
        String adult = row[16];
        String child = row[17];
        String infant = row[18];
        String travelClass = row[19];
        String expected = row[20];

        SearchFlightPage searchFlightPage = new SearchFlightPage(driver);
        String actualMessage;
        boolean isTestPassed;

        try {
            searchFlightPage.selectDirection(direction);
            searchFlightPage.selectFromCity(inputFrom);
            searchFlightPage.selectToCity(inputTo);

            if (handleDateError(departureDate, expected, rowIndex,
                    d -> searchFlightPage.selectDepartureDate(d.getDayOfMonth(), d.getMonth(), d.getYear()))) {
                return;
            }

            if (direction.equals("round-trip")) {
                if (handleDateError(returnDate, expected, rowIndex,
                        d -> searchFlightPage.selectReturnDate(d.getDayOfMonth(), d.getMonth(), d.getYear()))) {
                    return;
                }
            }

            if(direction.equals("multi-city")){
                searchFlightPage.selectMultiFrom2City(multiFrom2);
                searchFlightPage.selectMultiTo2City(multiTo2);

                if (handleDateError(multiDeparture2, expected, rowIndex,
                        d -> searchFlightPage.selectMultiDeparture2(d.getDayOfMonth(), d.getMonth(), d.getYear()))) {
                    return;
                }

                searchFlightPage.clickAddFlight();

                searchFlightPage.selectMultiFrom3City(multiFrom3);
                searchFlightPage.selectMultiTo3City(multiTo3);

                if (handleDateError(multiDeparture3, expected, rowIndex,
                        d -> searchFlightPage.selectMultiDeparture3(d.getDayOfMonth(), d.getMonth(), d.getYear()))) {
                    return;
                }
            }

            searchFlightPage.openPassengerDropdown();
            searchFlightPage.addAdult(Integer.parseInt(adult));
            searchFlightPage.addChild(Integer.parseInt(child));
            searchFlightPage.addInfant(Integer.parseInt(infant));
            searchFlightPage.selectTravelClass(travelClass);

            boolean click = searchFlightPage.clickSearchButton(direction);
            isTestPassed = handleSearchErrors(searchFlightPage, expected, click, rowIndex);

        } catch (Exception e) {
            actualMessage = e.getMessage();
            isTestPassed = expected.trim().equalsIgnoreCase(actualMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, actualMessage, 21, isTestPassed ? "Pass" : "Fail", 22);
        }
    }

    //Handle Date Error
    private boolean handleDateError(
            String dateStr,
            String expected,
            int rowIndex,
            java.util.function.Function<LocalDate, String> dateSelector
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        String invalidMessage = dateSelector.apply(date);
        if (!invalidMessage.isEmpty()) {
            boolean isTestPassed = expected.trim().equalsIgnoreCase(invalidMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, invalidMessage, 21,
                    isTestPassed ? "Pass" : "Fail", 22);
            return true;
        }
        return false;
    }

    // Handle Error After Click Search
    private boolean handleSearchErrors(SearchFlightPage searchFlightPage, String expected, boolean click, int rowIndex) {
        List<String> actualErrors = searchFlightPage.getAllErrorMessages();
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
                actualMessage = actualErrors.isEmpty() ? "" : actualErrors.getFirst();
            }
            isTestPassed = expected.trim().equalsIgnoreCase(actualMessage);
        }

        ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, actualMessage, 21, isTestPassed ? "Pass" : "Fail", 22);
        return isTestPassed;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
