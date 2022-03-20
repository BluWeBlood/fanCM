package com.jsc.fanCM.dto.member;

import com.jsc.fanCM.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDTO {
    private Long memberId;

    private String loginId;
    private String name;
    private String nickname;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public MemberDTO(Member member) {
        this.memberId = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();

        this.regDate = member.getRegDate();
        this.updateDate = member.getUpdateDate();
    }
}
