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
        return service.highCpuUsageMethod(booking);
    }
}
