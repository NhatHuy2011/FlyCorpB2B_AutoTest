package org.example.pages.ContactInfo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ContactInfoCommon {
    private final WebDriverWait wait;

    private final By monthSelect = By.cssSelector(".monthselect");
    private final By yearSelect = By.cssSelector(".yearselect");

    public ContactInfoCommon(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Lấy datepicker đang hiển thị (vì có thể có nhiều datepicker ẩn trong DOM)
     */
    private WebElement getActiveDatePicker() {
        // Đợi phần tử "daterangepicker.show-calendar" xuất hiện trong DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.daterangepicker.show-calendar")
        ));

        // Sau đó đợi phần tử thật sự hiển thị (display: block)
        return wait.until(driver -> {
            for (WebElement el : driver.findElements(
                    By.cssSelector("div.daterangepicker.show-calendar"))) {
                if (el.isDisplayed()) {
                    return el;
                }
            }
            return null;
        });
    }

    /** Chọn năm **/
    public void selectYear(int year) {
        WebElement picker = getActiveDatePicker();
        WebElement yearDropdown = picker.findElement(yearSelect);
        new Select(yearDropdown).selectByValue(String.valueOf(year));
    }

    /** Chọn tháng (0 = Jan, 11 = Dec) **/
    public void selectMonth(int monthIndex) {
        WebElement picker = getActiveDatePicker();
        WebElement monthDropdown = picker.findElement(monthSelect);
        new Select(monthDropdown).selectByValue(String.valueOf(monthIndex));
    }

    /** Chọn ngày **/
    public void selectDay(int day) {
        WebElement picker = getActiveDatePicker();
        String xpath = String.format(".//td[normalize-space(text())='%s' and contains(@class,'available')]", day);
        WebElement dayElement = picker.findElement(By.xpath(xpath));
        wait.until(ExpectedConditions.elementToBeClickable(dayElement)).click();
    }

    /** Chọn ngày tổng hợp **/
    public void pickDate(int year, int monthIndex, int day) {
        selectYear(year);
        selectMonth(monthIndex);
        selectDay(day);
    }
}