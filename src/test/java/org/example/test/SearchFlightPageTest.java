package org.example.test;

import org.example.pages.SearchFlightPage;
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
import java.time.Month;
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

            if (handleDepartureDateError(searchFlightPage, departureDate, expected, rowIndex)) {
                return;
            }

            if (direction.equals("round-trip")) {
                if (handleReturnDateError(searchFlightPage, returnDate, expected, rowIndex)) {
                    return;
                }
            }

            if(direction.equals("multi-city")){
                searchFlightPage.selectMultiFrom2City(multiFrom2);
                searchFlightPage.selectMultiTo2City(multiTo2);

                searchFlightPage.clickAddFlight();

                searchFlightPage.selectMultiFrom3City(multiFrom3);
                searchFlightPage.selectMultiTo3City(multiTo3);
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

    // Handle Error Departure Date
    private boolean handleDepartureDateError(SearchFlightPage searchFlightPage, String departureDate, String expected, int rowIndex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(departureDate, formatter);

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        String invalidMessage = searchFlightPage.selectDepartureDate(day, Month.of(month), year);
        if (!invalidMessage.isEmpty()) {
            boolean isTestPassed = expected.trim().equalsIgnoreCase(invalidMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, invalidMessage, 21, isTestPassed ? "Pass" : "Fail", 22);
            return true;
        }
        return false;
    }

    // Handle Error Return Date
    private boolean handleReturnDateError(SearchFlightPage searchFlightPage, String returnDate, String expected, int rowIndex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(returnDate, formatter);

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        String invalidReturnMessage = searchFlightPage.selectReturnDate(day, Month.of(month), year);
        if (!invalidReturnMessage.isEmpty()) {
            boolean isTestPassed = expected.trim().equalsIgnoreCase(invalidReturnMessage);
            ExcelUtils.writeTestResults(FILE_PATH, "SearchFlight", rowIndex, invalidReturnMessage, 21, isTestPassed ? "Pass" : "Fail", 22);
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
