package com.jsc.fanCM.controller;

import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.article.ArticleDTO;
import com.jsc.fanCM.dto.article.ArticleModifyForm;
import com.jsc.fanCM.dto.article.ArticleSaveForm;
import com.jsc.fanCM.dto.board.BoardDTO;
import com.jsc.fanCM.service.ArticleService;

import com.jsc.fanCM.service.BoardService;
import com.jsc.fanCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/boards/{id}/articles/write")
    public String showWrite(@PathVariable(name = "id")Long id, Model model) {
        // BoardDTO boardDetail = boardService.getBoardDetail(id);

        ArticleDTO findArticle = articleService.getArticle(id);

        model.addAttribute("boardName", findArticle.getBoardName());
        model.addAttribute("boardId", findArticle.getBoardId());

        model.addAttribute("articleSaveForm",new ArticleSaveForm());

        return "usr/article/write";
    }
    @PostMapping("/boards/{id}/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Model model, Principal principal,  @PathVariable(name = "id")Long id){
        if(bindingResult.hasErrors()) {
            ArticleDTO findArticle = articleService.getArticle(id);

            model.addAttribute("boardName", findArticle.getBoardName());
            model.addAttribute("boardId", findArticle.getBoardId());

            return "usr/article/write";
        }

        try {
            Member findMember = memberService.findByLoginId(principal.getName());
            Board findBoard = boardService.getBoard(articleSaveForm.getBoard_id());
            articleService.save(
                    articleSaveForm,
                    findMember,
                    findBoard
            );
        } catch (IllegalStateException e) {
            model.addAttribute("err_msg", e.getMessage());

            return "usr/article/write";
        }
        return "redirect:/articles";
    }

    @GetMapping("articles/modify/{id}")
    public String showModify(@PathVariable(name = "id")Long id, Model model) {
        try {
            ArticleDTO findarticle = articleService.getArticle(id);
            // Article article = articleService.getById(id);

            model.addAttribute("boarName", findarticle.getBoardName());
            model.addAttribute("boardId",findarticle.getBoardId());
            model.addAttribute("articleId",findarticle.getId());
            model.addAttribute("articleModifyForm", new ArticleModifyForm(findarticle));

            return "usr/article/modify";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @PostMapping("/articles/modify/{id}")
    public String doModify(@PathVariable(name="id")Long id,@Validated ArticleModifyForm articleModifyForm, BindingResult bindingResult, Principal principal , Model model) {

        if (bindingResult.hasErrors()) {
            ArticleDTO findarticle = articleService.getArticle(id);

            model.addAttribute("boarName", findarticle.getBoardName());
            model.addAttribute("boardId",findarticle.getBoardId());
            model.addAttribute("articleId",findarticle.getId());

            return "usr/article/modify";
        }

        try {
            ArticleDTO findArticle = articleService.getArticle(id);
            if(!findArticle.getMemberLoginId().equals(principal.getName())) {
                throw new IllegalStateException("잘못된 요청입니다");
            }

            Board findBoard = boardService.getBoard(id);
//            Board findBoard = boardService.getBoard(articleModifyForm.getBoard_id());

            articleService.modifyArticle(articleModifyForm,findBoard, id);
//            return "redirect:/articles/" + id;
            return "redirect:/boards/" + id;
        } catch (Exception e) {
            return "redirect:/articles/modify/" + id;
        }
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable(name="id")Long id,Principal principal){
        try {
            ArticleDTO article = articleService.getArticle(id);

            if (!article.getMemberLoginId().equals(principal.getName())) {
                return "redirect:/boards/" + id;
            }
            articleService.delete(id);
            return "redirect:/boards";
        } catch(Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/articles")
    public String showList(Model model) {
        List<ArticleDTO> articleList = articleService.getArticleList();

        ArticleDTO articleDTO = articleList.get(0);

        //html에 담기
        model.addAttribute("boardName", articleDTO.getBoardName());
        model.addAttribute("articleList",articleList);
        return "usr/article/list";
    }

    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable(name="id")Long id,Model model) {
        try{
            ArticleDTO findArticle = articleService.getArticle(id);
            model.addAttribute("article",findArticle);

            return "usr/article/detail";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
