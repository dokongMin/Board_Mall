package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.FiveBoardPostPerDay;
import com.dokong.board.repository.BoardRepository;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.boarddto.UpdateBoardDto;
import com.dokong.board.web.dto.boardlikedto.BoardDateDto;
import com.dokong.board.web.dto.coupondto.AddCouponDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    private final CouponService couponService;

    @Transactional
    public SaveBoardRespDto saveBoard(SaveBoardReqDto boardReqDto, SessionUserDto sessionUserDto) {
        User user = userService.findById(sessionUserDto.getId());
        fiveBoardPostCheck();
        boardPostCouponIssue(sessionUserDto);
        Board board = boardRepository.save(boardReqDto.toEntity());
        board.writeBoard(user);
        return SaveBoardRespDto.of(board);
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

    private void boardPostCouponIssue(SessionUserDto sessionUserDto) {
        int count = boardRepository.findByUserId(sessionUserDto.getId());
        if (count > 0 && count % 5 == 0) {
            AddCouponDto addCouponDto = AddCouponDto.builder()
                    .couponName("게시글 " + count + " 개 작성 쿠폰")
                    .couponRate(10 + (count / 5))
                    .minCouponPrice(10000)
                    .couponDetail("게시글을 "+ count +" 개 작성할 때마다 드리는 쿠폰입니다.")
                    .build();
            couponService.addCouponByBoard(addCouponDto, sessionUserDto);
        }
    }

    @Transactional
    public UpdateBoardDto updateBoard(Long id, UpdateBoardDto boardReqDto) {
        Board board = findById(id);
        board.updateBoard(boardReqDto.getBoardTitle(), boardReqDto.getBoardContent());
        return UpdateBoardDto.of(board);
    }
    
    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        });
    }
}
