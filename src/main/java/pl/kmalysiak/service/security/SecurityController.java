package pl.kmalysiak.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import pl.kmalysiak.service.security.JwtService.JwtRequest;
import pl.kmalysiak.service.security.JwtService.JwtResponse;
import pl.kmalysiak.service.security.JwtService.JwtTokenService;
import pl.kmalysiak.user.UserService;

import java.util.Date;

@RestController
public class SecurityController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> getAuthenticationToken(@RequestBody JwtRequest authRequest)
            throws AuthenticationException, InternalServerError {

        securityService.authenticate(authRequest);
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenService.generateToken(userDetails);

        Date tokenExpiry = jwtTokenService.getExpirationDateFromToken(token);
        return ResponseEntity.ok(new JwtResponse(token, tokenExpiry));
    }

}
