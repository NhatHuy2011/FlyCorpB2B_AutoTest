package org.example.utils;

import org.example.constant.Constant;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(Constant.APPLICATION_PATH)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUrl(){
        return properties.getProperty("url");
    }

    public static String getUrlOffer(){
        return properties.getProperty("url_offer");
    }

    public static String getCookiesAgencySessionName(){
        return properties.getProperty("cookies.agency_session.name");
    }

    public static String getCookiesAgencySessionValue(){
        return properties.getProperty("cookies.agency_session.value");
    }

    public static String getCookiesXSRFTokenName(){
        return properties.getProperty("cookies.XSRFToken.name");
    }

    public static String getCookiesXSRFTokenValue(){
        return properties.getProperty("cookies.XSRFToken.value");
    }
}
