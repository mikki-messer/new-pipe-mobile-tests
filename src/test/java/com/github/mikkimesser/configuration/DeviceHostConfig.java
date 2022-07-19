package com.github.mikkimesser.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "classpath:configuration/${deviceHost}.properties"})
public interface DeviceHostConfig extends Config {
    @Key("login")
    String login();

    @Key("password")
    String password();

    @Key("applId")
    String applId();

    @Key("device")
    String device();

    @Key("os_version")
    String osVersion();

    @Key("url")
    String browserstackURL();

    @Key("session_json_url")
    String sessionJsonUrl();

    @Key("platform_name")
    String platformName();

    @Key("app_package")
    String appPackage();

    @Key("app_activity")
    String appActivity();

    @Key("app_url")
    String appURL();

    @Key("app_path")
    String appPath();

    @Key("appium_server_url")
    String appiumServerURL();

    String name();

    String build();

    String project();
}
