package com.dokong.board.web.dto.userdto;

import com.dokong.board.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowUserDto {

    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private String userRole;
    private List<FindCouponNameDto> coupons;


    public static ShowUserDto of(User user) {
        return ShowUserDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userRole(user.getUserRole().getUserRoleDescription())
                .coupons(user.getCoupons().stream()
                        .map(c -> new FindCouponNameDto(c.getCouponName(), c.getCouponRate()))
                        .collect(Collectors.toList()))
                .build();
    }

//    @QueryProjection
//    public ShowUserDto(String username, String name, String phoneNumber, String email) {
//        this.username = username;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//    }

    @Getter
    public static class FindCouponNameDto{
        private String couponName;
        private int couponRate;

        public FindCouponNameDto(String couponName, int couponRate) {
            this.couponName = couponName;
            this.couponRate = couponRate;
        }
    }
}
