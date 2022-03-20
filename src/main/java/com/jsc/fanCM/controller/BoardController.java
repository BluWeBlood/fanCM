package com.jsc.fanCM.controller;

import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.article.ArticleListDTO;
import com.jsc.fanCM.dto.board.BoardDTO;
import com.jsc.fanCM.dto.board.BoardModifyForm;
import com.jsc.fanCM.dto.board.BoardSaveForm;
import com.jsc.fanCM.service.BoardService;
import com.jsc.fanCM.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    // 게시판 리스트
    @GetMapping("/boards")
    public String showBoard(Model model) {
        List<Board> boardlist = boardService.findAll();

        model.addAttribute("boardList", boardlist);

        return "adm/board/list";
    }

    @GetMapping("/boards/{id}") // https://localhost:8085/boards/1?page=7
    public String showBoardDetail(@PathVariable(name = "id")Long id, Model model, @RequestParam(name="page", defaultValue = "1") int page) {

        int size = 10;

        try {
            BoardDTO boardDetail = boardService.getBoardDetail(id);

            List<ArticleListDTO> articleListDTO = boardDetail.getArticleListDTO();

            Collections.reverse(articleListDTO); //reverse
            // 0, 10, 20, ...
            int startIndex = (page - 1) * size;
            // 9, 19, 29, ... -> 15 -> 1.5(총 게시글 개수 / 10(size)) -> 올림
            int lastIndex = startIndex + 9;

            int lastPage = (int)Math.ceil(articleListDTO.size()/(double)size);

            if( page == lastPage) {
                lastIndex = articleListDTO.size();
            }else if( page > lastPage ){
                return "redirect:/";
            }else {
                lastIndex += 1;
            }

            // 페이지 가르기
            List<ArticleListDTO> articlePage = articleListDTO.subList(startIndex, lastIndex);

            model.addAttribute("board",boardDetail);
            model.addAttribute("articles",articlePage);
            model.addAttribute("maxPage",lastPage);
            model.addAttribute("currentPage",page);

            model.addAttribute("board",boardDetail);
        } catch (Exception e) {
            return "redirect:/";
        }
        return "adm/board/detail";
    }
}
