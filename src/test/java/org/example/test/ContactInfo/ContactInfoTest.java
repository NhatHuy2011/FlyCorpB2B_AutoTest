package org.example.test.ContactInfo;

import org.example.constant.Constant;
import org.example.pages.ContactInfo.ContactInfoPage;
import org.example.pages.FlightList.FlightListPage;
import org.example.runner.FlightList.FlightListRunTestCase;
import org.example.runner.SearchFlight.SearchFlightRunTestcase;
import org.example.utils.ConfigReader;
import org.example.utils.ExcelUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class ContactInfoTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(ConfigReader.getUrl());

        Cookie agencySession = new Cookie(ConfigReader.getCookiesAgencySessionName(), ConfigReader.getCookiesAgencySessionValue());
        Cookie XSRFToken = new Cookie(ConfigReader.getCookiesXSRFTokenName(), ConfigReader.getCookiesXSRFTokenValue());

        driver.manage().addCookie(agencySession);
        driver.manage().addCookie(XSRFToken);

        driver.navigate().refresh();
    }

    @Test
    public void testBookingOneWay() throws Exception {
        FlightListRunTestCase flightListRunTestCase = new FlightListRunTestCase();
        String flightListMessage = flightListRunTestCase.flightListOneWay(driver);
        if(flightListMessage.equals("Pass")){
            ContactInfoPage form = new ContactInfoPage(driver);
            form.setAdultLastName0("Nguyen");
            form.setAdultFirstName0("Van A");
            form.selectAdultTitle0("1"); // MR
            form.selectAdultNationality0("AF");
            form.setAdultPassport0("B12345678");
            form.selectAdultPassportNation0("BS");

            form.setContactLastName("Nguyen");
            Thread.sleep(1000);
            form.setContactFirstName("Van A");
            Thread.sleep(1000);
            form.setContactPhone("363437324");
            Thread.sleep(1000);
            form.clickBooking();
            form.clickConfirmButton();
            Thread.sleep(5000);
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
