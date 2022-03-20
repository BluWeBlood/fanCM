package com.jsc.fanCM.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String detail;

    private LocalDateTime regDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();

    //board 혼자만 가져오기
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Article> articles = new ArrayList<>();

    //생성 매소드
    public static Board createBoard(String name, String detail,Member member) {
        Board board = new Board();

        board.name = name;
        board.detail = detail;
        board.member = member;

        return board;
    }

    public void modifyBoard(String name, String detail) {
        this.name = name;
        this.detail = detail;
        this.updateDate = LocalDateTime.now();
    }
}
