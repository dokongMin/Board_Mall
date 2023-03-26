package com.dokong.board.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClockService {

    private final Clock clock;

    public LocalDateTime find(final Long id) {
        LocalDateTime now = LocalDateTime.now(clock);
        return now;
    }
}
