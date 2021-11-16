package com.epam.profiling.auditor.service;

import com.epam.profiling.common.CalculationService;
import com.epam.profiling.common.dto.Booking;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    private final JaegerTracer tracer;
    private final CalculationService service;

    public BookingService(JaegerTracer tracer, CalculationService service) {
        this.tracer = tracer;
        this.service = service;
    }

    public Booking acceptBooking(Booking booking) {
        LOG.info("Received the following booking: {}", booking.toString());
        return methodWhichLoadsMemory(service.highCpuUsageMethod(booking));
    }

    public Booking methodWhichLoadsMemory(Booking booking) {
        JaegerSpan span = tracer.buildSpan("receive booking in service").start();

        for (int i = 0; i < 100_000; i++) {
            span.setTag("receive_booking", booking.getName());
            new Booking(booking.getName(), booking.getFrom());
        }

        span.finish();
        return booking;
    }
}
