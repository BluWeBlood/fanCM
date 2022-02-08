package com.jsc.fanCM.dto.member;

import com.jsc.fanCM.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberModifyForm {

    private Long id;

    private String loginId;

    private String loginPw;

    private String name;

    private String nickname;

    private String email;

    public MemberModifyForm(Member findMember) {
        this.id = findMember.getId();

        this.loginId = findMember.getLoginId();
        this.loginPw = findMember.getLoginPw();

        this.name = findMember.getName();
        this.nickname = findMember.getNickname();
        this.email = findMember.getEmail();
    }
}