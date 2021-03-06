package com.jsc.fanCM.controller;

import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.member.CheckStatus;
import com.jsc.fanCM.dto.member.MemberSaveForm;
import com.jsc.fanCM.dto.member.MemberModifyForm;
import com.jsc.fanCM.dto.member.MemberLoginForm;
import com.jsc.fanCM.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping("/members/check/id")
    @ResponseBody
    public CheckStatus checkLoginId(@RequestParam String loginId){
        boolean isExists = memberService.isDupleLoginId(loginId);

        CheckStatus checkStatus = new CheckStatus(isExists);
        return checkStatus;
    }

    @RequestMapping("/members/check/nickname")
    @ResponseBody
    public CheckStatus checkNickname(@RequestParam String nickname) {
        boolean isExists = memberService.isDupleNickname(nickname);

        CheckStatus checkStatus = new CheckStatus(isExists);
        return checkStatus;
    }

    @RequestMapping("/members/check/email")
    @ResponseBody
    public CheckStatus checkEmail(@RequestParam String email) {
        boolean isExists = memberService.isDupleEmail(email);

        CheckStatus checkStatus = new CheckStatus(isExists);
        return checkStatus;
    }


    @GetMapping("/members/join")
    public String showJoin(Model model) {
        model.addAttribute("memberSaveForm", new MemberSaveForm());

        return "usr/member/join";
    }

    /**
     * 회원가입
     * @param memberSaveForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors() ) {
            return "usr/member/join";
        }

        try {
            memberService.save(memberSaveForm);
        } catch (Exception e) {
            model.addAttribute("err_msg",e.getMessage());

            return "usr/member/join";
        }
        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String showLogin(Model model){
        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

    @GetMapping("/members/modify/{id}")
    public String showModify(@PathVariable(name="id")Long id, Model model, Principal principal){

        // Member findMember = memberService.findByLoginId(principal.getName());
        Member findMember = memberService.findById(id);

        if( !findMember.getLoginId().equals(principal.getName()) ) {
            return "redirect:/";
        }

        //model.addAttribute("member", findMember);
        model.addAttribute("memberModifyForm", new MemberModifyForm(
                findMember
        ));

        return "usr/member/modify";
    }

    @PostMapping("/members/modify/{id}")
    public String doModify(@PathVariable(name="id")Long id, @Validated MemberModifyForm memberModifyForm, BindingResult bindingResult , Principal principal, Model model){

        if(bindingResult.hasErrors()){
            return "usr/member/modify";
        }

        Member findMember = memberService.findById(id);

        if (!findMember.getLoginId().equals(principal.getName()) ) {
            return "redirect:/";
        }

        try {
            memberService.modifyMember(memberModifyForm, principal.getName());

        }catch (Exception e){
            model.addAttribute("err_msg", e.getMessage());
            model.addAttribute("memberModifyForm", new MemberModifyForm(findMember));
            return "usr/member/modify";
        }
        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String doLogout(Principal principal, Model model) {
        String name = "";
        if(principal != null) {

        }
        return "redirect:/";
    }

    @GetMapping("/members/find/pw")
    public String showFindPw(){
        return "usr/member/findpw";
    }
}
