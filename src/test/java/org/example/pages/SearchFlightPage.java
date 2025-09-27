package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SearchFlightPage {
    private final WebDriver driver;

    //Direction
    private final By oneWayLabel = By.cssSelector("label[for='one-way']");
    private final By roundTripLabel = By.cssSelector("label[for='round-trip']");
    private final By multiCityLabel = By.cssSelector("label[for='multi-city']");

    //From
    private final By fromInput = By.id("input_from");
    private final By fromOptions = By.cssSelector("#input_from ~ .dropdown-menu li.searchflight-item");

    //To
    private final By toInput = By.id("input_to");
    private final By toOptions = By.cssSelector("#input_to ~ .dropdown-menu li.searchflight-item");

    //From Multi 2
    private final By fromMulti2Input = By.id("multi_from_2");
    private final By fromMulti2Options = By.cssSelector("#multi_from_2 ~ .dropdown-menu li.searchflight-item");

    //To Multi 2
    private final By toMulti2Input = By.id("multi_to_2");
    private final By toMulti2Options = By.cssSelector("#multi_to_2 ~ .dropdown-menu li.searchflight-item");

    //From Multi 3
    private final By fromMulti3Input = By.id("multi_from_3");
    private final By fromMulti3Options = By.cssSelector("#multi_from_3 ~ .dropdown-menu li.searchflight-item");

    //To Multi 3
    private final By toMulti3Input = By.id("multi_to_3");
    private final By toMulti3Options = By.cssSelector("#multi_to_3 ~ .dropdown-menu li.searchflight-item");

    //Add Flight (Multi Trip)
    private final By addFlightButton = By.cssSelector("button.btn-add-flight");

    //Departure
    private final By departureInput = By.id("input_departure");
    private final By departureCalendar = By.cssSelector("div.daterangepicker.opensright.show-calendar");

    //Return
    private final By returnInput = By.id("input_return");
    private final By returnCalendar = By.cssSelector("div.daterangepicker.opensleft.show-calendar");

    //Component In Calendar
    private final By monthLabel = By.cssSelector("th.month");
    private final By nextButton = By.cssSelector("th.next");
    private final By prevButton = By.cssSelector("th.prev");
    private final By availableDates = By.cssSelector("td.available");

    //Airline
    private final By airlineInput = By.cssSelector("input.airline-text");
    private final By airlineOptions = By.cssSelector("a.airline-item");

    //Passenger Number
    private final By paxInput = By.cssSelector("input.pax-text");
    private final By addAdultBtn = By.cssSelector("button.pax-add[data-value='adult']");
    private final By addChildBtn = By.cssSelector("button.pax-add[data-value='children']");
    private final By addInfantBtn = By.cssSelector("button.pax-add[data-value='infant']");

    //Travel Class
    private final By travelClassInput = By.cssSelector("input.travel-class-text");

    //Button Search
    private final By oneWaySearchBtn = By.cssSelector(".js-one-way .btn-search");
    private final By roundTripSearchBtn = By.cssSelector(".js-round-trip .btn-search");
    private final By multiSearchBtn = By.cssSelector(".js-multi .btn-search");


    public SearchFlightPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectDirection(String type) {
        switch (type.toLowerCase()) {
            case "one-way":
                driver.findElement(oneWayLabel).click();
                break;
            case "round-trip":
                driver.findElement(roundTripLabel).click();
                break;
            case "multi-city":
                driver.findElement(multiCityLabel).click();
                break;
            default:
                throw new IllegalArgumentException("Direction not found: " + type);
        }
    }

    public void selectFromCity(String cityName) {
        driver.findElement(fromInput).click();

        List<WebElement> options = driver.findElements(fromOptions);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-city")).equalsIgnoreCase(cityName)) {
                option.click();
                break;
            }
        }
    }

    public void selectToCity(String cityName) {
        driver.findElement(toInput).click();

        List<WebElement> options = driver.findElements(toOptions);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-city")).equalsIgnoreCase(cityName)) {
                option.click();
                break;
            }
        }
    }

    public void selectMultiFrom2City(String cityName) {
        driver.findElement(fromMulti2Input).click();

        List<WebElement> options = driver.findElements(fromMulti2Options);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-city")).equalsIgnoreCase(cityName)) {
                option.click();
                break;
            }
        }
    }

    public void selectMultiTo2City(String cityName) {
        driver.findElement(toMulti2Input).click();

        List<WebElement> options = driver.findElements(toMulti2Options);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-city")).equalsIgnoreCase(cityName)) {
                option.click();
                break;
            }
        }
    }

    public void selectMultiFrom3City(String cityName) {
        driver.findElement(fromMulti3Input).click();

        List<WebElement> options = driver.findElements(fromMulti3Options);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-city")).equalsIgnoreCase(cityName)) {
                option.click();
                break;
            }
        }
    }

    public void selectMultiTo3City(String cityName) {
        driver.findElement(toMulti3Input).click();

        List<WebElement> options = driver.findElements(toMulti3Options);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-city")).equalsIgnoreCase(cityName)) {
                option.click();
                break;
            }
        }
    }

    public void clickAddFlight(){
        driver.findElement(addFlightButton).click();
    }

    public String selectDepartureDate(int day, Month month, int year) {
        driver.findElement(departureInput).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement calendar = wait.until(ExpectedConditions.visibilityOfElementLocated(departureCalendar));

        while (true) {
            String monthYearText = calendar.findElement(monthLabel).getText().trim();
            System.out.println("Month Year Departure Text: " + monthYearText);

            String[] parts = monthYearText.split(" ");
            if (parts.length < 2) return "Không cho phép chọn";

            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
            Month currentMonth = Month.from(monthFormatter.parse(parts[0]));
            int currentYear = Integer.parseInt(parts[1]);

            if (currentYear == year && currentMonth == month) {
                break;
            } else if (currentYear < year || (currentYear == year && currentMonth.getValue() < month.getValue())) {
                calendar.findElement(nextButton).click();
            } else {
                calendar.findElement(prevButton).click();
            }
        }

        for (WebElement date : calendar.findElements(availableDates)) {
            if (date.getText().equals(String.valueOf(day))) {
                date.click();
                return "";
            }
        }
        return "Không cho phép chọn";
    }

    public String selectReturnDate(int day, Month month, int year) {
        driver.findElement(returnInput).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement calendar = wait.until(ExpectedConditions.visibilityOfElementLocated(returnCalendar));

        while (true) {
            String monthYearText = calendar.findElement(monthLabel).getText().trim();
            System.out.println("Month Year Return Text: " + monthYearText);

            String[] parts = monthYearText.split(" ");
            if (parts.length < 2) return "Không cho phép chọn";

            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
            Month currentMonth = Month.from(monthFormatter.parse(parts[0]));
            int currentYear = Integer.parseInt(parts[1]);

            if (currentYear == year && currentMonth == month) {
                break;
            } else if (currentYear < year || (currentYear == year && currentMonth.getValue() < month.getValue())) {
                calendar.findElement(nextButton).click();
            } else {
                calendar.findElement(prevButton).click();
            }
        }

        for (WebElement date : calendar.findElements(availableDates)) {
            if (date.getText().equals(String.valueOf(day))) {
                date.click();
                return "";
            }
        }
        return "Không cho phép chọn";
    }

    public void selectAirlineByCode(String code) {
        driver.findElement(airlineInput).click();
        List<WebElement> options = driver.findElements(airlineOptions);
        for (WebElement option : options) {
            if (Objects.requireNonNull(option.getAttribute("data-value")).equalsIgnoreCase(code)) {
                option.click();
                break;
            }
        }
    }

    public void openPassengerDropdown() {
        driver.findElement(paxInput).click();
    }

    public void addAdult(int count) {
        for (int i = 0; i < count; i++) {
            driver.findElement(addAdultBtn).click();
        }
    }

    public void addChild(int count) {
        for (int i = 0; i < count; i++) {
            driver.findElement(addChildBtn).click();
        }
    }

    public void addInfant(int count) {
        for (int i = 0; i < count; i++) {
            driver.findElement(addInfantBtn).click();
        }
    }

    public void selectTravelClass(String value) {
        driver.findElement(travelClassInput).click();
        By option = By.cssSelector("a.travel-class-item[data-value='" + value + "']");
        driver.findElement(option).click();
    }

    public boolean clickSearchButton(String direction) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            By locator;

            switch (direction.toLowerCase()) {
                case "one-way":
                    locator = oneWaySearchBtn;
                    break;
                case "round-trip":
                    locator = roundTripSearchBtn;
                    break;
                case "multi-city":
                    locator = multiSearchBtn;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown direction: " + direction);
            }

            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
            btn.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.box-content")
            ));

            return true;

        } catch (Exception e){
            return false;
        }
    }

    public List<String> getAllErrorMessages() {
        List<String> errors = new ArrayList<>();

        List<WebElement> errorElements = driver.findElements(
                By.cssSelector("span.form-text")
        );

        for (WebElement e : errorElements) {
            String msg = e.getText().trim();
            if (!msg.isEmpty()) {
                errors.add(msg);
            }
        }
        return errors;
    }
}
