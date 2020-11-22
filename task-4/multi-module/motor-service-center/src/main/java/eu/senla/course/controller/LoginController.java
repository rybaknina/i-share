package eu.senla.course.controller;

import eu.senla.course.api.service.IUserService;
import eu.senla.course.config.jwt.JwtTokenService;
import eu.senla.course.dto.user.UserDto;
import eu.senla.course.dto.user.UserLogin;
import eu.senla.course.entity.JwtToken;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private IUserService userService;

    @Autowired
    @Qualifier("userService")
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@RequestBody UserLogin userLogin, HttpServletResponse httpServletResponse) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getUsername(),
                        userLogin.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenService.generateToken(authentication);

        return ResponseEntity.ok(new JwtToken(token));
    }

    @PostMapping("/register")
    public void save(@RequestBody UserDto userDto) throws ServiceException {
        userService.save(userDto);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        // TODO: need to implement
        return HttpStatus.OK.toString();
    }
}
