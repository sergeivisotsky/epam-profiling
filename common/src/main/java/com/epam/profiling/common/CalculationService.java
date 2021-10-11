package com.epam.profiling.common;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.epam.profiling.common.dto.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.atan;
import static java.lang.Math.cbrt;
import static java.lang.Math.tan;

public class CalculationService {

    private static final Logger LOG = LoggerFactory.getLogger(CalculationService.class);

    public Booking highCpuUsageMethod(Booking booking) {
        try {
            List<Callable<Booking>> executables = List.of(
                    () -> calculateCubicAtan(booking),
                    () -> calculateCubicAtan(booking),
                    () -> calculateCubicAtan(booking));

            Executors.newFixedThreadPool(7).invokeAll(executables);
            return booking;
        } catch (InterruptedException e) {
            throw new RuntimeException("Tangent, Cotangent and Cubit square calculation interrupted", e);
        }
    }

    private Booking calculateCubicAtan(Booking booking) {
        double result = tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(tan(atan(
                Double.parseDouble(booking.getDuration().getStartDate())))))))))))));
        double cbrtResult = cbrt(result);
        LOG.info("Result: {} cbrtResult: {}", result, cbrtResult);
        return booking;
    }
}
