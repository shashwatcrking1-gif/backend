package com.civicshield.security;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {
    @Test
    void rejectsMissingOrTooShortSecretWithRenderConfigurationHint() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> new JwtService("", Duration.ofHours(8)));

        org.junit.jupiter.api.Assertions.assertTrue(error.getMessage().contains("JWT_SECRET"));
    }

    @Test
    void acceptsSecretWithAtLeastThirtyTwoUtf8Bytes() {
        assertDoesNotThrow(() -> new JwtService("0123456789abcdef0123456789abcdef", Duration.ofHours(8)));
    }
}
