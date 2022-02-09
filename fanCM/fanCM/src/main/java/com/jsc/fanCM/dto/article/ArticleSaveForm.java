package com.jsc.fanCM.dto.article;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ArticleSaveForm {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String body;

    private Long Board_id;

    public ArticleSaveForm(ArticleDTO articleDTO) {
        this.title = articleDTO.getTitle();
        this.body = articleDTO.getBody();
        this.Board_id = articleDTO.getBoardId();
    }
}
