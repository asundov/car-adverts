package com.car.adverts.common.auth;

import com.car.adverts.common.config.exception.TokenRefreshException;
import com.car.adverts.common.constants.CarAdvertsErrorMessagesConstants;
import com.car.adverts.common.model.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@Log4j2
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final Long REFRESH_TOKEN_VALID_TIME = 1000 * 60 * 50L;
    private static final Long JWT_VALID_TIME = 1000 * 60 * 500L;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static MessageDigest digest = null;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public TokenData generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    public TokenData generateToken(Map<String, Object> extraClaims, String username) {
        Date created = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(System.currentTimeMillis() + JWT_VALID_TIME);
        String token = Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(created)
                .expiration(expiresAt)
                .signWith(getSignInKey())
                .compact();
        return TokenData.builder()
                .created(created.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .expiresAt(expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .token(token)
                .build();
    }

    public boolean isTokenValid(String token) {
        return (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        Jwts.builder().signWith(getSignInKey()).compact();

        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Gets the refresh token.
     *
     * @param username username
     * @return the token
     */
    public TokenData getRefreshToken(final String username) {
        if (username == null || username.trim().isEmpty()) {
            log.error("Invalid username provided for token generation.");
            throw new TokenRefreshException(CarAdvertsErrorMessagesConstants.USERNAME_NULL_ERROR);
        }
        try {
            final String text = username + ":" + new Date().getTime() + ":" + Math.random();
            final StringBuilder hexString = new StringBuilder();
            final byte[] hash = getMessageDigest().digest(text.getBytes(StandardCharsets.UTF_8));

            for (byte b : hash) {
                if ((0xff & b) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & b)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & b));
                }
            }
            LocalDateTime now = LocalDateTime.now();
            return TokenData.builder()
                    .token(hexString.toString())
                    .created(now)
                    .expiresAt(now.plus(Duration.ofMillis(REFRESH_TOKEN_VALID_TIME)))
                    .build();
        } catch (Exception e) {
            log.error("An unexpected error occurred during token generation: " + e.getMessage());
            throw new TokenRefreshException(CarAdvertsErrorMessagesConstants.GENERATE_TOKEN_ERROR);
        }
    }

    /**
     * Gets the message digest.
     *
     * @return the message digest
     */
    private MessageDigest getMessageDigest() {
        if (digest != null) {
            return digest;
        } else {
            try {
                digest = MessageDigest.getInstance(HASH_ALGORITHM);
            } catch (final NoSuchAlgorithmException e) {
                throw new TokenRefreshException(e.getMessage());
            }
            return digest;
        }
    }
}


