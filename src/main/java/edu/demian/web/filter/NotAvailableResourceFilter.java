package edu.demian.web.filter;

import com.google.common.base.Strings;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NotAvailableResourceFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        List<String> uriParts = Arrays.stream(uri.split("/"))
                .filter(part -> !Strings.isNullOrEmpty(part))
                .collect(Collectors.toList());

        if (uriParts.isEmpty()) {
            response.sendRedirect("/jsp/home");
            return;
        } else if ("favicon.ico".equals(uriParts.get(0))) {
            //do nothing
        }  else if (!"jsp".equals(uriParts.get(0))) {
            response.sendRedirect("/jsp/notFound");
            return;
        }

        chain.doFilter(request, response);
    }
}
