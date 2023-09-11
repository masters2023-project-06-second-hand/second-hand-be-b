package com.codesquad.secondhand.adapter.in.web.config;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationResponseHandler {

    void handleSuccessfulAuthentication(HttpServletResponse response, String email) throws IOException;

    void handleNotRegisteredMember(HttpServletResponse response, String email) throws IOException;
}
