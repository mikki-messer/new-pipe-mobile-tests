package com.github.mikkimesser.helpers;

import com.github.mikkimesser.configuration.DeviceHostConfig;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class Browserstack {

    public static String videoUrl(String sessionId) {
        DeviceHostConfig deviceHostConfig = ConfigFactory.create(DeviceHostConfig.class, System.getProperties());

        String url = format(deviceHostConfig.sessionJsonUrl(), sessionId);

        return given()
                .auth().basic(deviceHostConfig.login(), deviceHostConfig.password())
                .log().all()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .path("automation_session.video_url");
    }
}

