package edu.demian.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class StaticFilter extends HttpFilter {

    private static final String PATH_TO_SOURCE_FILES_DIR = "../../src/main/webapp";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ServletContext context = getServletContext();
        String uri = request.getRequestURI();

        String realPath = context.getRealPath("/");
        File file = new File(new File(realPath, PATH_TO_SOURCE_FILES_DIR), uri);

        if (!file.isFile()) {
            file = new File(context.getRealPath(uri));
        }

        if (file.isFile()) {
            String mimeType = context.getMimeType(file.getCanonicalPath());
            response.setContentType(mimeType);
            response.setContentLengthLong(file.length());
            Files.copy(file.toPath(), response.getOutputStream());
        } else {
            chain.doFilter(request, response);
        }

    }
}
