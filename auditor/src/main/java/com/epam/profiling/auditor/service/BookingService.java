package com.epam.profiling.auditor.service;

import com.epam.profiling.common.CalculationService;
import com.epam.profiling.common.dto.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    private final CalculationService service;

    public BookingService(CalculationService service) {
        this.service = service;
    }

    public Booking acceptBooking(Booking booking) {
        LOG.info("Received the following booking: {}", booking.toString());
        return methodWhichLoadsMemory(service.highCpuUsageMethod(booking));
    }

    public Booking methodWhichLoadsMemory(Booking booking) {
        for (int i = 0; i < 50_000; i++) {
            new Booking(booking.getName(), booking.getFrom());
        }
        return booking;
    }
}
