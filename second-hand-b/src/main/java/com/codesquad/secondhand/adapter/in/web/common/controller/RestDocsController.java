package com.codesquad.secondhand.adapter.in.web.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    @GetMapping("/api/docs")
    public String getRestDocs() {
        return "/docs/index.html";
    }
}
