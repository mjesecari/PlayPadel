package fer.progi.mjesecari.ppadel.security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Set;

import java.util.HashSet;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class WebSecurityBasic {

    @Value("${progi.frontend.url}")
    private String frontendUrl;
    @Value("${progi.frontend.register.url}")
    private String frontendRegisterUrl;

    @Bean
    @Profile("basic-security")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    // login: localhost:8080/oauth2/authorization/google
    @Bean
    @Profile("oauth-security")
    public SecurityFilterChain oauthFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                auth.anyRequest().authenticated();
            })
            .oauth2Login(oauth2 -> {
                oauth2
                    .userInfoEndpoint(
                        userInfoEndpoint -> userInfoEndpoint.userAuthoritiesMapper(this.authorityMapper()))
                    .successHandler(
                        (request, response, authentication) -> {
                            response.sendRedirect(frontendRegisterUrl);
                        });
            })
            .exceptionHandling(handling -> handling.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
            .build();
    }


    // @Bean
    // @Profile({ "basic-security", "form-security" })
    // @Order(Ordered.HIGHEST_PRECEDENCE)
    // public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
    //     http.securityMatcher(PathRequest.toH2Console());
    //     http.csrf(AbstractHttpConfigurer::disable);
    //     http.headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    //     return http.build();
    // }
    private GrantedAuthoritiesMapper authorityMapper() {
        // TODO: check if user exists in db and return ROLE_oauth2 only if he does not
        return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<GrantedAuthority>();

			authorities.forEach(authority -> {
				if (OAuth2UserAuthority.class.isInstance(authority)) {
					OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

					Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

					// Map the attributes found in userAttributes
					// to one or more GrantedAuthority's and add it to mappedAuthorities
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_oauth2"));

				}
			});

			return mappedAuthorities;
		};
    }

}
