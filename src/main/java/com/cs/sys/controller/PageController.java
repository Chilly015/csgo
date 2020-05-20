package com.cs.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/doIndexUI")
    public String doIndexUI(){
        return "starter";
    }

    @RequestMapping("/doPageUI")
    public String doPageUI(){
        return "common/page";
    }

    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }

/*    @RequestMapping("log/log_list")
    public String doLogUI(){
        return "sys/log_list";
    }

    @RequestMapping("menu/menu_list")
    public String doMenuUI(){ return  "sys/menu_list";}*/

    @RequestMapping("{page}/{ui}")
    public String page(@PathVariable String ui){
        return "sys/"+ui;
    }

}
