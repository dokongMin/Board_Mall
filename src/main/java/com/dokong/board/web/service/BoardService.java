package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.FiveBoardPostPerDay;
import com.dokong.board.repository.board.BoardRepository;
import com.dokong.board.web.dto.boarddto.*;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.service.redis.RedisBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    private final CouponService couponService;
    private final RedisBoardService redisBoardService;

    @Transactional

    public SaveBoardRespDto saveBoard(SaveBoardReqDto boardReqDto) {
        User user = userService.findById(boardReqDto.getUserId());
        fiveBoardPostCheck();
        Board board = boardRepository.save(boardReqDto.toEntity());
        boardPostCouponIssue(user.getId());
        board.writeBoard(user);
        return SaveBoardRespDto.of(board);
    }

    public FindBoardDto addViewCountInRedis(Long boardId, String username) {
        Board board = findById(boardId);
        if (redisBoardService.checkDuplicateRequest(username, boardId)) {
            throw new IllegalStateException("해당 유저는 이미 조회수를 증가시켰습니다.");
        }
        redisBoardService.addViewCountInRedis(board);
        return FindBoardDto.of(board);
    }

    @Transactional
    public long addViewCount(Long boardId, long viewCount) {
        Board board = findById(boardId);
        board.addViewCount(viewCount);
        return board.getViewCount();
    }



    private void fiveBoardPostCheck() {
        List<LocalDateTime> byCreatedDate = boardRepository.findByCreatedDate();
        LocalDateTime now = LocalDateTime.now();
        long count = byCreatedDate.stream()
                .filter(b -> ChronoUnit.DAYS.between(now, b) == 0)
                .count();
        if (count == 5) {
            throw new FiveBoardPostPerDay("게시글은 하루에 5개만 작성 가능합니다.");
        }
    }

    private void boardPostCouponIssue(Long userId) {
        int count = boardRepository.findByUserId(userId) + 1;
        System.out.println("count = " + count);
        if (count > 0 && count % 5 == 0) {
            AddCouponDto addCouponDto = AddCouponDto.builder()
                    .couponName("게시글 " + count + " 개 작성 쿠폰")
                    .couponRate(10 + (count / 5))
                    .minCouponPrice(10000)
                    .couponDetail("게시글을 " + count + " 개 작성할 때마다 드리는 쿠폰입니다.")
                    .userId(userId)
                    .build();
            couponService.addCouponByBoard(addCouponDto);
        }
    }

    @Transactional
    public UpdateBoardDto updateBoard(Long id, UpdateBoardDto boardReqDto) {
        Board board = findById(id);
        board.updateBoard(boardReqDto.getBoardTitle(), boardReqDto.getBoardContent());
        return UpdateBoardDto.of(board);
    }

    @Transactional
    public DeleteBoardRespDto deleteBoard(Long id) {
        Board board = findById(id);
        board.deleteBoard();
        return DeleteBoardRespDto.of(board);
    }

    public List<FindBoardDto> findAll() {
        return boardRepository.findAll().stream()
                .map(b -> FindBoardDto.of(b))
                .collect(Collectors.toList());
    }

    // todo -> findAllByBoardStatus 로직 이상.
    // 그냥 처음부터 CREATED 상태인 Board 를 찾으면 될 듯.
    public List<FindBoardDto> findAllByBoardStatusCreated() {
        return boardRepository.findAllByBoardStatusCreated().stream()
                .map(FindBoardDto::of)
                .collect(Collectors.toList());
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        });
    }
}
