package com.jsc.fanCM.service;

import com.jsc.fanCM.dao.BoardRepository;
import com.jsc.fanCM.domain.Article;
import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.article.ArticleListDTO;
import com.jsc.fanCM.dto.board.BoardDTO;
import com.jsc.fanCM.dto.board.BoardListDTO;
import com.jsc.fanCM.dto.board.BoardModifyForm;
import com.jsc.fanCM.dto.board.BoardSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardListDTO> getBoardsList() {

        List<BoardListDTO> boardListDTOList = new ArrayList<>();

        List<Board> boardList = boardRepository.findAll();

        for(Board board : boardList) {
            BoardListDTO boardListDTO = new BoardListDTO(board);
            boardListDTOList.add(boardListDTO);
        }
        return boardListDTOList;
    }

    // DB 저장/수정/삭제시 필요
    @Transactional
    public void save(BoardSaveForm boardSaveForm, Member member){
        Board board = Board.createBoard(
                boardSaveForm.getName(),
                boardSaveForm.getDetail(),
                member
        );
        boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    public Board getBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );
        return boardOptional.get();
    }

    public BoardDTO getBoardDetail(Long id) {
        Optional<Board> boardOptional = findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );
        Board findBoard = boardOptional.get();
        List<ArticleListDTO> articleList = new ArrayList<>();
        List<Article> articles = findBoard.getArticles();
        for (Article article : articles) {
            ArticleListDTO articleListDTO = new ArticleListDTO(article);
            articleList.add(articleListDTO);
    }
        return new BoardDTO(findBoard,articleList);
    }

    @Transactional
    public Long modify(Long id,BoardModifyForm boardModifyForm) throws NoSuchElementException{
        Optional<Board> boardOptional = boardRepository.findById(id);

        boardOptional.orElseThrow(
            () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );
        Board board = boardOptional.get();
        board.modifyBoard(
                boardModifyForm.getName(),
                boardModifyForm.getDetail()
        );
        return board.getId();
    }


    @Transactional
    public String delete(Long id){
        Optional<Board> boardOptional = findById(id);
        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        Board findBoard = boardOptional.get();

        boardRepository.delete(findBoard);
        return "redirect:/";
    }
}
