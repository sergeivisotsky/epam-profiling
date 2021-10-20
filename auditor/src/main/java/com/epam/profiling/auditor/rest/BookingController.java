package com.epam.profiling.auditor.rest;

import com.epam.profiling.auditor.service.BookingService;
import com.epam.profiling.common.dto.Booking;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final JaegerTracer tracer;
    private final BookingService bookingService;

    public BookingController(JaegerTracer tracer, BookingService bookingService) {
        this.tracer = tracer;
        this.bookingService = bookingService;
    }

    @PostMapping("/accept")
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking request) {
        JaegerSpan span = tracer.buildSpan("receive booking in controller").start();

        ResponseEntity<Booking> response = ResponseEntity.ok(bookingService.acceptBooking(request));

        if (!response.getStatusCode().is2xxSuccessful()) {
            span.setTag("failed_on_receiving_booking", response.getStatusCode().value());
        }
        span.setTag("successfully_received_booking", response.getStatusCode().value());

        return response;
    }
}
