package org.example.runner.FlightList;

import org.example.constant.Constant;
import org.example.pages.FlightList.FlightListPage;
import org.example.runner.SearchFlight.SearchFlightRunTestcase;
import org.example.utils.ExcelUtils;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class FlightListRunTestCase {

    public String flightListOneWay(WebDriver driver) throws Exception{
        //Search Flight
        List<String[]> testDataSearch = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        int indexSearchFlight = 1;
        String[] rowSearch = testDataSearch.get(indexSearchFlight);
        SearchFlightRunTestcase.doSearch(driver, rowSearch);

        //Flight List
        List<String[]> testDataFlightList = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_FLIGHTLIST_SHEET);
        int indexFlightList = 1;
        String[] rowFlightList = testDataFlightList.get(indexFlightList);
        String oneWayIndex = rowFlightList[4];

        FlightListPage resultPage = new FlightListPage(driver);

        return resultPage.selectFlights(1, List.of(Integer.parseInt(oneWayIndex)));
    }

    public String flightListRoundTrip(WebDriver driver) throws Exception{
        //Search Flight
        List<String[]> testDataSearch = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        int indexSearchFlight = 7;
        String[] rowSearch = testDataSearch.get(indexSearchFlight);
        SearchFlightRunTestcase.doSearch(driver, rowSearch);

        //Flight List
        List<String[]> testDataFlightList = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_FLIGHTLIST_SHEET);
        int indexFlightList = 3;
        String[] rowFlightList = testDataFlightList.get(indexFlightList);
        String roundTripIndex1 = rowFlightList[5];
        String roundTripIndex2 = rowFlightList[6];

        FlightListPage resultPage = new FlightListPage(driver);
        resultPage.waitForResult();

        String actualMessage = resultPage.selectFlights(2, List.of(
                Integer.parseInt(roundTripIndex1),
                Integer.parseInt(roundTripIndex2)
        ));

        if(actualMessage.equals("Pass")){
            resultPage.clickNextPage();
        }

        return actualMessage;
    }

    public String flightListMultiCity(WebDriver driver) throws Exception{
        //Search Flight
        List<String[]> testDataSearch = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_SEARCHFLIGHT_SHEET);
        int indexSearchFlight = 12;
        String[] rowSearch = testDataSearch.get(indexSearchFlight);
        SearchFlightRunTestcase.doSearch(driver, rowSearch);

        //Flight List
        List<String[]> testDataFlightList = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_FLIGHTLIST_SHEET);
        int indexFlightList = 5;
        String[] rowFlightList = testDataFlightList.get(indexFlightList);
        String multiCityIndex1 = rowFlightList[7];
        String multiCityIndex2 = rowFlightList[8];
        String multiCityIndex3 = rowFlightList[9];

        FlightListPage resultPage = new FlightListPage(driver);
        resultPage.waitForResult();

        String actualMessage = resultPage.selectFlights(3, List.of(
                Integer.parseInt(multiCityIndex1),
                Integer.parseInt(multiCityIndex2),
                Integer.parseInt(multiCityIndex3)
        ));

        if(actualMessage.equals("Pass")){
            resultPage.clickNextPage();
        }

        return actualMessage;
    }
}
