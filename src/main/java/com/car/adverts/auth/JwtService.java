package com.car.adverts.auth;

import com.car.adverts.model.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private static final Long REFRESH_TOKEN_VALID_TIME = 1000 * 60 * 50L;
    public static final Integer REFRESH_TOKEN_VALID_TIME_SECONDS = 300;

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

    // refresh token


    /**
     * Gets the token.
     *
     * @param wsAuthToken
     *            the ws auth token
     * @return the token
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
//  public String getRefreshToken(final OssWSAuthToken wsAuthToken) throws IllegalArgumentException {
//    final String text = wsAuthToken.getUserName() + ":" + wsAuthToken.getPassword() + ":" + new Date().getTime() + ":" + Math.random();
//    final StringBuilder hexString = new StringBuilder();
//
//    final byte[] hash = getMessageDigest().digest(text.getBytes(StandardCharsets.UTF_8));
//    for (int i = 0; i < hash.length; i++) {
//      if ((0xff & hash[i]) < 0x10) {
//        hexString.append("0"
//                + Integer.toHexString((0xFF & hash[i])));
//      } else {
//        hexString.append(Integer.toHexString(0xFF & hash[i]));
//      }
//    }
//    return hexString.toString();
//  }


    /**
     * Gets the refresh token.
     *
     * @param username username
     * @return the token
     * @throws IllegalArgumentException the illegal argument exception
     */
    public TokenData getRefreshToken(final String username) throws IllegalArgumentException {
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
        TokenData td = TokenData.builder()
                .token(hexString.toString())
                .created(LocalDateTime.now())
                .expiresAt(now.plus(Duration.ofMillis(REFRESH_TOKEN_VALID_TIME))
                )
                .build();
        return td;
    }


//  /**
//   * Gets the refresh token.
//   *
//   * @param username
//   *            the username
//   * @return the token
//   * @throws IllegalArgumentException
//   *             the illegal argument exception*/
//  public String getToken(final String username) throws IllegalArgumentException {
//    final String text = username + ":" + new Date().getTime() + ":" + Math.random();
//    final StringBuilder hexString = new StringBuilder();
//
//    final byte[] hash = getMessageDigest().digest(text.getBytes(StandardCharsets.UTF_8));
//    for (int i = 0; i < hash.length; i++) {
//      if ((0xff & hash[i]) < 0x10) {
//        hexString.append("0"
//                + Integer.toHexString((0xFF & hash[i])));
//      } else {
//        hexString.append(Integer.toHexString(0xFF & hash[i]));
//      }
//    }
//    return hexString.toString();
//  }

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
                throw new IllegalArgumentException(e);
            }
            return digest;
        }
    }
}

