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
    private final By cabinClassButtons = By.cssSelector(".btn-cabin-class");
    private final By continueButtons = By.cssSelector("button.btn.btn-orange.vn-choose");

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

    public List<WebElement> getCabinClassButtons() {
        return driver.findElements(cabinClassButtons);
    }

    public List<WebElement> getContinueButtons() {
        return driver.findElements(continueButtons);
    }

    public void clickCabinClassByIndex(int index) {
        List<WebElement> buttons = getCabinClassButtons();
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void clickContinueButtonByIndex(int index) {
        List<WebElement> buttons = getContinueButtons();
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).click();
        }
    }
}
