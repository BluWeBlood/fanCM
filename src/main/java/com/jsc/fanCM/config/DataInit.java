package com.jsc.fanCM.config;

import com.jsc.fanCM.dao.ArticleRepository;
import com.jsc.fanCM.dao.BoardRepository;
import com.jsc.fanCM.dao.MemberRepository;
import com.jsc.fanCM.domain.Article;
import com.jsc.fanCM.domain.Board;
import com.jsc.fanCM.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.jar.JarEntry;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final InitService initService;
    // 서버를 다시 켰을때 먼저 읽어들임
    @PostConstruct
    public void init() {
        initService.initAdmin();
        initService.initMember();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final MemberRepository memberRepository;
        private final BoardRepository boardRepository;
        private final ArticleRepository articleRepository;

        public void initAdmin() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            Member admin = Member.createMember(
                    "admin",
                    bCryptPasswordEncoder.encode("admin"),
                    "관리자",
                    "관리자",
                    "admin@admin.com",
                    Role.ADMIN
            );
            memberRepository.save(admin);

            for(int i=1;i<=3;i++) {
                Board board = Board.createBoard(
                        "게시판" + i,
                        "게시판" + i,
                        admin
                );
                boardRepository.save(board);
            }
        }

        public void initMember() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            List<Board> boardList = boardRepository.findAll();

            for(int i=1;i<=5;i++) {
                Member member = Member.createMember(
                        "user" + i,
                        bCryptPasswordEncoder.encode("user" + i),
                        "user" + i,
                        "user" + i,
                        "user" + i + "@user.com",
                        Role.MEMBER
                );
                memberRepository.save(member);

                for(int j=1;j<=3; j++) {
                    for(int k=1; k<=3; k++) {
                        Article article = Article.createArticle(
                                "제목" + k,
                                "내용" + k
                        );
                        article.setMember(member);
                        article.setBoard(boardList.get(j -1));

                        articleRepository.save(article);
                    }
                }

            }

        }
    }

}
