package com.epam.profiling.booking.service;

import java.util.ArrayList;
import java.util.List;

import com.epam.profiling.common.CalculationService;
import com.epam.profiling.common.dto.Booking;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingService {

    private static final Integer CAPACITY = 100_000;

    private final String auditorUrl;
    private final String acceptBookingUri;
    private final RestTemplate restTemplate;
    private final CalculationService calculationService;
    private final JaegerTracer tracer;

    public BookingService(@Value("${auditor.url}") String auditorUrl,
                          @Value("${auditor.accept-booking-uri}") String acceptBookingUri,
                          RestTemplate restTemplate,
                          CalculationService calculationService, JaegerTracer tracer) {
        this.auditorUrl = auditorUrl;
        this.acceptBookingUri = acceptBookingUri;
        this.restTemplate = restTemplate;
        this.calculationService = calculationService;
        this.tracer = tracer;
    }

    public Booking sendBooking(Booking booking) {
        JaegerSpan span = tracer.buildSpan("send booking").start();

        List<Booking> bookings = new ArrayList<>(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            bookings.add(new Booking("someDestination", "New-York"));

            span.setTag("reaching_high_cpu_usage", booking.getDestination());

            calculationService.highCpuUsageMethod(booking);
            postBooking(booking);
        }
        span.finish();
        return sendBookingFromList(bookings);
    }

    private Booking sendBookingFromList(List<Booking> bookings) {
        bookings.forEach(this::postBooking);
        return bookings.get(0);
    }

    private void postBooking(Booking booking) {
        restTemplate.postForEntity(auditorUrl + acceptBookingUri, booking, Booking.class);
    }
}
