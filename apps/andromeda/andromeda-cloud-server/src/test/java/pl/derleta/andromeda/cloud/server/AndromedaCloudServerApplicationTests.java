package pl.derleta.andromeda.cloud.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootTest
class AndromedaCloudServerApplicationTests {

    @InjectMocks
    private AndromedaCloudServerApplication andromedaCloudServerApplication;

    @Test
    void contextLoads() {
    }

    @Test
    public void main() {
        AndromedaCloudServerApplication.main(new String[]{});
    }
}
