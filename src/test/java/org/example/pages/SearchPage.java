package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchPage {
    private final WebDriver driver;

    private final By oneWayLabel = By.cssSelector("label[for='one-way']");
    private final By roundTripLabel = By.cssSelector("label[for='round-trip']");
    private final By multiCityLabel = By.cssSelector("label[for='multi-city']");

    private final By fromInput = By.id("input_from");
    private final By fromOptions = By.cssSelector("#input_from ~ .dropdown-menu li.searchflight-item");

    private final By toInput = By.id("input_to");
    private final By toOptions = By.cssSelector("#input_to ~ .dropdown-menu li.searchflight-item");

    private final By departureInput = By.id("input_departure");
    private final By departureDates = By.cssSelector("td.available");

    private final By returnInput = By.id("input_return");
    private final By returnDates = By.cssSelector("td.available");

    private final By airlineInput = By.cssSelector("input.airline-text");
    private final By airlineOptions = By.cssSelector("a.airline-item");

    private final By paxInput = By.cssSelector("input.pax-text");
    private final By addAdultBtn = By.cssSelector("button.pax-add[data-value='adult']");
    private final By addChildBtn = By.cssSelector("button.pax-add[data-value='children']");
    private final By addInfantBtn = By.cssSelector("button.pax-add[data-value='infant']");

    private final By travelClassInput = By.cssSelector("input.travel-class-text");

    private final By oneWaySearchBtn = By.cssSelector(".js-one-way .btn-search");
    private final By roundTripSearchBtn = By.cssSelector(".js-round-trip .btn-search");
    private final By multiSearchBtn = By.cssSelector(".js-multi .btn-search");


    public SearchPage(WebDriver driver) {
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

    public String selectDepartureDate(String day) {
        driver.findElement(departureInput).click();

        List<WebElement> dates = driver.findElements(departureDates);
        boolean found = false;
        for (WebElement date : dates) {
            if (date.getText().equals(day)) {
                date.click();
                found = true;
                break;
            }
        }

        if(!found){
            return "Không cho phép chọn";
        }
        return "";
    }

    public String selectReturnDate(String day) {
        driver.findElement(returnInput).click();
        List<WebElement> dates = driver.findElements(returnDates);
        boolean found = false;
        for (WebElement date : dates) {
            if (date.getText().equals(day)) {
                date.click();
                found = true;
                break;
            }
        }
        if(!found){
            return "Không cho phép chọn";
        }
        return "";
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
