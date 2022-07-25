package com.github.mikkimesser.drivers;

import com.codeborne.selenide.WebDriverProvider;
import com.github.mikkimesser.configuration.DeviceHostConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class LocalMobileDriver implements WebDriverProvider {


    public static URL getAppiumServerUrl() {
        try {
            DeviceHostConfig deviceHostConfig = ConfigFactory.create(DeviceHostConfig.class, System.getProperties());
            System.out.println(deviceHostConfig.appiumServerURL());
            return new URL(deviceHostConfig.appiumServerURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        File app = getApp();
        DeviceHostConfig deviceHostConfig = ConfigFactory.create(DeviceHostConfig.class, System.getProperties());
        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setPlatformName(deviceHostConfig.platformName());
        options.setDeviceName(deviceHostConfig.device());
        options.setPlatformVersion(deviceHostConfig.osVersion());
        options.setApp(app.getAbsolutePath());
        options.setAppPackage(deviceHostConfig.appPackage());
        options.setAppActivity(deviceHostConfig.appActivity());

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

    private File getApp() {
        DeviceHostConfig deviceHostConfig = ConfigFactory.create(DeviceHostConfig.class, System.getProperties());

        String appUrl = deviceHostConfig.appURL();
        String appPath = deviceHostConfig.appPath();

        File app = new File(appPath);
        if (!app.exists()) {
            try (InputStream in = new URL(appUrl).openStream()) {
                copyInputStreamToFile(in, app);
            } catch (IOException e) {
                throw new AssertionError("Failed to download application", e);
            }
        }
        return app;
    }

}
