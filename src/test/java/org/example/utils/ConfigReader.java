package org.example.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/application.properties")) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUrl(){
        return properties.getProperty("url");
    }

    public static String getExcelPath(){
        return properties.getProperty("excel.path");
    }

    public static String getSheetSearchFlight(){
        return properties.getProperty("excel.sheet.searchFlight");
    }

    public static String getCookiesAgencyName(){
        return properties.getProperty("cookies.agency.name");
    }

    public static String getCookiesAgencyValue(){
        return properties.getProperty("cookies.agency.value");
    }

    public static String getCookiesXSRFTokenName(){
        return properties.getProperty("cookies.XSRFToken.name");
    }

    public static String getCookiesXSRFTokenValue(){
        return properties.getProperty("cookies.XSRFToken.value");
    }
}
