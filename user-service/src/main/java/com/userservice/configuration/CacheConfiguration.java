package com.userservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class CacheConfiguration {

    @Value("${cache.user-cache}")
    private String useCache;

    @Value("${cache.time-to-live}")
    private int timeToLiveSeconds;

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();

        MapConfig mapConfig = new MapConfig(useCache);
        mapConfig.setTimeToLiveSeconds(timeToLiveSeconds);
        config.addMapConfig(mapConfig);
        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance(hazelcastConfig());
    }
}
