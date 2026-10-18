package com.civicshield.security;
import com.civicshield.domain.AppUser; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.Instant; import java.time.Duration; import java.util.Date;
@Service public class JwtService {
 private final SecretKey key; private final Duration expiration;
 public JwtService(@Value("${civicshield.jwt.secret}") String secret,@Value("${civicshield.jwt.expiration}") Duration expiration){if(secret.length()<32)throw new IllegalArgumentException("JWT secret must have at least 32 characters");this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiration=expiration;}
 public String issue(AppUser user){Instant now=Instant.now();return Jwts.builder().subject(user.getEmail()).claim("role",user.getRole().name()).issuedAt(Date.from(now)).expiration(Date.from(now.plus(expiration))).signWith(key).compact();}
 public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
