package com.civicshield.config;
import com.civicshield.domain.*;import com.civicshield.repo.UserRepository;import org.springframework.beans.factory.annotation.Value;import org.springframework.boot.ApplicationArguments;import org.springframework.boot.ApplicationRunner;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Component;
@Component public class BootstrapAdmin implements ApplicationRunner {
 private final UserRepository users;private final PasswordEncoder encoder;private final String email,password,name;
 public BootstrapAdmin(UserRepository users,PasswordEncoder encoder,@Value("${BOOTSTRAP_ADMIN_EMAIL:}")String email,@Value("${BOOTSTRAP_ADMIN_PASSWORD:}")String password,@Value("${BOOTSTRAP_ADMIN_NAME:Local Administrator}")String name){this.users=users;this.encoder=encoder;this.email=email;this.password=password;this.name=name;}
 @Override public void run(ApplicationArguments args){if(email.isBlank()&&password.isBlank())return;if(email.isBlank()||password.length()<12)throw new IllegalStateException("Set both BOOTSTRAP_ADMIN_EMAIL and a BOOTSTRAP_ADMIN_PASSWORD of at least 12 characters");if(users.findByEmailIgnoreCase(email).isEmpty())users.save(new AppUser(email,encoder.encode(password),name,Role.ADMIN));}
}
