package org.senla.share.security;

import org.senla.share.jwt.JwtAuthConverter;
import org.senla.share.rest.RestAuthenticationEntryPoint;
import org.senla.share.service.UserService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String AUTHORITY_PREFIX = "";
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
                        "/v3/api-docs**",
                        "/v3/api-docs/**",        // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(unauthorizedHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v3/api-docs", "/configuration/**",
                "/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/api-docs/**", "/swagger-ui.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtAuthConverter());
        return jwtAuthenticationConverter;
    }
}
