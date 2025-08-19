package com.example.demo.controller;

import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewNameConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupCompletionController {

    /**
     * 画面の初期表示を行います。
     *
     * @return ユーザー登録情報確認結果画面
     */
    @GetMapping(UrlConst.SIGNUP_COMPLETION)
    public String View(Model model) {
        return ViewNameConst.SIGNUP_COMPLETION;
    }

}
