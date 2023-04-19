package com.dokong.board.web.scheduler;

import com.dokong.board.domain.board.Board;
import com.dokong.board.repository.board.BoardRepository;
import com.dokong.board.web.service.BoardService;
import com.dokong.board.web.service.redis.RedisBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewCountScheduler {

    private final RedisBoardService redisBoardService;
    private final BoardRepository boardRepository;
    private final BoardService boardService;


    @Scheduled(fixedDelay = 15000)
    private void sendVisitCount() {
        log.info("스케줄링 확인");
        List<Board> boards = boardRepository.findAll();
        boards.forEach(b -> {
            long viewCount = redisBoardService.sendViewCount(b);
            if (viewCount != 0) {
                boardService.addViewCount(b.getId(), viewCount);
            }
        });
    }
}
