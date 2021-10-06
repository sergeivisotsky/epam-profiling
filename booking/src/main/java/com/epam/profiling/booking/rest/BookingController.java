package com.epam.profiling.booking.rest;

import com.epam.profiling.booking.service.BookingService;
import com.epam.profiling.common.dto.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/save")
    public ResponseEntity<Booking> saveBooking(@RequestBody Booking request) {
        return ResponseEntity.ok(bookingService.sendBooking(request));
    }

}