package org.example.pages.ContactInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Month;

public class ContactInfoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ContactInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By adultLastName0Input = By.id("adult_surname_0");
    private final By adultFirstName0Input = By.id("adult_givenName_0");
    private final By adultTitle0Select = By.id("adult_title_0");
    private final By adultBirthday0Input = By.id("adult_birthday_0");
    private final By adultNationality0   = By.id("adult_nationality_0");
    private final By adultPassport0      = By.id("adult_passport_0");
    private final By adultPassportNation0= By.id("adult_conOfIssue_0");
    private final By adultIssueDate0 = By.id("adult_issueDate_0");
    private final By adultExpireDate0 = By.id("adult_expiredDate_0");

    private final By contactLastName     = By.id("contact_last_name");
    private final By contactFirstName    = By.id("contact_first_name");
    private final By contactPhone = By.id("contact_phone");
    private final By contactEmail = By.id("contact_email");
    private final By bookingButton    = By.id("btn-next");

    private final By confirmButton = By.cssSelector("button.btn.btn-primary.rounded-3[type='submit']");

    public void setAdultLastName0(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(adultLastName0Input));
        input.clear();
        input.sendKeys(value);
    }

    public void setAdultFirstName0(String value) {
        WebElement input = driver.findElement(adultFirstName0Input);
        input.clear();
        input.sendKeys(value);
    }

    public void selectAdultTitle0(String value) {
        new Select(driver.findElement(adultTitle0Select)).selectByValue(value);
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

    public void setContactPhone(String value){
        WebElement input = driver.findElement(contactPhone);
        input.clear();
        input.sendKeys(value);
    }

    public void clickBooking() {
        WebElement bookingBtn = driver.findElement(bookingButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookingBtn);
        bookingBtn.click();
    }

    public void clickConfirmButton() {
        WebElement button = driver.findElement(confirmButton);
        button.click();
    }
}
