package com.mihajlo.betbull.playermarket.playerteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class PlayerTeamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerTeamServiceApplication.class, args);
    }
}
