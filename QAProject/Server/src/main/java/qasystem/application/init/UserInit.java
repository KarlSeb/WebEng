package qasystem.application.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import qasystem.persistence.entities.Role;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.RoleRepository;
import qasystem.persistence.repositories.UserRepository;

import java.util.HashSet;

@Component
public class UserInit implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInit(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... strings) throws Exception {
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");

        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRole("USER");

        roleRepository.save(userRole);

        User admin = new User("admin", passwordEncoder.encode("pass"));

        HashSet<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        admin.setRoles(adminRoles);

        userRepository.save(admin);

        User user = new User("user",passwordEncoder.encode("pass"));

        HashSet<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        user.setRoles(userRoles);

        userRepository.save(user);
    }
}

