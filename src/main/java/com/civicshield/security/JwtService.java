package com.civicshield.security;
import com.civicshield.domain.AppUser; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.Instant; import java.time.Duration; import java.util.Date;
@Service public class JwtService {
 private final SecretKey key; private final Duration expiration;
 public JwtService(@Value("${civicshield.jwt.secret}") String secret,@Value("${civicshield.jwt.expiration}") Duration expiration){
  byte[] secretBytes=secret.getBytes(StandardCharsets.UTF_8);
  if(secretBytes.length==0)throw new IllegalStateException("JWT_SECRET is empty; set this Render environment variable to a random value of at least 32 UTF-8 bytes");
  if(secretBytes.length<32)throw new IllegalArgumentException("JWT_SECRET is too short; provide at least 32 UTF-8 bytes");
  this.key=Keys.hmacShaKeyFor(secretBytes);
  this.expiration=expiration;
 }
 public String issue(AppUser user){Instant now=Instant.now();return Jwts.builder().subject(user.getEmail()).claim("role",user.getRole().name()).issuedAt(Date.from(now)).expiration(Date.from(now.plus(expiration))).signWith(key).compact();}
 public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
