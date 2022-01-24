package com.jsc.fanCM.service;

import com.jsc.fanCM.dao.ArticleRepository;
import com.jsc.fanCM.domain.Article;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.article.ArticleDTO;
import com.jsc.fanCM.dto.article.ArticleModifyForm;
import com.jsc.fanCM.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void save(ArticleSaveForm articleSaveForm, Member member){
        Article article = Article.createArticle(
            articleSaveForm.getTitle(),
            articleSaveForm.getBody()
        );
        article.setMember(member);

        articleRepository.save(article);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public Article getById(Long id) throws NoSuchElementException {
        Optional<Article> articleOptional = findById(id);

        articleOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        return articleOptional.get();
    }

    public ArticleDTO getArticle(Long id) {
        Article findArticle = getById(id);
        ArticleDTO articleDTO = new ArticleDTO(findArticle);

        return articleDTO;
    }

    @Transactional
    public void modifyArticle(ArticleModifyForm articleModifyForm,Long id) {

        Article findArticle = getById(id);
        findArticle.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getBody()
        );
    }

    public List<ArticleDTO> getArticleList() {
        List<Article> articleList = articleRepository.findAll();
        //빈 articlelist 생성후 for문으로 옮겨 return
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for (Article article:articleList) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }

    public void delete(Long id) {
        Article findArticle = getById(id);
        articleRepository.delete(findArticle);
    }
}
