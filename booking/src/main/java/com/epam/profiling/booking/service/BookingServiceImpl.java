package com.epam.profiling.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.epam.profiling.common.dto.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.Math.atan;
import static java.lang.Math.cbrt;
import static java.lang.Math.tan;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final String auditorUrl;
    private final String acceptBookingUri;
    private final RestTemplate restTemplate;

    public BookingServiceImpl(@Value("${auditor.url}") String auditorUrl,
                              @Value("${auditor.accept-booking-uri}") String acceptBookingUri,
                              RestTemplate restTemplate) {
        this.auditorUrl = auditorUrl;
        this.acceptBookingUri = acceptBookingUri;
        this.restTemplate = restTemplate;
    }

    @Override
    public Booking sendBooking(Booking booking) {
        List<Booking> bookings = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            bookings.add(new Booking("someDestination", "New-York"));
            calculateTanInThreads(booking);
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

    private void calculateTanInThreads(Booking booking) {
        Executors.newFixedThreadPool(5)
                .submit(() -> {
                    double result = tan(atan(tan(atan(tan(atan(tan(
                            atan(tan(atan(tan(atan(
                                    Double.parseDouble(booking.getDuration().getStartDate())))))))))))));
                    double cbrtResult = cbrt(result);
                    LOG.info("Result: {} cbrtResult: {}", result, cbrtResult);
                });
    }
}
