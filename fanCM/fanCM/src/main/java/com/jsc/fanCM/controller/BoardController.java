package com.jsc.fanCM.controller;

import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.dto.board.BoardDTO;
import com.jsc.fanCM.dto.board.BoardModifyForm;
import com.jsc.fanCM.dto.board.BoardSaveForm;
import com.jsc.fanCM.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards/add")
    public String showAddBoard(Model model) {
        model.addAttribute("boardSaveForm",new BoardSaveForm());

        return "adm/board/add";
    }

    @PostMapping("/boards/add")
    public String doAddBoard(BoardSaveForm boardSaveForm) {

        boardService.save(boardSaveForm);

        return "redirect:/adm/boards";
    }

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

    @GetMapping("/boards/modify/{id}")
    public String showModifyBoard(Model model){
        model.addAttribute("BoardModifyForm",new BoardModifyForm());

        return "adm/board/modify";
    }

    @PostMapping("/boards/modify/{id}")
    public String modifyBoard(BoardModifyForm boardModifyForm) {
        try {
            boardService.modify(boardModifyForm);
        } catch (Exception e) {
            return "adm/board/modify";
        }
        return "redirect:/";
    }

    //삭제
    @GetMapping("/boards/delete/{id}")
    public String doDeleteBoard(@PathVariable(name = "id") Long id){

        try {
            boardService.delete(id);
            return "adm/board/list";
        } catch (Exception e){
            return "adm/board/list";
        }
    }
}
