import com.uci.utils.SysPathUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.uci.*")
public class ServerApplication {

    public static void main(String[] args) {
        //http://ketqi.blog.51cto.com/1130608/325255/
        SpringApplication.run(ServerApplication.class, args);
    }

}
