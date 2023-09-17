package com.codesquad.secondhand.utils;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import io.restassured.specification.RequestSpecification;

public class RestDocsUtils {

    public static void 출력_필드_추가(String 출력_이름, RequestSpecification 요청_스펙) {
        요청_스펙.filter(document(출력_이름,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }
}
