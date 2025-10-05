package org.example.test.ContactInfo;

import org.example.pages.ContactInfo.ContactInfoPage;
import org.example.runner.FlightList.FlightListRunTestCase;
import org.example.utils.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

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
            form.selectBirthday(2011, 9, 9);
            form.selectAdultNationality0("AF");
            form.setAdultPassport0("B12345678");
            form.selectAdultPassportNation0("BS");
            form.selectIssueDate(2021, 11, 10);
            form.selectExpiryDate(2032, 2, 5);

            form.setContactLastName("Nguyen");
            Thread.sleep(1000);
            form.setContactFirstName("Van A");
            Thread.sleep(1000);
            form.selectPhoneCountryCode("Vietnam");
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
