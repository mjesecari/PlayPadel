package fer.progi.mjesecari.ppadel.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class KorisnikUserDetailsService implements UserDetailsService {
  @Value("${ppadel.admin.password}")
  private String adminPasswordHash;

  @Autowired
  private KorisnikService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return new User(username, adminPasswordHash, authorities(username));
  }

  private List<GrantedAuthority> authorities(String username) {
    if ("admin".equals(username))
      return commaSeparatedStringToAuthorityList("ROLE_ADMIN");
    Korisnik korisnik = userService.findByEmail(username).orElseThrow(
      () -> new UsernameNotFoundException("No user '" + username + "'")
    );
    if (korisnik.isOwner())
      return commaSeparatedStringToAuthorityList("ROLE_OWNER");
    else if(korisnik.isAdmin())
      return commaSeparatedStringToAuthorityList("ROLE_ADMIN");
    else
      return commaSeparatedStringToAuthorityList("ROLE_PLAYER");
    
  }
}

