package pl.kmalysiak.service.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pl.kmalysiak.Utils.UserAuthenticationException;
import pl.kmalysiak.service.security.JwtService.JwtRequest;

@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public void authenticate(JwtRequest request) throws AuthenticationException {
        String username = request.getUsername();
        String password = request.getPassword();

        if (StringUtils.isAnyBlank(username, password))
            throw new UserAuthenticationException("No username or password given");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new UserAuthenticationException("No username or password given");
        }
    }

}
