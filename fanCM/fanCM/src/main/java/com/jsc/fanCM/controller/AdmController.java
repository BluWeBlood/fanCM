package com.jsc.fanCM.controller;

import com.jsc.fanCM.service.AdmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmController {
    private final AdmService admService;

    @GetMapping("/page")
    public String showAdminPage(){
        return "adm/general/main";
    }

    @GetMapping("/members")
    public String showManageMember(){
        return "adm/member/main";
    }

    @GetMapping("/articles")
    public String showManageArticle() {
        return "adm/article/main";
    }

    @GetMapping("/boards")
    public String showManageBoard() {
        return "adm/board/main";
    }
}
