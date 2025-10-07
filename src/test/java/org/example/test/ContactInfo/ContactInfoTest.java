package org.example.test.ContactInfo;

import org.example.constant.Constant;
import org.example.enums.PassengerType;
import org.example.pages.ContactInfo.ContactInfoPage;
import org.example.pages.ContactInfo.PassengerForm;
import org.example.runner.FlightList.FlightListRunTestCase;
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
    public void testBookingOneWayMultiplePassenger() throws Exception {
        FlightListRunTestCase flightListRunTestCase = new FlightListRunTestCase();
        String flightListMessage = flightListRunTestCase.flightListOneWay(driver);
        List<String[]> testDataPassengerInfo = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_PASSENGERINFO_SHEET);
        List<String[]> testDataContactInfo = ExcelUtils.readExcel(Constant.EXCEL_FILE_PATH, Constant.EXCEL_CONTACTINFO_SHEET);

        if(flightListMessage.equals("Pass")){
            ContactInfoPage contactInfoPage = new ContactInfoPage(driver);
            PassengerForm form = contactInfoPage.passengers();

            //Adult Info
            for(int i=1; i<=2; i++){
                String[] row = testDataPassengerInfo.get(i);
                form.setLastName(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[6]);
                form.setFirstName(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[7]);
                form.selectTitle(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[8]);
                form.selectBirthday(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[9]), Integer.parseInt(row[10]), Integer.parseInt(row[11]));
                Thread.sleep(1000);
                form.selectNationality(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[12]);
                form.setPassport(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[13]);
                form.selectNation(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[14]);
                form.selectIssueDate(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[15]), Integer.parseInt(row[16]), Integer.parseInt(row[17]));
                Thread.sleep(1000);
                form.selectExpiryDate(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[18]), Integer.parseInt(row[19]), Integer.parseInt(row[20]));
                Thread.sleep(1000);
            }

            //Children Info
            for(int i=4; i<=5; i++){
                String[] row = testDataPassengerInfo.get(i);
                form.setLastName(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[6]);
                form.setFirstName(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[7]);
                form.selectTitle(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[8]);
                form.selectBirthday(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[9]), Integer.parseInt(row[10]), Integer.parseInt(row[11]));
                Thread.sleep(1000);
                form.selectNationality(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[12]);
                form.setPassport(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[13]);
                form.selectNation(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[14]);
                form.selectIssueDate(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[15]), Integer.parseInt(row[16]), Integer.parseInt(row[17]));
                Thread.sleep(1000);
                form.selectExpiryDate(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[18]), Integer.parseInt(row[19]), Integer.parseInt(row[20]));
                Thread.sleep(1000);
            }

            //Infant
            for(int i=7; i<=8; i++){
                String[] row = testDataPassengerInfo.get(i);
                form.setLastName(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[6]);
                form.setFirstName(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[7]);
                form.selectTitle(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[8]);
                form.selectBirthday(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[9]), Integer.parseInt(row[10]), Integer.parseInt(row[11]));
                Thread.sleep(1000);
                form.selectNationality(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[12]);
                form.setPassport(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[13]);
                form.selectNation(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]), row[14]);
                form.selectIssueDate(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[15]), Integer.parseInt(row[16]), Integer.parseInt(row[17]));
                Thread.sleep(1000);
                form.selectExpiryDate(PassengerType.valueOf(row[4].toUpperCase()), Integer.parseInt(row[5]),
                        Integer.parseInt(row[18]), Integer.parseInt(row[19]), Integer.parseInt(row[20]));
                Thread.sleep(1000);
            }

            String[] rowContact = testDataContactInfo.get(0);
            contactInfoPage.setContactInfo(rowContact[4], rowContact[5], rowContact[6], rowContact[7]);
            contactInfoPage.clickBooking();
            Thread.sleep(2000);
            contactInfoPage.clickConfirmButton();
            Thread.sleep(2000);
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
