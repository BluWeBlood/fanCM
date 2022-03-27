package com.jsc.fanCM.service;

import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.member.FindPasswordForm;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "gpfla3690@gmail.com";

    public void sendMail(FindPasswordForm findPasswordForm){

        Member findMember = memberService.findByLoginId(findPasswordForm.getLoginId());

        String uuid = UUID.randomUUID().toString();
        String tempPw = uuid.substring(0, 5);

        memberService.changeTempPw(tempPw, findMember);

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

            mailHelper.setFrom(FROM_ADDRESS);
            mailHelper.setTo(findPasswordForm.getEmail());
            mailHelper.setSubject("임시 비밀번호를 보내드립니다.");
            mailHelper.setText(findMember.getNickname() + "님의 임시 비밀번호는" + tempPw + "입니다.\r\n 로그인 후 비밀번호를 변경해 주시기 바랍니다.");

            javaMailSender.send(mail);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}