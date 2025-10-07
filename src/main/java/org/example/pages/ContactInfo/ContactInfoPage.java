package org.example.pages.ContactInfo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ContactInfoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final PassengerForm passengerForm;
    private final ContactInfoCommon contactInfoCommon;

    public ContactInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.contactInfoCommon = new ContactInfoCommon(driver);
        this.passengerForm = new PassengerForm(driver);
    }

    //Contact Info
    private final By contactLastName = By.id("contact_last_name");
    private final By contactFirstName = By.id("contact_first_name");
    private final By selectedCountryButton = By.cssSelector("button.iti__selected-country");
    private final By searchInput = By.cssSelector("input.iti__search-input");
    private final String countryItem = "//li[contains(@class,'iti__country') and .//span[contains(@class,'iti__country-name') and normalize-space(text())='%s']]";
    private final By contactPhone = By.id("contact_phone");
    private final By contactEmail = By.id("contact_email");
    private final By bookingButton = By.id("btn-next");
    private final By confirmButton = By.cssSelector("button.btn.btn-primary.rounded-3[type='submit']");

    public PassengerForm passengers() {
        return passengerForm;
    }

    public void setContactInfo(String lastName, String firstName, String countryName, String phone) {
        driver.findElement(contactLastName).sendKeys(lastName);
        driver.findElement(contactFirstName).sendKeys(firstName);
        selectPhoneCountryCode(countryName);
        driver.findElement(contactPhone).sendKeys(phone);
        //driver.findElement(contactEmail).sendKeys(email);
    }

    private void openCountryDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(selectedCountryButton)).click();
    }

    private void searchCountry(String countryName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(countryName);
    }

    private void selectCountry(String countryName) {
        String xpath = String.format(countryItem, countryName);
        WebElement countryOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        countryOption.click();
    }

    public void selectPhoneCountryCode(String countryName) {
        openCountryDropdown();
        searchCountry(countryName);
        selectCountry(countryName);
    }

    public void clickBooking() {
        WebElement bookingBtn = wait.until(ExpectedConditions.elementToBeClickable(bookingButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookingBtn);
        bookingBtn.click();
    }

    public void clickConfirmButton() {
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmBtn);
        confirmBtn.click();
    }
}
