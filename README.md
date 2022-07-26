# Automated tests for the New Pipe Android application

## <a href = "https://newpipe.net/" target ="_blank">newpipe.net</a>

## :tv: Contents

- <a href="#tv-coverage">Coverage</a>
- <a href="#tv-technology-stack">Technology stack</a>
- <a href="#tv-prepare-test-data">Prepare test data</a>
- <a href="#tv-launch">Launch</a>
    - <a href="#how-to-launch-on-the-local-machine-with-tests-running-on-the-android-studio-emulator">How to launch on the local machine with tests running on the Android Studio Emulator</a>
        - <a href="#prerequisites">Prerequisites</a>
        - <a href="#prepare-the-configuration-file">Prepare the configuration file</a>
        - <a href="#required-parameters">Required parameters</a>
        - <a href="#emulationproperties-file-example">Emulation.properties file example</a>
        - <a href="#command">Command</a>
    - <a href="#how-to-launch-on-the-local-machine-with-tests-running-on-browserstackcom">How to launch on the local machine with tests running on Browserstack.com</a>
        - <a href="#prerequisites-1">Prerequisites</a>
        - <a href="#prepare-the-configuration-file-1">Prepare the configuration file</a>
        - <a href="#required-parameters-1">Required parameters</a>
        - <a href="#browserstackproperties-file-example">Browserstack.properties file example</a>
        - <a href="#command-1">Command</a>
    - <a href="#how-to-launch-remotely-on-jenkins">How to launch remotely on Jenkins</a>
        - <a href="#command-2">Command</a>
        - <a href="#jenkins-build-example">Jenkins build example</a>
- <a href="#tv-allure-reports-integration">Allure reports integration</a>
    - <a href="#overview">Overview</a>
    - <a href="#test-suites">Test suites</a>
    - <a href="#behaviors">Behaviors</a>
- <a href="#tv-allure-testops-integration">Allure TestOps integration</a>
    - <a href="#dashboards">Dashboards</a>
    - <a href="#test-cases">Test cases</a>
    - <a href="#launches">Launches</a>
- <a href="#tv-telegram-notifications">Telegram Notifications</a>
- <a href="#tv-browserstackcom-launch-example">Browserstack.com launch example</a>

## :tv: Coverage

- Check that on typing the search phrase into the Search input the dropdown with search suggestions appears.
- Check that on clicking on the search suggestion the search result page opens.
- Check that on clicking on the Main menu button the Main menu appears.
- Check that on clicking on the Main menu header the menu tab changes.
- Check that on clicking on the Settings menu item in the Main menu the Settings section opens.

## :tv: Technology stack

<p align="center">
<img width="6%" title="IntelliJ IDEA" src="images/logos/Intelij_IDEA.svg">
<img width="6%" title="Java" src="images/logos/Java.svg">
<img width="6%" title="Selenide" src="images/logos/Selenide.svg">
<img width="6%" title="Appium" src="images/logos/Appium.svg">
<img width="6%" title="Allure Report" src="images/logos/Allure_Report.svg">
<img width="6%" title="Gradle" src="images/logos/Gradle.svg">
<img width="6%" title="JUnit5" src="images/logos/JUnit5.svg">
<img width="6%" title="GitHub" src="images/logos/GitHub.svg">
<img width="6%" title="Jenkins" src="images/logos/Jenkins.svg">
<img width="6%" title="Telegram" src="images/logos/Telegram.svg">
<img width="6%" title="Allure TestOps" src="images/logos/Allure_TO.svg">
</p>

## :tv: Prepare test data

The test data is passed via the `src/test/resources/configuration/testData.properties` file

Required test data:
- `search_phrase` - String. Required for the search tests. The phrase to search videos with.

## :tv: Launch

### How to launch on the local machine with tests running on the Android Studio Emulator

#### Prerequisites

Make sure to install Android Studio and Appium Server. Add the `.apk` file with the current version of the New Pipe app to the `src/test/resources/apps/` folder. 
At the moment of writing it is v 0.23.1.

#### Prepare the configuration file

Create the `emulation.properties` file in the `src/test/resources/configuration/` folder. 

#### Required parameters

`device` - The model of the emulated device.
`platform_name` - The platform name, Android.
`os_version` - The version of the device OS.
`app_package` - The application package. The value at the moment of writing is below.
`app_activity` - The main application package. The value at the moment of writing is below.
`app_url` -  The URL to download the current version of `.apk` file if it is missing in the `src/test/resources/configuration/` folder.
`app_path` - The path to the `.apk` file in the repository.
`appium_server_url` - The URL of the Appium server.

