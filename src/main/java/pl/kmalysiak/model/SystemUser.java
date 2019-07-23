package pl.kmalysiak.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SystemUser {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private Date lastPasswordResetDate;
}
