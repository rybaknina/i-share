package org.senla.share.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String AUTHORITY_PREFIX = "";
    private static final String CLAIM_AUTHORITIES = "authorities";
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter();

    public JwtAuthConverter() {
    }

    private static Collection<? extends GrantedAuthority> extractAuthorities(final Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.of(jwt.getClaimAsString(CLAIM_AUTHORITIES)
                .split(",", -1))
                .map(role -> new SimpleGrantedAuthority(AUTHORITY_PREFIX + role))
                .collect(Collectors.toSet());
        if (authorities.size() > 0) {
            return authorities;
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
        Collection<GrantedAuthority> authorities = Stream
                .concat(defaultGrantedAuthoritiesConverter.convert(source).stream(), extractAuthorities(source).stream())
                .collect(Collectors.toSet());
        return authorities;
    }

    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(AUTHORITY_PREFIX);
        converter.setAuthoritiesClaimName(CLAIM_AUTHORITIES);

        return converter;
    }
}
