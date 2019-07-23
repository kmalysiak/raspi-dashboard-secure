package pl.kmalysiak.service.security.JwtService;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 5166255348328331015L;

    private String token;

    private Date expiresIn;

}
