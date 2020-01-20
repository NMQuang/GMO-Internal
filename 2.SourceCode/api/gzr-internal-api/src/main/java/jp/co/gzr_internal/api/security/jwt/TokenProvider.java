package jp.co.gzr_internal.api.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jp.co.gzr_internal.api.security.SecurityUtils;
import jp.co.gzr_internal.api.service.dto.ProjectAfterLoginDto;
import jp.co.gzr_internal.api.web.rest.UserJWTController.JWTToken;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.util.Utils;

/**
 * The Class TokenProvider.
 */
@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final JHipsterProperties jHipsterProperties;

    public TokenProvider(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
        if (!StringUtils.isEmpty(secret)) {
            log.warn("Warning: the JWT key used is not Base64-encoded. "
                + "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security.");
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64
                .decode(jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret());
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = 1000
            * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000
            * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public JWTToken createToken(Authentication authentication, boolean rememberMe) {

        long now = Utils.getCurrentDate(Contants.CONST_STR_PATTERN_YYYYMMDDHHMMSS).getTime();

        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        JWTToken jwtToken = new JWTToken();
        String jwt = Jwts.builder().setSubject(authentication.getName() + Contants.CONST_STR_HASH_TAG + now)
            .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();

        jwtToken.setExpirationDate(validity.getTime());
        jwtToken.setIdToken(jwt);

        return jwtToken;
    }

    
    public JWTToken createToken(Authentication authentication, boolean rememberMe, List<ProjectAfterLoginDto> projectInfo) {

        long now = Utils.getCurrentDate(Contants.CONST_STR_PATTERN_YYYYMMDDHHMMSS).getTime();

        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        JWTToken jwtToken = new JWTToken();
        String jwt = Jwts.builder().setSubject(authentication.getName() + Contants.CONST_STR_HASH_TAG + now)
            .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();

        jwtToken.setExpirationDate(validity.getTime());
        jwtToken.setIdToken(jwt);

        return jwtToken;
    }
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        User principal = new User(claims.getSubject(), Contants.CONST_STR_BLANK, new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    /**
     * Update mail address for token when user change the mail address.
     *
     * @author datnt
     *
     * @param authentication
     *
     * @return jwtToken
     */
    public JWTToken updateToken(Authentication authentication, long time) {

        // Get info old token
        Optional<String> oldJwtToken = SecurityUtils.getCurrentUserJWT();

        if (oldJwtToken.isPresent()) {

            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(oldJwtToken.get()).getBody();

            Date validity = claims.getExpiration();

            JWTToken newJwtToken = new JWTToken();
            String jwt = Jwts.builder().setSubject(authentication.getName() + Contants.CONST_STR_HASH_TAG + time)
                .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();

            newJwtToken.setExpirationDate(validity.getTime());
            newJwtToken.setIdToken(jwt);

            return newJwtToken;
        }

        return null;
    }
}
