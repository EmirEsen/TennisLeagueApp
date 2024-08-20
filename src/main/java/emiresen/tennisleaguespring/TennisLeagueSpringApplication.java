package emiresen.tennisleaguespring;

import emiresen.tennisleaguespring.config.s3.S3Buckets;
import emiresen.tennisleaguespring.service.JwtService;
import emiresen.tennisleaguespring.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableMongoAuditing
public class TennisLeagueSpringApplication {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TennisLeagueSpringApplication.class, args);

        // Example: Access a bean to debug it
        JwtService myBean = context.getBean(JwtService.class);
        System.out.println("Initialized MyBean: " + myBean);
        System.out.println(myBean.getSECRET_KEY());
        }


}
