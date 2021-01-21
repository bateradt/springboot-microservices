package com.monitora.aulamicroservices.auth.security.user;

import com.monitora.aulamicroservices.core.model.ApplicationUser;
import com.monitora.aulamicroservices.core.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    //cria uma instacia do repositório
    private final ApplicationUserRepository applicationUserRepository;

    //função para buscar o usuário pelo username
    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Searching in the DB the user by username '{}'", username);
        //busca o usuário no banco de dados
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        log.info("User found {}", applicationUser);
        //se for null irá retornar uma exceção para o usuário
        if (applicationUser == null) {
            throw new UsernameNotFoundException(String.format("Application user '%s' not found", username));
        }
        //irá retornar um objeto de usuário
        return new CustomUserDetails(applicationUser);
    }

    private static final class CustomUserDetails extends ApplicationUser implements UserDetails {

        //construtor da classe ApplicationUser, onde irá copiar os dados passados para o objeto
        CustomUserDetails(@NotNull ApplicationUser applicationUser) {
            super(applicationUser);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            //essa função irá retornar uma lista de ROLE, caso esteja separador por virgula
            return commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
