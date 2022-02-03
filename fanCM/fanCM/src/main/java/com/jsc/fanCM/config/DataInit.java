package com.jsc.fanCM.config;

import com.jsc.fanCM.dao.MemberRepository;
import com.jsc.fanCM.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
    @Service
    @RequiredArgsConstructor
    static class InitService {
        private final MemberRepository memberRepository;

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
        }

        public void initMember() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
            }

        }
    }

}
