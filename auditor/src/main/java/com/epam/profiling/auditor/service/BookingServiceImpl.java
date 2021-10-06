package com.epam.profiling.auditor.service;

import java.util.concurrent.Executors;

import com.epam.profiling.common.dto.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.lang.Math.atan;
import static java.lang.Math.cbrt;
import static java.lang.Math.tan;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public Booking acceptBooking(Booking booking) {
        LOG.info("Received the following booking: {}", booking.toString());
        return calculateTanInThreads(booking);
    }

    private Booking calculateTanInThreads(Booking booking) {
        Executors.newFixedThreadPool(5)
                .submit(() -> {
                    double result = tan(atan(tan(atan(tan(atan(tan(
                            atan(tan(atan(tan(atan(
                                    Double.parseDouble(booking.getDuration().getStartDate())))))))))))));
                    double cbrtResult = cbrt(result);
                    LOG.info("Result: {} cbrtResult: {}", result, cbrtResult);
                });
        return booking;
    }
}
