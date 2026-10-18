package com.civicshield.risk;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

/** Deterministic cascade propagation. Local factors are caller-supplied values on a 0..100 scale. */
public class CascadeRiskEngine {
    public static final Map<String, Double> WEIGHTS;
    static {
        Map<String, Double> weights = new LinkedHashMap<>();
        weights.put("rain-flood", .88); weights.put("flood-road", .91);
        weights.put("road-traffic", .93); weights.put("traffic-ambulance", .84);
        weights.put("ambulance-hospital", .76);
        WEIGHTS = Map.copyOf(weights);
    }

    public Result analyze(double seed, Map<String, Double> localFactors) {
        if (!Double.isFinite(seed) || seed < 0 || seed > 100) throw new IllegalArgumentException("seed must be between 0 and 100");
        Map<String, Double> nodes = new LinkedHashMap<>();
        double previous = seed;
        for (var edge : WEIGHTS.entrySet()) {
            double factor = localFactors.getOrDefault(edge.getKey(), 0d);
            if (!Double.isFinite(factor) || factor < 0 || factor > 100) throw new IllegalArgumentException("local factor " + edge.getKey() + " must be between 0 and 100");
            previous = clamp(previous * edge.getValue() + factor * .1);
            nodes.put(edge.getKey(), round(previous, 2));
        }
        double mean = nodes.values().stream().mapToDouble(Double::doubleValue).average().orElse(seed);
        double maximum = nodes.values().stream().mapToDouble(Double::doubleValue).max().orElse(seed);
        double score = round(.5 * mean + .5 * maximum, 2);
        return new Result(round(seed, 2), nodes, score, severity(maximum));
    }

    private static double clamp(double value) { return Math.max(0, Math.min(100, value)); }
    private static double round(double value, int scale) { return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).doubleValue(); }
    private static String severity(double maximum) {
        if (maximum >= 80) return "CRITICAL";
        if (maximum >= 60) return "HIGH";
        if (maximum >= 30) return "MEDIUM";
        return "LOW";
    }
    public record Result(double seed, Map<String, Double> nodes, double score, String severity) {}
}
