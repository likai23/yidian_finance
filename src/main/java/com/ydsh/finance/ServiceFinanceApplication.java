package com.ydsh.finance;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class})
@EnableEurekaClient
@EnableApolloConfig
//@EnableDiscoveryClient
public class ServiceFinanceApplication {

    public static void main(String[] args){
        Config config = ConfigService.getAppConfig();
        final String LOG_PATH="logging.path";
        final String PROFILES_ACTIVE="spring.profiles.active";
        final String LOG_NAME="logging.name";
        final String APPLICATION_NAME="spring.application.name";
        System.setProperty(PROFILES_ACTIVE,config.getProperty(PROFILES_ACTIVE, "prod"));
        System.setProperty(LOG_PATH,config.getProperty(LOG_PATH, "/logs"));
        System.setProperty(LOG_NAME,config.getProperty(APPLICATION_NAME, "UNDEFINED"));
        SpringApplication.run(ServiceFinanceApplication.class, args);
    }
}
