package com.github.mikkimesser.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.mikkimesser.configuration.TestDataConfig;
import com.github.mikkimesser.drivers.BrowserstackMobileDriver;
import com.github.mikkimesser.drivers.LocalMobileDriver;
import com.github.mikkimesser.helpers.Attach;

import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static com.github.mikkimesser.helpers.Attach.sessionId;
import static io.qameta.allure.Allure.step;

public class TestBase {

    static String deviceHost = System.getProperty("deviceHost", "emulation");
    static TestDataConfig testDataConfig = ConfigFactory.create(TestDataConfig.class);

    @BeforeAll
    public static void setup() {
        System.out.println(deviceHost);
        switch (deviceHost) {
            case "browserstack":
                Configuration.browser = BrowserstackMobileDriver.class.getName();
                break;
            default:
                Configuration.browser = LocalMobileDriver.class.getName();
        }

        Configuration.browserSize = null;
    }

    @BeforeEach
    public void startDriver() {
        addListener("AllureSelenide", new AllureSelenide());

        open();
    }

    @AfterEach
    public void afterEach() {
        String sessionId = sessionId();

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        step("Close driver", Selenide::closeWebDriver);

        if (deviceHost.equals("browserstack")) {
            Attach.video(sessionId);
        }
    }
}
