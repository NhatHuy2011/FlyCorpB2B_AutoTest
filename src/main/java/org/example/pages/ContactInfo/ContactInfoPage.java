package org.example.pages.ContactInfo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Month;
import java.util.List;

public class ContactInfoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ContactInfoCommon contactInfoCommon;

    public ContactInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.contactInfoCommon = new ContactInfoCommon(driver);
    }

    private final By adultLastName0      = By.id("adult_surname_0");
    private final By adultFirstName0     = By.id("adult_givenName_0");
    private final By adultTitle0         = By.id("adult_title_0");

    private final By adultDob0 = By.id("adult_birthday_0");
    private final By yearSelect = By.cssSelector(".yearselect");
    private final By monthSelect = By.cssSelector(".monthselect");
    private final By dayCells = By.cssSelector("td.available");

    private final By adultNationality0   = By.id("adult_nationality_0");
    private final By adultPassport0      = By.id("adult_passport_0");
    private final By adultPassportNation0= By.id("adult_conOfIssue_0");
    private final By adultIssueDate0     = By.id("adult_issueDate_0");

    private final By adultExpiredDate0   = By.id("adult_expiredDate_0");

    private final By contactLastName     = By.id("contact_last_name");
    private final By contactFirstName    = By.id("contact_first_name");

    private final By bookingButton    = By.id("btn-next");

    public void setAdultLastName0(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(adultLastName0));
        input.clear();
        input.sendKeys(value);
    }

    public void setAdultFirstName0(String value) {
        WebElement input = driver.findElement(adultFirstName0);
        input.clear();
        input.sendKeys(value);
    }

    public void selectAdultTitle0(String value) {
        new Select(driver.findElement(adultTitle0)).selectByValue(value); // MR=1, MS=2
    }

    public void setAdultDob0() {
        contactInfoCommon.selectDate(adultDob0, 15, Month.SEPTEMBER, 1990);
    }

    public void selectAdultNationality0(String value) {
        new Select(driver.findElement(adultNationality0)).selectByValue(value);
    }

    public void setAdultPassport0(String value) {
        WebElement input = driver.findElement(adultPassport0);
        input.clear();
        input.sendKeys(value);
    }

    public void selectAdultPassportNation0(String value) {
        new Select(driver.findElement(adultPassportNation0)).selectByValue(value);
    }

    public void setAdultIssueDate0() {
        contactInfoCommon.selectDate(adultIssueDate0, 15, Month.SEPTEMBER, 2020);
    }

    public void setAdultExpiredDate0() {
        contactInfoCommon.selectDate(adultExpiredDate0, 15, Month.SEPTEMBER, 2030);
    }

    public void setContactLastName(String value) {
        WebElement input = driver.findElement(contactLastName);
        input.clear();
        input.sendKeys(value);
    }

    public void setContactFirstName(String value) {
        WebElement input = driver.findElement(contactFirstName);
        input.clear();
        input.sendKeys(value);
    }

    public void clickBooking() {
        WebElement bookingBtn = driver.findElement(bookingButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookingBtn);
        bookingBtn.click();
    }

    private final By confirmButton = By.cssSelector("button.btn.btn-primary.rounded-3[type='submit']");

    // Action
    public void clickConfirmButton() {
        WebElement button = driver.findElement(confirmButton);
        button.click();
    }
}