#### Emulation.properties file example

```
device=Pixel 4 API 30
platform_name=Android
os_version=11.0
app_package=org.schabi.newpipe
app_activity=org.schabi.newpipe.MainActivity
app_url=https://github.com/TeamNewPipe/NewPipe/releases/tag/v0.23.1
app_path=src/test/resources/apps/NewPipe_v0.23.1.apk
appium_server_url=http://localhost:4723/wd/hub
```

#### Command:

```
gradle clean test -"DdeviceHost=emulation"  
```

### How to launch on the local machine with tests running on Browserstack.com

#### Prerequisites

Make sure to register at <a href="https://browserstack.com" target="_blank">browserstack.com</a>, upload your `.apk` file to your dashboard, and get your credentials and the Browserstack link for the app.

#### Prepare the configuration file

Create the `browserstack.properties` file in the `src/test/resources/configuration/` folder.

#### Required parameters

`login` - The login to the Browserstack account.
`password` - The password to the Browserstack account.
`applId` - The link to the upload to the Browserstack apk.
`device` - The model of the emulated device.
`os_version` - The version of the device OS.
`url` - The URL of the Browserstack remote web driver.
`session_json_url` - The template of the URL of the session description on the Browserstack. Required to fetch the video URL.
`name` - Name of the session on the Browserstack.
`build` - Name of the build on the Browserstack.
`project` - Name of the project on Browserstack.

#### Browserstack.properties file example

```
login=myBrowserStackLogin
password=myBrowserStackPassword
applId=bs://myAwesomeBrowserstackLink
device=Google Pixel 3
os_version=9.0
url=http://hub.browserstack.com/wd/hub
session_json_url=https://api.browserstack.com/app-automate/sessions/%s.json
name=New Pipe v.0.23.1 appium tests
build=browserstack-build-2
project=new-pipe-mobile-tests
```

#### Command:

```
gradle clean test -"DdeviceHost=browserstack" 
```


### How to launch remotely on Jenkins

> Create the `testData.properties` file in the `src/test/resources/configuration/` folder following instructions from <a href="#tv-prepare-test-data">here</a>.
> Create the `browserstack.properties` file in the `src/test/resources/configuration/` folder following instructions from <a href="#prepare-the-configuration-file-1">here</a>.

#### Command:

```
gradle clean test -"DdeviceHost=browserstack" 
```

#### Jenkins build example

<h4><a target="_blank" href="https://jenkins.autotests.cloud/job/C12-Mike-B-thesis-mobile">Jenkins build</a></h4>

<p align="center">
<img title="Jenkins Dashboard" src="images/screenshots/jenkins-new-pipe-build-main-page.png">
</p>

## :tv: Allure reports integration

### Overview

<p align="center">
<img title="Allure reports Overview tab screenshot" src="images/screenshots/allure-reports-new-pipe-main-page.png">
</p>

### Test Suites

<p align="center">
<img title="Allure reports Test suites tab screenshot" src="images/screenshots/allure-reports-new-pipe-test-cases-page.png">
</p>

### Behaviors

<p align="center">
<img title="Allure reports Behaviors tab screenshot" src="images/screenshots/allure-reports-new-pipe-behaviors-page.png">
</p>

## :tv: Allure TestOps integration

The Jenkins job automatically sends the results of the launch to the Allure TestOps.

### Dashboards

<p align="center">
<img title="Allure TestOps Dashboard screenshot" src="images/screenshots/allure-testops-dashboards.png">
</p>

### Test cases

<p align="center">
<img title="Allure TestOps Test Cases screenshot" src="images/screenshots/allure-testops-test-cases.png">
</p>

### Launches

<p align="center">
<img title="Allure TestOps Launches screenshot" src="images/screenshots/allure-testops-launches.png">
</p>

## :tv: Telegram Notifications

> <a href="https://github.com/qa-guru/allure-notifications">qa-guru/allure-notifications</a> is used

<p align="center">
<img title="Telegram notification screenshot" src="images/screenshots/telegram-new-pipe-tests-notification.png">
</p>

## :tv: Browserstack.com launch example

There is a video for each test demonstrating the flow.

<p align="center">
<img title="Browserstack Video" src="images/gifs/new-pipe-browserstack-video.gif">
</p>

