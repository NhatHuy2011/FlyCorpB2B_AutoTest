package org.example.pages.FlightList;

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
        try {
            return driver.findElement(noFlightMessage).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getNoFlightMessage() {
        if (hasNoFlights()) {
            return driver.findElement(noFlightMessage).getText().trim();
        }
        return null;
    }

    private WebElement waitForCabinClassByKey(int key) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".btn-cabin-class[data-key='" + key + "']")));
    }

    private WebElement waitForContinueButtonByKey(int key) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-orange.vn-choose[data-key='" + key + "']")));
    }

    public void clickCabinClassByKey(int key) {
        waitForCabinClassByKey(key).click();
    }

    public void clickContinueButtonByKey(int key) {
        waitForContinueButtonByKey(key).click();
    }

    public void selectFlights(int legs, List<Integer> keys) {
        for (int i = 0; i < legs; i++) {
            waitForResult();
            if (hasNoFlights()) {
                return;
            }
            int key = keys.get(i);
            clickCabinClassByKey(key);
            clickContinueButtonByKey(key);
        }
    }


    public void clickNextPage() {
        driver.findElement(nextPageButton).click();
    }
}