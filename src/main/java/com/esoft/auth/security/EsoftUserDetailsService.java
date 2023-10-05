package com.esoft.auth.security;

import com.esoft.auth.entity.user.UserEntity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.esoft.auth.repository.UserRepository;
import com.esoft.auth.security.model.LoginUserDetails;

@Service
public class EsoftUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public EsoftUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return new LoginUserDetails(user, new HashSet<>());
    }

    public Collection<GrantedAuthority> getAuthorities(String username)
            throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Account not found.");
        }

        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        UserEntity user = userOpt.get();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        authorities.add(new SimpleGrantedAuthority(user.getPermissions()));
        return authorities;
    }
}
