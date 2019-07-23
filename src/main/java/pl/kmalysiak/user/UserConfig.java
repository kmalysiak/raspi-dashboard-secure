package pl.kmalysiak.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "default")
@Getter
@Setter
public class UserConfig {

    private String password;
    private String userName;


}
