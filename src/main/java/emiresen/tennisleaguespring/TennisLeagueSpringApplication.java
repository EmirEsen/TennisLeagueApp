package emiresen.tennisleaguespring;

import emiresen.tennisleaguespring.config.s3.S3Buckets;
import emiresen.tennisleaguespring.service.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableMongoAuditing
public class TennisLeagueSpringApplication {

    public static void main(String[] args) {SpringApplication.run(TennisLeagueSpringApplication.class, args);}

//    @Bean
//    CommandLineRunner runner(S3Service s3Service, S3Buckets s3Buckets) {
//        return args -> {
//            s3Service.putObject(s3Buckets.getCustomer(), "foo", "helloaws".getBytes());
//            byte[] obj = s3Service.getObject("tennisleaguebucket", "foo");
//            System.out.println(obj);
//            System.out.println(new String(obj));
//        };
//    };


}
