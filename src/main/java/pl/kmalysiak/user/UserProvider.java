package pl.kmalysiak.user;

import org.springframework.stereotype.Service;
import pl.kmalysiak.model.SystemUser;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserProvider {

    private List<SystemUser> systemUserRepository;

    public UserProvider() {
        systemUserRepository = new ArrayList<>();
    }

    public Long addUser(SystemUser systemUser) {
        systemUser.setId(getLastId() + 1);
        systemUserRepository.add(systemUser);
        return systemUser.getId();
    }

    public SystemUser getUserById(Integer id) {
        return systemUserRepository.stream().filter(systemUser -> systemUser.getId().equals(id)).findFirst().orElse(null);
    }

    private Long getLastId() {
        return systemUserRepository.stream().mapToLong(SystemUser::getId).max().orElse(-1);
    }

    public SystemUser getUserByName(String name) {
        return systemUserRepository.stream().filter(systemUser -> systemUser.getUsername().equals(name)).findFirst().orElse(null);
    }

}
