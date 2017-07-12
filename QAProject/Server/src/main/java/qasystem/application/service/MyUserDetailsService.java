package qasystem.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import qasystem.persistence.entities.Role;
import qasystem.persistence.entities.User;
import qasystem.persistence.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {
    protected final UserRepository userRepository;


    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException("Didn't find user with id " + s);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Set<GrantedAuthority> getAuthorities(User user){
        if(user == null){
            return new HashSet<>();
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}
