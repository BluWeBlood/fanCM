package com.jsc.fanCM.controller;

import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.domain.Member;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name = "id")Long id, Model model) {
        try {
            BoardDTO boardDetail = boardService.getBoardDetail(id);
            model.addAttribute("board",boardDetail);
        } catch (Exception e) {
            return "redirect:/";
        }
        return "adm/board/detail";
    }
}
