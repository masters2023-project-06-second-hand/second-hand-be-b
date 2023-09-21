package com.codesquad.secondhand.common.restdocs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    @GetMapping("/api/docs")
    public String getRestDocs() {
        return "/docs/index.html";
    }
}
