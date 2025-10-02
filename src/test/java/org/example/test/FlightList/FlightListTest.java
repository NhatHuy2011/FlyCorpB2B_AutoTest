package org.example.test.FlightList;

import org.example.common.TestResult;
import org.example.common.WriteResultExcel;
import org.example.constant.Constant;
import org.example.pages.FlightList.FlightListPage;
import org.example.runner.SearchFlight.SearchFlightRunTestcase;
import org.example.utils.ConfigReader;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
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
    public void testFlightListOneWay() throws Exception {
        //Search Flight
        List<String[]> testDataSearch = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        int indexSearchFlight = 1;
        String[] rowSearch = testDataSearch.get(indexSearchFlight);
        SearchFlightRunTestcase.doSearch(driver, rowSearch);

        //Flight List
        List<String[]> testDataFlightList = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH,
                Constant.EXCEL_FLIGHTLIST_SHEET);
        int indexFlightList = 1;
        String[] rowFlightList = testDataFlightList.get(indexFlightList);
        String oneWayIndex = rowFlightList[4];

        FlightListPage resultPage = new FlightListPage(driver);

        String resultMessage = resultPage.selectFlights(1, List.of(Integer.parseInt(oneWayIndex)));

        boolean isPassed = true;

        String actualMessage = "";
        if (resultMessage.equals("Pass")){
            actualMessage = "Hiện form thông tin liên lạc";
        } else {
            actualMessage = resultMessage;
        }

        TestResult testResult = new TestResult(actualMessage, isPassed);

        WriteResultExcel.writeResultExcel(Constant.EXCEL_FLIGHTLIST_SHEET,
                indexFlightList + 2,
                testResult,
                Constant.EXCEL_FLIGHTLIST_SHEET_ACTUALINDEX,
                Constant.EXCEL_FLIGHTLIST_SHEET_STATUS);
    }

    @Test
    public void testFlightListRoundTrip() throws Exception {
        //Search Flight
        List<String[]> testDataSearch = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH,
                Constant.EXCEL_SEARCHFLIGHT_SHEET);
        int indexSearchFlight = 7;
        String[] rowSearch = testDataSearch.get(indexSearchFlight);
        SearchFlightRunTestcase.doSearch(driver, rowSearch);

        //Flight List
        List<String[]> testDataFlightList = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH,
                Constant.EXCEL_FLIGHTLIST_SHEET);
        int indexFlightList = 3;
        String[] rowFlightList = testDataFlightList.get(indexFlightList);
        String roundTripIndex1 = rowFlightList[5];
        String roundTripIndex2 = rowFlightList[6];

        FlightListPage resultPage = new FlightListPage(driver);
        resultPage.waitForResult();

        String resultMessage = resultPage.selectFlights(2, List.of(
                Integer.parseInt(roundTripIndex1),
                Integer.parseInt(roundTripIndex2)
        ));

        String actualMessage = "";

        if(resultMessage.equals("Pass")){
            boolean result = resultPage.clickNextPage();
            if(result){
                actualMessage = "Hiện form thông tin liên lạc";
            }else {
                actualMessage = resultMessage;
            }
        }

        boolean isPassed = true;

        TestResult testResult = new TestResult(actualMessage, isPassed);

        WriteResultExcel.writeResultExcel(Constant.EXCEL_FLIGHTLIST_SHEET,
                indexFlightList + 2,
                testResult,
                Constant.EXCEL_FLIGHTLIST_SHEET_ACTUALINDEX,
                Constant.EXCEL_FLIGHTLIST_SHEET_STATUS);
    }

    @Test
    public void testFlightListMultiCity() throws Exception {
        //Search Flight
        List<String[]> testDataSearch = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH,
                Constant.EXCEL_SEARCHFLIGHT_SHEET);
        int indexSearchFlight = 12;
        String[] rowSearch = testDataSearch.get(indexSearchFlight);
        SearchFlightRunTestcase.doSearch(driver, rowSearch);

        //Flight List
        List<String[]> testDataFlightList = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH,
                Constant.EXCEL_FLIGHTLIST_SHEET);
        int indexFlightList = 5;
        String[] rowFlightList = testDataFlightList.get(indexFlightList);
        String multiCityIndex1 = rowFlightList[7];
        String multiCityIndex2 = rowFlightList[8];
        String multiCityIndex3 = rowFlightList[9];

        FlightListPage resultPage = new FlightListPage(driver);
        resultPage.waitForResult();

        String resultMessage = resultPage.selectFlights(3, List.of(
                Integer.parseInt(multiCityIndex1),
                Integer.parseInt(multiCityIndex2),
                Integer.parseInt(multiCityIndex3)
        ));

        String actualMessage = "";
        if(resultMessage.equals("Pass")){
            boolean result = resultPage.clickNextPage();
            if(result){
                actualMessage = "Hiện form thông tin liên lạc";
            }else {
                actualMessage = resultMessage;
            }
        }

        boolean isPassed = true;

        TestResult testResult = new TestResult(actualMessage, isPassed);

        WriteResultExcel.writeResultExcel(Constant.EXCEL_FLIGHTLIST_SHEET,
                indexFlightList + 2,
                testResult,
                Constant.EXCEL_FLIGHTLIST_SHEET_ACTUALINDEX,
                Constant.EXCEL_FLIGHTLIST_SHEET_STATUS);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
