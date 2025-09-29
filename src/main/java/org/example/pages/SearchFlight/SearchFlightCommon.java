package org.example.pages.SearchFlight;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SearchFlightCommon {
    private final WebDriver driver;

    private final By monthLabel = By.cssSelector("th.month");
    private final By nextButton = By.cssSelector("th.next");
    private final By prevButton = By.cssSelector("th.prev");
    private final By availableDates = By.cssSelector("td.available");

    public SearchFlightCommon(WebDriver driver) {
        this.driver = driver;
    }

    protected String selectDate(By inputLocator, int day, Month month, int year) {
        driver.findElement(inputLocator).click();

        WebElement calendar = waitForCalendar();

        while (true) {
            String monthYearText = calendar.findElement(monthLabel).getText().trim();
            System.out.println("Calendar Text: " + monthYearText);

            String[] parts = monthYearText.split(" ");
            if (parts.length < 2) return "Không cho phép chọn";

            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
            Month currentMonth = Month.from(monthFormatter.parse(parts[0]));
            int currentYear = Integer.parseInt(parts[1]);

            if (currentYear == year && currentMonth == month) {
                break;
            } else if (currentYear < year || (currentYear == year && currentMonth.getValue() < month.getValue())) {
                calendar.findElement(nextButton).click();
            } else {
                calendar.findElement(prevButton).click();
            }
        }

        for (WebElement date : calendar.findElements(availableDates)) {
            if (date.getText().equals(String.valueOf(day))) {
                date.click();
                return "";
            }
        }
        return "Không cho phép chọn";
    }

    private WebElement waitForCalendar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(d -> {
            for (WebElement el : driver.findElements(By.cssSelector("div.daterangepicker.show-calendar"))) {
                if (el.isDisplayed()) {
                    String text = el.findElement(monthLabel).getText().trim();
                    if (!text.isEmpty()) {
                        return el;
                    }
                }
            }
            return null;
        });
    }
}
