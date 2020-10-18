package eu.senla.course.config.jwt;

import eu.senla.course.api.service.IUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger logger =  LogManager.getLogger(JwtAuthorizationFilter.class);
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TOKEN_HEADER = "Authorization";

    @Resource(name = "userService")
    private IUserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, IUserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(TOKEN_HEADER);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        JwtTokenService jwtTokenService = webApplicationContext.getBean(JwtTokenService.class);

        String header = request.getHeader(TOKEN_HEADER);
        String username = null;
        String token = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            token = header.replace(TOKEN_PREFIX, "");
            try {
                username = jwtTokenService.getUsernameFromToken(token);
            } catch (ExpiredJwtException exception) {
                logger.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                logger.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                logger.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                logger.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                logger.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtTokenService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenService.getAuthentication(token, userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("Authenticated user " + username + ", setting security context");
                return authentication;
            }
        }
        return null;
    }
}
