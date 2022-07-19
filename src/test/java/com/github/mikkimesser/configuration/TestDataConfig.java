package com.github.mikkimesser.configuration;

import org.aeonbits.owner.Config;
import org.checkerframework.checker.units.qual.K;

@Config.Sources("classpath:configuration/testData.properties")
public interface TestDataConfig extends Config {
    @Key("search_phrase")
    String searchPhrase();
}
