package com.epam.profiling.booking.service;

import java.util.ArrayList;
import java.util.List;

import com.epam.profiling.common.dto.Booking;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingServiceImpl implements BookingService {

    private static final String URL = "http://auditor:8384/api/v1/booking/accept";
    private final RestTemplate restTemplate;

    public BookingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Booking sendBooking(Booking booking) {
        List<Booking> bookings = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            bookings.add(new Booking("someDestination", "New-York"));
            restTemplate.postForEntity(URL, booking, Booking.class);
        }
        return sendBookingFromList(bookings);
    }

    private Booking sendBookingFromList(List<Booking> bookings) {
        bookings.forEach(booking -> restTemplate.postForEntity(URL, booking, Booking.class));
        return bookings.get(0);
    }
}
