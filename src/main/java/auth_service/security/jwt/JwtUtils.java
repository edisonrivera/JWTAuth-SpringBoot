package auth_service.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import auth_service.security.services.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtUtils {
  @Value("${app.jwtSecret}")
  private String jwtSecret;
  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder().subject(userPrincipal.getUsername())
            .claim("id", userPrincipal.getId())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .issuedAt(new Date(System.currentTimeMillis())).signWith(key()).compact();
  }
  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Long getIdFromJwtToken(String token) {
    return getClaimFromToken(parseJWT(token), claim -> claim.get("id", Long.class));
  }

  private String parseJWT(String token){
    if (token.startsWith("Bearer ")) {
      return token.substring(7);
    }
    return token;
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
            .verifyWith((SecretKey) key())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().verifyWith((SecretKey) key()).build().parse(authToken);
      return true;

    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
