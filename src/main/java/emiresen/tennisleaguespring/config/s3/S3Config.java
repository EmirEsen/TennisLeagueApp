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

    @Value("${aws.credentials.access-key-id}")
    private String awsAccessKeyId;

    @Value("${aws.credentials.secret-access-key}")
    private String awsSecretAccessKey;

    @Value("${aws.region}")
    private String region;


    @Bean
    public S3Client s3Client(){
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey)
        );

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

}
