package by.ryni.share;

import by.ryni.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.annotation.Resource;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String AUTHORITY_PREFIX = ""; //"ROLE_";
    private static final String CLAIM_AUTHORITIES = "authorities";
    @Resource(name = "userService")
    private UserService userService;

    @Bean
    public RestAuthenticationEntryPoint unauthorizedHandler() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().formLogin().disable()
                .logout().disable()
                .authorizeRequests()
                .antMatchers("/authentication", "/register",
                        "/v3/api-docs",           // swagger
                        "/v3/api-docs/**",        // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())) //jwtAuthenticationConverter()
                .authenticationEntryPoint(unauthorizedHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v3/api-docs",
//                "/swagger-ui.html",
//                "/swagger-ui/**");
        web.ignoring().antMatchers("/v3/api-docs", "/configuration/**",
                "/swagger-resources/**",  "/swagger-ui/**", "/webjars/**", "/api-docs/**", "/swagger-ui.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(AUTHORITY_PREFIX);
        converter.setAuthoritiesClaimName(CLAIM_AUTHORITIES);
        return converter;
    }
}
