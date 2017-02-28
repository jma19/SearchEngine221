import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.uci.*")
public class ServerApplication {
    public static void main(String[] args) {
        //http://ketqi.blog.51cto.com/1130608/325255/
        SpringApplication.run(ServerApplication.class, args);
    }

}
