package com.civicshield.security;

import org.junit.jupiter.api.Test;
import com.civicshield.domain.AppUser;
import com.civicshield.domain.Role;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtServiceTest {
    @Test
    void rejectsMissingSecretWithRenderConfigurationHint() {
        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> new JwtService("", Duration.ofHours(8)));

        org.junit.jupiter.api.Assertions.assertTrue(error.getMessage().contains("JWT_SECRET"));
    }

    @Test
    void rejectsSecretShorterThanThirtyTwoUtf8Bytes() {
        assertThrows(IllegalArgumentException.class,
                () -> new JwtService("0123456789abcdef0123456789abcde", Duration.ofHours(8)));
    }

    @Test
    void acceptsSecretWithAtLeastThirtyTwoUtf8Bytes() {
        assertDoesNotThrow(() -> new JwtService("0123456789abcdef0123456789abcdef", Duration.ofHours(8)));
    }

    @Test
    void signingAndValidationUseTheConfiguredKey() {
        JwtService jwt = new JwtService("0123456789abcdef0123456789abcdef", Duration.ofHours(8));
        AppUser user = new AppUser("civicshield@example.org", "hash", "CivicShield", Role.ANALYST);

        assertEquals("civicshield@example.org", jwt.subject(jwt.issue(user)));
    }
}
