package emiresen.tennisleaguespring.config.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class S3Config {

//    using .aws
    @Value("${aws.credentials.profile}")
    private String awsProfile;

    @Value("${aws.region}")
    private String region;
    private AwsCredentialsProvider credentialsProvider(String profileName) {
        return ProfileCredentialsProvider.builder()
                .profileName(profileName)
                .build();
    }

    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider(awsProfile))
                .build();
    }

}
