package com.github.mikkimesser.tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Owner("mikki_messer")
public class SearchTests extends TestBase {

    @Test
    @Feature("Search")
    @Story("Search video from the main page")
    @DisplayName("Display search results dropdown test")
    void displaySearchResultsTest() {
        step("Wait for the first page to load", () ->
                $(AppiumBy.id("org.schabi.newpipe:id/itemVideoTitleView"))
                        .shouldBe(Condition.visible, Duration.ofSeconds(30))
        );

        step("Click the search icon", () ->
                $(AppiumBy.id("org.schabi.newpipe:id/action_search")).click()
        );

        step("Type in the search phrase", () -> {
            $(AppiumBy.id("org.schabi.newpipe:id/toolbar_search_edit_text")).click();
            $(AppiumBy.id("org.schabi.newpipe:id/toolbar_search_edit_text"))
                    .sendKeys(testDataConfig.searchPhrase());
        });

        step("Verify the search results", () -> {
            $$(AppiumBy.id("org.schabi.newpipe:id/item_suggestion_query"))
                    .shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(30));
        });
    }

    @Test
    @Feature("Search")
    @Story("Search video from the main page")
    @DisplayName("Open the search results page test")
    void clickOnSearchResultsTest() {
        step("Wait for the first page to load", () ->
                $(AppiumBy.id("org.schabi.newpipe:id/itemVideoTitleView"))
                        .shouldBe(Condition.visible, Duration.ofSeconds(30))
        );
        step("Click the search icon", () ->
                $(AppiumBy.id("org.schabi.newpipe:id/action_search")).click()
        );

        step("Type in the search phrase", () -> {
            $(AppiumBy.id("org.schabi.newpipe:id/toolbar_search_edit_text")).click();
            $(AppiumBy.id("org.schabi.newpipe:id/toolbar_search_edit_text"))
                    .sendKeys(testDataConfig.searchPhrase());
        });

        step("Verify the search results displayed in the dropdown", () -> {
            $$(AppiumBy.id("org.schabi.newpipe:id/item_suggestion_query"))
                    .shouldHave(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(30));
        });

        step("Click on the first search result from the dropdown", () ->
                $(AppiumBy.id("org.schabi.newpipe:id/item_suggestion_query")).click()
        );
        step("Verify the search results page displayed", () -> {
            $(AppiumBy.id("org.schabi.newpipe:id/itemVideoTitleView"))
                    .shouldHave(Condition.text(testDataConfig.searchPhrase()), Duration.ofSeconds(30));
        });
    }
}
