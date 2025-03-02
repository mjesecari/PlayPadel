
/**package fer.progi.mjesecari.ppadel.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;

import java.util.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityBasic {

    @Value("${progi.frontend.url}")
    private String frontendUrl;
    @Value("${progi.frontend.register.url}")
    private String frontendRegisterUrl;

    @Autowired
    private KorisnikService userService;
    // @Autowired
    // private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    @Profile("oauth-security")
    SecurityFilterChain registerFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(CorsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/register").permitAll(); // dostupne svakome
                    auth.requestMatchers("/register/igrac").permitAll();
                    auth.requestMatchers("/register/vlasnik").permitAll();
                    auth.requestMatchers("/h2-console/*").permitAll();
                    auth.requestMatchers("/korisnik/*").permitAll();
                    auth.anyRequest().authenticated();
                }).oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(
                                    userInfoEndpoint -> {
                                        userInfoEndpoint.userAuthoritiesMapper(this.authorityMapper());
                                        // userInfoEndpoint.baseUri();
                                    })
                            .successHandler(
                                    (request, response, authentication) -> {
                                        response.sendRedirect("http://localhost:5173/MainPage");
                                    });
                    oauth2.authorizationEndpoint().baseUri("/oauth2/authorization/**");
                })
                .build();
    }

    @Bean
    CorsConfigurationSource CorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl, "http://localhost:8080"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    private GrantedAuthoritiesMapper authorityMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();

            authorities.forEach(authority -> {
                if (OAuth2UserAuthority.class.isInstance(authority)) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                    String userEmail = (String)userAttributes.get("email");
                    Korisnik korisnik = userService.findByEmail(userEmail).orElse(new Korisnik());
                    if(korisnik.getId() == null){
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_oauth2"));
                        return;
                    }
                    if (korisnik.isAdmin())
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    if(korisnik.isOwner())
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
                    else
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_PLAYER"));

                    return;
                }
            });

            return mappedAuthorities;
        };
    }

}**/
/**package fer.progi.mjesecari.ppadel.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;

import java.util.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityBasic {

    @Value("${progi.frontend.url}")
    private String frontendUrl;
    @Value("${progi.frontend.register.url}")
    private String frontendRegisterUrl;

    @Autowired
    private KorisnikService userService;
    // @Autowired
    // private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    @Profile("oauth-security")
    SecurityFilterChain registerFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(CorsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/register").permitAll(); // dostupne svakome
                    auth.requestMatchers("/h2-console/*").permitAll();
                    auth.anyRequest().authenticated();
                }).oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(
                                    userInfoEndpoint -> {
                                        userInfoEndpoint.userAuthoritiesMapper(this.authorityMapper());
                                        // userInfoEndpoint.baseUri();
                                    })
                            .successHandler(
                                    (request, response, authentication) -> {
                                        response.sendRedirect(frontendUrl);
                                    });
                    oauth2.authorizationEndpoint().baseUri("/oauth2/authorization/");
                })
                .build();
    }

    @Bean
    CorsConfigurationSource CorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    private GrantedAuthoritiesMapper authorityMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();

            authorities.forEach(authority -> {
                if (OAuth2UserAuthority.class.isInstance(authority)) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                    String userEmail = (String)userAttributes.get("email");
                    Korisnik korisnik = userService.findByEmail(userEmail).orElse(new Korisnik());
                    if(korisnik.getId() == null){
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_oauth2"));
                        return;
                    }
                    if (korisnik.isAdmin())
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    else if(korisnik.isOwner())
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
                    else
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_PLAYER"));

                    return;
                }
            });

            return mappedAuthorities;
        };
    }

}**/
package fer.progi.mjesecari.ppadel.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;

import java.util.List;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityBasic {

    @Value("${progi.frontend.url}")
    private String frontendUrl;
    @Value("${progi.frontend.register.url}")
    private String frontendRegisterUrl;

    @Autowired
    private KorisnikService userService;
    // @Autowired
    // private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    @Profile("oauth-security")
    SecurityFilterChain registerFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(CorsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/register").permitAll(); // dostupne svakome
                    auth.requestMatchers("register/vlasnik").permitAll();
                    auth.requestMatchers("register/igrac").permitAll();
                    auth.requestMatchers("/h2-console/*").permitAll();
                    auth.anyRequest().authenticated();
                }).oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(
                                    userInfoEndpoint -> {
                                        userInfoEndpoint.userAuthoritiesMapper(this.authorityMapper());
                                        // userInfoEndpoint.baseUri();
                                    })
                            .successHandler(
                                    (request, response, authentication) -> {
                                        response.sendRedirect(frontendUrl);
                                    });
                    oauth2.authorizationEndpoint().baseUri("/oauth2/authorization/");
                }).logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .build();
    }

    @Bean
    CorsConfigurationSource CorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    private GrantedAuthoritiesMapper authorityMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();

            authorities.forEach(authority -> {
                if (OAuth2UserAuthority.class.isInstance(authority)) {
                    OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                    String userEmail = (String)userAttributes.get("email");
                    Korisnik korisnik = userService.findByEmail(userEmail).orElse(new Korisnik());
                    if(korisnik.getId() == null){
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_oauth2"));
                        return;
                    }
                    if (korisnik.isAdmin())
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    else if(korisnik.isOwner())
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
                    else
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_PLAYER"));

                    return;
                }
            });

            return mappedAuthorities;
        };
    }

}
