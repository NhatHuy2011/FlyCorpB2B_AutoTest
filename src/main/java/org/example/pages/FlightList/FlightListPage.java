package org.example.pages.FlightList;

import org.example.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FlightListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public FlightListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By resultContainer = By.id("js-search-result");
    private final By noFlightMessage = By.xpath("//div[@id='js-search-result' and contains(text(),'Không tìm thấy chuyến bay')]");
    private final By nextPageButton = By.id("btn-next-page");

    public void waitForResult() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultContainer));
    }

    public boolean hasNoFlights() {
        List<WebElement> flights = driver.findElements(By.cssSelector("#js-search-result .flight-item"));
        return flights.isEmpty();
    }

    // Lấy thông báo khi không có chuyến bay
    public String getNoFlightMessage() {
        if (hasNoFlights()) {
            return driver.findElement(noFlightMessage).getText().trim();
        }
        return null;
    }

    private WebElement waitForCabinClassByKey(int key) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".btn-cabin-class[data-key='" + key + "']")));
    }

    private WebElement waitForContinueButtonByKey(int key) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-orange.vn-choose[data-key='" + key + "']")));
    }

    public void clickCabinClassByKey(int key) {
        waitForCabinClassByKey(key).click();
    }

    public void clickContinueButtonByKey(int key) {
        waitForContinueButtonByKey(key).click();
    }

    public String selectFlights(int step, List<Integer> keys) {
        for (int i = 0; i < step; i++) {
            waitForResult();
            if(hasNoFlights()){
                return getNoFlightMessage() + " ở step " + (i + 1);
            }
            int key = keys.get(i);
            clickCabinClassByKey(key);
            clickContinueButtonByKey(key);
        }
        return "Pass";
    }

    public boolean clickNextPage() {
        driver.findElement(nextPageButton).click();
        return wait.until(ExpectedConditions.urlToBe(ConfigReader.getUrlOffer()));
    }
}