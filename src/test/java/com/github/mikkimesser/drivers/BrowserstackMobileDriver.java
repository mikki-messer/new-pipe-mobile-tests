package com.github.mikkimesser.drivers;

import com.codeborne.selenide.WebDriverProvider;
import com.github.mikkimesser.configuration.DeviceHostConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackMobileDriver implements WebDriverProvider {

    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);
        // Set your access credentials
        DeviceHostConfig deviceHostConfig = ConfigFactory.create(DeviceHostConfig.class);
        mutableCapabilities.setCapability("browserstack.user", deviceHostConfig.login());
        mutableCapabilities.setCapability("browserstack.key", deviceHostConfig.password());

        // Set URL of the application under test
        mutableCapabilities.setCapability("app", deviceHostConfig.applId());

        // Specify device and os_version for testing
        mutableCapabilities.setCapability("device", deviceHostConfig.device());
        mutableCapabilities.setCapability("os_version", deviceHostConfig.osVersion());

        // Set other BrowserStack capabilities
        mutableCapabilities.setCapability("project", deviceHostConfig.project());
        mutableCapabilities.setCapability("build", deviceHostConfig.build());
        mutableCapabilities.setCapability("name", deviceHostConfig.name());
        return new RemoteWebDriver(getBrowserstackUrl(), mutableCapabilities);
    }

    public static URL getBrowserstackUrl() {
        try {
            DeviceHostConfig deviceHostConfig = ConfigFactory.create(DeviceHostConfig.class);
            return new URL(deviceHostConfig.browserstackURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
