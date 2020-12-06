package org.senla.share.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.senla.share.dto.UserDto;
import org.senla.share.dto.UserLogin;
import org.senla.share.entity.JwtToken;
import org.senla.share.jwt.JwtHelper;
import org.senla.share.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    @Operation(summary = "Login")
    @PostMapping(path = "/authentication")
    public JwtToken login(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody UserLogin userLogin) {
        UserDetails userDetails;
        userDetails = userService.loadUserByUsername(userLogin.getUsername());

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

        throw new AuthenticationCredentialsNotFoundException("User not authenticated");
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Register the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @PutMapping("/register")
    public ResponseEntity<Object> register(@RequestHeader(value = "Accept-Language", defaultValue = "ru", required = false) String language, @Valid @RequestBody UserDto dto) {
        Optional<UserDto> userDto = userService.save(dto);
        if (!userDto.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto.get(), HttpStatus.CREATED);
    }
}
