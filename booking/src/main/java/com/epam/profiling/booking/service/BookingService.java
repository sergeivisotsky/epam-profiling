package com.epam.profiling.booking.service;

import java.util.ArrayList;
import java.util.List;

import com.epam.profiling.common.CalculationService;
import com.epam.profiling.common.dto.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingService {

    private static final Integer CAPACITY = 10_000;

    private final String auditorUrl;
    private final String acceptBookingUri;
    private final RestTemplate restTemplate;
    private final CalculationService calculationService;

    public BookingService(@Value("${auditor.url}") String auditorUrl,
                          @Value("${auditor.accept-booking-uri}") String acceptBookingUri,
                          RestTemplate restTemplate,
                          CalculationService calculationService) {
        this.auditorUrl = auditorUrl;
        this.acceptBookingUri = acceptBookingUri;
        this.restTemplate = restTemplate;
        this.calculationService = calculationService;
    }

    public Booking sendBooking(Booking booking) {
        List<Booking> bookings = new ArrayList<>(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            bookings.add(new Booking("someDestination", "New-York"));
            calculationService.highCpuUsageMethod(booking);
            postBooking(booking);
        }
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
