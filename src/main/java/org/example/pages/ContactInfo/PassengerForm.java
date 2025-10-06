package org.example.pages.ContactInfo;

import org.example.enums.PassengerType;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class PassengerForm {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ContactInfoCommon contactInfoCommon;

    public PassengerForm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.contactInfoCommon = new ContactInfoCommon(driver);
    }

    private By byId(String format, PassengerType type, int index) {
        return By.id(String.format(format, type.getPrefix(), index));
    }

    //Lastname
    public void setLastName(PassengerType type, int index, String value) {
        By locator = byId("%s_surname_%d", type, index);
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        input.clear();
        input.sendKeys(value);
    }

    //Firstname
    public void setFirstName(PassengerType type, int index, String value) {
        By locator = byId("%s_givenName_%d", type, index);
        WebElement input = driver.findElement(locator);
        input.clear();
        input.sendKeys(value);
    }

    //Title
    public void selectTitle(PassengerType type, int index, String value) {
        By locator = byId("%s_title_%d", type, index);
        new Select(driver.findElement(locator)).selectByValue(value);
    }

    //Dob
    public void selectBirthday(PassengerType type, int index, int year, int month, int day) {
        By locator = byId("%s_birthday_%d", type, index);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        contactInfoCommon.pickDate(year, month, day);
    }

    //Nationality
    public void selectNationality(PassengerType type, int index, String value) {
        By locator = byId("%s_nationality_%d", type, index);
        new Select(driver.findElement(locator)).selectByValue(value);
    }

    //Passport
    public void setPassport(PassengerType type, int index, String value) {
        By locator = byId("%s_passport_%d", type, index);
        WebElement input = driver.findElement(locator);
        input.clear();
        input.sendKeys(value);
    }

    //Nationality
    public void selectNation(PassengerType type, int index, String value) {
        By locator = byId("%s_conOfIssue_%d", type, index);
        new Select(driver.findElement(locator)).selectByValue(value);
    }

    //IssueDate
    public void selectIssueDate(PassengerType type, int index, int year, int month, int day) {
        By locator = byId("%s_issueDate_%d", type, index);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        contactInfoCommon.pickDate(year, month, day);
    }

    //ExpiredDate
    public void selectExpiryDate(PassengerType type, int index, int year, int month, int day) {
        By locator = byId("%s_expiredDate_%d", type, index);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        contactInfoCommon.pickDate(year, month, day);
    }
}
