package org.example.runner.SearchFlight;

import org.example.pages.SearchFlight.SearchFlightPage;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class SearchFlightRunTestcase {

    public static class TestResult {
        public final String actualMessage;
        public final boolean isPassed;

        public TestResult(String actualMessage, boolean isPassed) {
            this.actualMessage = actualMessage;
            this.isPassed = isPassed;
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static TestResult runTestCase(WebDriver driver, String[] row) throws Exception {
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

        try {
            searchFlightPage.selectDirection(direction);
            searchFlightPage.selectFromCity(inputFrom);
            searchFlightPage.selectToCity(inputTo);

            // Departure
            String depError = getDateError(departureDate,
                    d -> searchFlightPage.selectDepartureDate(d.getDayOfMonth(), d.getMonth(), d.getYear()));
            if (!depError.isEmpty()) {
                return new TestResult(depError, expected.trim().equalsIgnoreCase(depError));
            }

            // Return
            if (direction.equals("round-trip")) {
                String retError = getDateError(returnDate,
                        d -> searchFlightPage.selectReturnDate(d.getDayOfMonth(), d.getMonth(), d.getYear()));
                if (!retError.isEmpty()) {
                    return new TestResult(retError, expected.trim().equalsIgnoreCase(retError));
                }
            }

            // Multi-city
            if (direction.equals("multi-city")) {
                searchFlightPage.selectMultiFrom2City(multiFrom2);
                searchFlightPage.selectMultiTo2City(multiTo2);

                String multi2Error = getDateError(multiDeparture2,
                        d -> searchFlightPage.selectMultiDeparture2(d.getDayOfMonth(), d.getMonth(), d.getYear()));
                if (!multi2Error.isEmpty()) {
                    return new TestResult(multi2Error, expected.trim().equalsIgnoreCase(multi2Error));
                }

                searchFlightPage.clickAddFlight();
                searchFlightPage.selectMultiFrom3City(multiFrom3);
                searchFlightPage.selectMultiTo3City(multiTo3);

                String multi3Error = getDateError(multiDeparture3,
                        d -> searchFlightPage.selectMultiDeparture3(d.getDayOfMonth(), d.getMonth(), d.getYear()));
                if (!multi3Error.isEmpty()) {
                    return new TestResult(multi3Error, expected.trim().equalsIgnoreCase(multi3Error));
                }
            }

            // Passenger & class
            searchFlightPage.openPassengerDropdown();
            searchFlightPage.addAdult(Integer.parseInt(adult));
            searchFlightPage.addChild(Integer.parseInt(child));
            searchFlightPage.addInfant(Integer.parseInt(infant));
            searchFlightPage.selectTravelClass(travelClass);

            // Click search
            boolean click = searchFlightPage.clickSearchButton(direction);
            String actualMessage = getSearchError(searchFlightPage, click);

            boolean isPassed = compareExpected(expected, actualMessage);
            return new TestResult(actualMessage, isPassed);

        } catch (Exception e) {
            String actualMessage = e.getMessage();
            boolean isPassed = expected.trim().equalsIgnoreCase(actualMessage);
            return new TestResult(actualMessage, isPassed);
        }
    }

    private static String getDateError(String dateStr, Function<LocalDate, String> dateSelector) {
        if (dateStr == null || dateStr.isBlank()) return "";
        LocalDate date = LocalDate.parse(dateStr, FORMATTER);
        return dateSelector.apply(date); // trả về "" nếu không lỗi
    }

    private static String getSearchError(SearchFlightPage searchFlightPage, boolean click) {
        List<String> actualErrors = searchFlightPage.getAllErrorMessages();
        if (click && actualErrors.isEmpty()) {
            return "Hiện form danh sách chuyến bay";
        }
        return actualErrors.isEmpty() ? "" : actualErrors.get(0);
    }

    private static boolean compareExpected(String expected, String actualMessage) {
        if (expected.contains(";")) {
            List<String> expectedErrors = List.of(expected.split(";"));
            List<String> actualErrors = List.of(actualMessage.split(";"));
            return actualErrors.containsAll(expectedErrors) && expectedErrors.containsAll(actualErrors);
        } else {
            return expected.trim().equalsIgnoreCase(actualMessage);
        }
    }
}