package by.ryni.share.controller;

import by.ryni.share.dto.UserDto;
import by.ryni.share.dto.UserLogin;
import by.ryni.share.entity.JwtToken;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.handler.ResponseEntityError;
import by.ryni.share.jwt.JwtHelper;
import by.ryni.share.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class LoginController {
    private final static Logger logger = LogManager.getLogger(LoginController.class);
    private final JwtHelper jwtHelper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(JwtHelper jwtHelper, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/authentication")
    public JwtToken login(@RequestBody UserLogin userLogin) {
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(userLogin.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (passwordEncoder.matches(userLogin.getPassword(), userDetails.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", userLogin.getUsername());

            String authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            claims.put("authorities", authorities);
            claims.put("userId", String.valueOf(1));

            String jwt = jwtHelper.createJwtForClaims(userLogin.getUsername(), claims);
            return new JwtToken(jwt);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserDto userDto) {
        try {
            userService.save(userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            return ResponseEntityError
                    .objectResponseEntity(logger, "Service exception " + e.getMessage(), e.getLocalizedMessage());
        }
    }
}
