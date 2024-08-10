package emiresen.tennisleaguespring.config.s3;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class S3Config {
    // todo using .aws region fix that
    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public S3Client s3Client(){
        S3Client client = S3Client.builder()
                .region(Region.of(awsRegion))
//                .endpointOverride(URI.create("https://s3.us-west-2.amazonaws.com"))
//                .forcePathStyle(true)
                .build();
        return client;
    }



}
