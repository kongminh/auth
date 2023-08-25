package com.esoft.auth.security;

//import com.tmg.cargolink.application.dto.mobile.LoginShipperCompanyDetails;
//import com.tmg.cargolink.application.dto.mobile.LoginUserDetails;
//import com.tmg.cargolink.config.ConstantProperties;
//import com.tmg.cargolink.domain.primary.entity.DtbUser;
//import com.tmg.cargolink.domain.primary.enums.ApproveStatus;
//import com.tmg.cargolink.domain.primary.enums.RoleType;
//import com.tmg.cargolink.domain.primary.enums.RoleTypeText;
//import com.tmg.cargolink.domain.primary.enums.UserType;
//import com.tmg.cargolink.domain.primary.repository.AdminRepository;
//import com.tmg.cargolink.domain.primary.repository.UserRepository;
//import com.tmg.cargolink.domain.primary.repository.specification.AdminUserSpecification;
//import com.tmg.cargolink.util.Helper;
import com.esoft.auth.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.esoft.auth.repository.UserRepository;
import com.esoft.auth.security.model.LoginUserDetails;
import com.esoft.auth.model.UserDTO.UserRole;

@Service
public class EsoftUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
//    @Autowired private ConstantProperties constantProperties;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user =
                userRepository
                        .findByUsername(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return new LoginUserDetails(user, new HashSet<>());
    }

    public LoginUserDetails loadUserById(String loginId, String userRole)
            throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(loginId);
        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("Account not found.");
        }

        if (!userOpt.isEmpty()) {
            UserEntity user = userOpt.get();
            if (user.getRole() == UserRole.USER.name()) {
                return new LoginUserDetails(user, new HashSet<>());
            }
        }
        return null;
    }

    /**
     * @param loginId String
     * @param userRole String
     * @return Collection-GrantedAuthority
     * @throws UsernameNotFoundException throws exeption
     */
    public Collection<GrantedAuthority> getAuthorities(String loginId, String userRole)
            throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(loginId);

        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("Account not found.");
        }

        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (!userOpt.isEmpty()) {
            UserEntity user = userOpt.get();
            if (user.getRole() == UserRole.USER.name()) {
                authorities.add(new SimpleGrantedAuthority(user.getRole()));
                authorities.add(new SimpleGrantedAuthority(user.getPermissions()));
                return authorities;
            }
        }
        return authorities;
    }

//    public boolean checkTokenPortal(String token) throws UsernameNotFoundException {
//        try {
//            Claims claims =
//                    Jwts.parser()
//                            .setSigningKey(
//                                    constantProperties
//                                            .getString("secretKeyPortal")
//                                            .getBytes(Charset.forName("UTF-8")))
//                            .parseClaimsJws(token)
//                            .getBody();
//            return true;
//        } catch (RuntimeException e) {
//            return false;
//        }
//    }
}
