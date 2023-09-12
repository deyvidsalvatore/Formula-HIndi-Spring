package deyvid.silva.formula.mscars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCarsApplication.class, args);
    }

}
