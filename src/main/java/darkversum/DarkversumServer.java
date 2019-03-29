package darkversum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Slf4j
@EnableMongoAuditing
@EnableReactiveMongoRepositories
@EnableRedisRepositories
@SpringBootApplication(scanBasePackages = "darkversum")
public class DarkversumServer {

    public static void main(String[] args) {
        //log.info("Launching DarkversumServer...");
        setProfile();
        SpringApplication.run(DarkversumServer.class, args);
    }

    private static void setProfile() {
        String os = System.getProperty("os.name");
        String profile = os.contains("Windows") ? "Windows" : "Linux";
        log.info("Launching DarkversumServer on {} with profile {}", os, profile);
        System.setProperty("spring.profiles.active", profile);
    }
}
