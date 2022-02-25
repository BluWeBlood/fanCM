package com.jsc.fanCM.service;

import com.jsc.fanCM.dao.ArticleRepository;
import com.jsc.fanCM.domain.Article;
import com.jsc.fanCM.dto.article.ArticleListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdmArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleListDTO> getArticleList() {

        List<ArticleListDTO> articleListDTOList = new ArrayList<>();

        List<Article> articleList = articleRepository.findAll();

        for(Article article : articleList) {
            ArticleListDTO articleListDTO = new ArticleListDTO(article);
            articleListDTOList.add(articleListDTO);
        }

        return articleListDTOList;
    }
}
