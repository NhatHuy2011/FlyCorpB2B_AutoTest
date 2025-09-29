package org.example.pages.ContactInfo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Month;
import java.util.List;

public class ContactInfoCommon {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By calendarContainer = By.cssSelector("div.daterangepicker.show-calendar");
    private final By leftCalendar      = By.cssSelector("div.drp-calendar.left.single");
    private final By yearSelect        = By.cssSelector(".yearselect");
    private final By monthSelect       = By.cssSelector(".monthselect");
    private final By dayCells          = By.cssSelector("td.available:not(.off)");

    public ContactInfoCommon(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement waitCalendar() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(calendarContainer));
    }

    private WebElement getLeftCalendar() {
        WebElement container = waitCalendar();
        return container.findElement(leftCalendar);
    }

    public void selectDate(By inputLocator, int day, Month month, int year) {
        // Click để mở calendar - đảm bảo calendar hiển thị cho input cụ thể
        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));

        // Scroll vào view và click
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateInput);
        dateInput.click();

        // Đợi calendar hiển thị
        WebElement calendar = waitCalendar();
        WebElement leftCalendarEl = calendar.findElement(leftCalendar);

        // Chọn năm
        selectYear(leftCalendarEl, year);

        // Chọn tháng (Month enum: 1=Jan → value="0")
        selectMonth(leftCalendarEl, month.getValue() - 1);

        // Chọn ngày
        selectDay(leftCalendarEl, day);

        // Đợi calendar đóng
        wait.until(ExpectedConditions.invisibilityOfElementLocated(calendarContainer));
    }

    private void selectYear(WebElement calendar, int year) {
        WebElement yearDropdownEl = calendar.findElement(yearSelect);
        Select yearSelect = new Select(yearDropdownEl);

        // Kiểm tra năm hiện tại đã được chọn chưa
        String currentYear = yearSelect.getFirstSelectedOption().getAttribute("value");
        if (!currentYear.equals(String.valueOf(year))) {
            yearSelect.selectByVisibleText(String.valueOf(year));
            // Đợi một chút để calendar refresh
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void selectMonth(WebElement calendar, int monthValue) {
        WebElement monthDropdownEl = calendar.findElement(monthSelect);
        Select monthSelect = new Select(monthDropdownEl);

        String targetMonthValue = String.valueOf(monthValue);
        String currentMonth = monthSelect.getFirstSelectedOption().getAttribute("value");

        if (!currentMonth.equals(targetMonthValue)) {
            monthSelect.selectByValue(targetMonthValue);
            // Đợi một chút để calendar refresh
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void selectDay(WebElement calendar, int day) {
        // Chọn ngày - chỉ chọn các ngày available (không phải off)
        List<WebElement> days = calendar.findElements(dayCells);
        boolean dateFound = false;

        for (WebElement d : days) {
            if (d.getText().trim().equals(String.valueOf(day)) && d.isEnabled() && d.isDisplayed()) {
                try {
                    // Đợi element có thể click được
                    wait.until(ExpectedConditions.elementToBeClickable(d));
                    d.click();
                    dateFound = true;
                    break;
                } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                    // Thử click bằng JavaScript nếu bị intercepted hoặc stale
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", d);
                        dateFound = true;
                        break;
                    } catch (Exception ex) {
                        // Continue to next element
                    }
                }
            }
        }

        if (!dateFound) {
            // Thử tìm lại elements nếu không tìm thấy
            List<WebElement> refreshedDays = calendar.findElements(dayCells);
            for (WebElement d : refreshedDays) {
                if (d.getText().trim().equals(String.valueOf(day)) && d.isEnabled() && d.isDisplayed()) {
                    try {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", d);
                        dateFound = true;
                        break;
                    } catch (Exception ex) {
                        // Continue to next element
                    }
                }
            }
        }

        if (!dateFound) {
            throw new RuntimeException("Không tìm thấy ngày " + day + " trong calendar hiện tại");
        }
    }
}