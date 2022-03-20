package com.jsc.fanCM.dto.member;

import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.article.ArticleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageDTO {

    private String nickname;

    private List<ArticleDTO> articles;

    public MyPageDTO(Member member, List<ArticleDTO> articles) {
        this.nickname = member.getNickname();
        this.articles = articles;
    }

}
