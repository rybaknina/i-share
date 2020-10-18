package eu.senla.course.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.course.api.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_HEADER = "Authorization";

    private AuthenticationManager authenticationManager;

    private final IUserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, IUserService userService) {
        this.setAuthenticationManager(authenticationManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        HashMap<String, Object> responseBody = new HashMap<>();

        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        JwtTokenService jwtTokenService = webApplicationContext.getBean(JwtTokenService.class);
        String token = jwtTokenService.generateToken(authResult);

        responseBody.put("token", token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
        response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
    }
}
