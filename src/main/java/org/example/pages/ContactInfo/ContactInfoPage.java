package org.example.pages.ContactInfo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ContactInfoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ContactInfoCommon contactInfoCommon;

    public ContactInfoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.contactInfoCommon = new ContactInfoCommon(driver);
    }

    // ====== Locator phần thông tin hành khách ======
    private final By adultLastName0Input = By.id("adult_surname_0");
    private final By adultFirstName0Input = By.id("adult_givenName_0");
    private final By adultTitle0Select = By.id("adult_title_0");
    private final By adultBirthday0Input = By.id("adult_birthday_0");
    private final By adultNationality0 = By.id("adult_nationality_0");
    private final By adultPassport0 = By.id("adult_passport_0");
    private final By adultPassportNation0 = By.id("adult_conOfIssue_0");
    private final By adultIssueDate0 = By.id("adult_issueDate_0");
    private final By adultExpireDate0 = By.id("adult_expiredDate_0");

    // ====== Locator phần liên hệ ======
    private final By selectedCountryButton = By.cssSelector("button.iti__selected-country");
    private final By searchInput = By.cssSelector("input.iti__search-input");
    private final String countryItem = "//li[contains(@class,'iti__country') and .//span[contains(@class,'iti__country-name') and normalize-space(text())='%s']]";
    private final By contactLastName = By.id("contact_last_name");
    private final By contactFirstName = By.id("contact_first_name");
    private final By contactPhone = By.id("contact_phone");
    private final By contactEmail = By.id("contact_email");
    private final By bookingButton = By.id("btn-next");
    private final By confirmButton = By.cssSelector("button.btn.btn-primary.rounded-3[type='submit']");

    // ====== Nhập thông tin hành khách ======
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

    // ====== Mở datepicker ======
    public void openBirthdayPicker() {
        wait.until(ExpectedConditions.elementToBeClickable(adultBirthday0Input)).click();
    }

    public void openIssueDatePicker() {
        wait.until(ExpectedConditions.elementToBeClickable(adultIssueDate0)).click();
    }

    public void openExpiryDatePicker() {
        wait.until(ExpectedConditions.elementToBeClickable(adultExpireDate0)).click();
    }

    // ====== Chọn ngày cụ thể ======
    public void selectBirthday(int year, int month, int day) {
        openBirthdayPicker();
        contactInfoCommon.pickDate(year, month, day);
    }

    public void selectIssueDate(int year, int month, int day) {
        openIssueDatePicker();
        contactInfoCommon.pickDate(year, month, day);
    }

    public void selectExpiryDate(int year, int month, int day) {
        openExpiryDatePicker();
        contactInfoCommon.pickDate(year, month, day);
    }

    // ====== Chọn mã quốc gia điện thoại ======
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

    // ====== Nhập thông tin liên hệ ======
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

    public void setContactPhone(String value) {
        WebElement input = driver.findElement(contactPhone);
        input.clear();
        input.sendKeys(value);
    }

    // ====== Nút hành động ======
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
