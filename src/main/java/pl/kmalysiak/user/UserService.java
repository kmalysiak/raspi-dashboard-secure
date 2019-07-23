package pl.kmalysiak.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kmalysiak.Utils.UserAuthenticationException;
import pl.kmalysiak.model.SystemUser;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private UserProvider userProvider;
    private UserConfig userConfig;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserProvider userProvider, PasswordEncoder passwordEncoder, UserConfig config) {
        this.userProvider = userProvider;
        this.userConfig = config;
        this.passwordEncoder = passwordEncoder;
        addDefaultUser();
    }

    private void addDefaultUser() {
        if (userConfig.getUserName() != null && userConfig.getPassword() != null)
            userProvider.addUser(
                    SystemUser.builder()
                            .username(userConfig.getUserName())
                            .password(passwordEncoder.encode(userConfig.getPassword())).build()
            );
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        SystemUser user = userProvider.getUserByName(username);

        if (user == null)
            throw new UserAuthenticationException("User not found");
        else
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
