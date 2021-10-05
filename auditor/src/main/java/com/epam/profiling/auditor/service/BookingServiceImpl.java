package com.epam.profiling.auditor.service;

import com.epam.profiling.common.dto.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public Booking acceptBooking(Booking booking) {
        LOG.info("Received the following booking: {}", booking.toString());
        return null;
    }
}
