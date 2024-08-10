package emiresen.tennisleaguespring.config.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Buckets {

    private String customer;
}
