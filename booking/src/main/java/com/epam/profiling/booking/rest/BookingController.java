package com.epam.profiling.booking.rest;

import com.epam.profiling.booking.service.BookingService;
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

    @PostMapping("/save")
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking request) {
        JaegerSpan span = tracer.buildSpan("receive booking in controller").start();

        ResponseEntity<Booking> response = ResponseEntity.ok(bookingService.sendBooking(request));

        if (!response.getStatusCode().is2xxSuccessful()) {
            span.setTag("failed_to_send_booking", response.getStatusCode().value());
        }
        span.setTag("successfully_sent_booking", response.getStatusCode().value());

        return response;
    }

}
