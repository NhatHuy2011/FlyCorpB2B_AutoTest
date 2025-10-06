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
    private final By contactPhone = By.id("contact_phone");
    private final By contactEmail = By.id("contact_email");
    private final By bookingButton = By.id("btn-next");
    private final By confirmButton = By.cssSelector("button.btn.btn-primary.rounded-3[type='submit']");

    public PassengerForm passengers() {
        return passengerForm;
    }

    public void setContactInfo(String lastName, String firstName, String phone, String email) {
        driver.findElement(contactLastName).sendKeys(lastName);
        driver.findElement(contactFirstName).sendKeys(firstName);
        driver.findElement(contactPhone).sendKeys(phone);
        driver.findElement(contactEmail).sendKeys(email);
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
