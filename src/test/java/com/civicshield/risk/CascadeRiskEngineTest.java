package com.civicshield.risk;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CascadeRiskEngineTest {
    private final CascadeRiskEngine engine = new CascadeRiskEngine();
    @Test void propagatesAndClampsInOrder() {
        var result = engine.analyze(80, Map.of());
        assertEquals(70.4, result.nodes().get("rain-flood"));
        assertEquals(80 * .88 * .91, result.nodes().get("flood-road"), .01);
        assertEquals("HIGH", result.severity());
    }
    @Test void addsExplicitLocalFactorsAndClassifiesBoundaries() {
        assertEquals("LOW", engine.analyze(29.99, Map.of()).severity());
        assertEquals("MEDIUM", engine.analyze(40, Map.of("rain-flood", 100d)).severity());
        assertThrows(IllegalArgumentException.class, () -> engine.analyze(101, Map.of()));
    }
}
