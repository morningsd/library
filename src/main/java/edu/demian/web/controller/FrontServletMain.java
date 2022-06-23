package edu.demian.web.controller;

import com.google.common.base.Strings;
import edu.demian.web.controller.page.jsp.HomePage;
import edu.demian.web.controller.page.jsp.NotFoundPage;
import edu.demian.web.exception.NotFoundException;
import edu.demian.web.exception.RedirectException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FrontServletMain extends HttpServlet {

    private static final String JSP_EXTENSION = ".jsp";
    private static final String PATH_TO_WEBINF_FILES_DIR = "WEB-INF";

    private static final String PAGE_PACKAGE = "page";
//    private static final String JSP_PACKAGE = "jsp";
    private static final String BASE_PACKAGE = FrontServletMain.class.getPackage().getName() + "." + PAGE_PACKAGE;

    private File webinfDirectory;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        webinfDirectory = new File(context.getRealPath(PATH_TO_WEBINF_FILES_DIR));
        isDirectory(webinfDirectory);
    }

    private void isDirectory(File jspDirectory) throws ServletException {
        if (!jspDirectory.isDirectory()) {
            throw new ServletException("Jsp directory is not a directory [jspDirectory=" + jspDirectory + "]");
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    private void process(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Route route = Route.newRoute(request);

        try {
            process(route, request, response);
        } catch (NotFoundException e) {
            try {
                process(Route.newNotFoundRoute(), request, response);
            } catch (NotFoundException ex) {
                // Impossible situation
                throw new ServletException(ex);
            }
        }
    }


    private void process(final Route route, final HttpServletRequest request, final HttpServletResponse response)  throws NotFoundException, ServletException, IOException {
        Class<?> pageClass;
        try {
            pageClass = Class.forName(route.getClassName());
        } catch (ClassNotFoundException e) {
            throw new NotFoundException();
        }

        Method method = null;
        for (Class<?> clazz = pageClass; method == null && clazz != null; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(route.getAction(), HttpServletRequest.class);
            } catch (NoSuchMethodException ignored) {
                // No operations
            }
        }

        if (method == null) {
            throw new NotFoundException();
        }

        Object page;
        try {
            page = pageClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ServletException("Can't create page [pageClass=" + pageClass + "]");
        }

        method.setAccessible(true);
        try {
            method.invoke(page, request);
        } catch (IllegalAccessException e) {
            throw new ServletException("Can't invoke method [pageClass=" + pageClass + ", method=" + method + "]", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();

            if (cause instanceof NotFoundException) {
                throw (NotFoundException) cause;
            } else if (cause instanceof RedirectException) {
                response.sendRedirect(((RedirectException) cause).getTarget());
                return;
            } else {
                throw new ServletException("Can't invoke method [pageClass=" + pageClass + ", method=" + method + "]", e);
            }
        }

        String pathToJspFile = getJspFile(pageClass);

        request.getRequestDispatcher("/WEB-INF/" + pathToJspFile).forward(request, response);
    }


    private String getJspFile(Class<?> pageClass) throws IOException, ServletException {
        String pageClassName = pageClass.getName();

        String classNamePart = pageClassName.substring(BASE_PACKAGE.length());

        List<String> classNameParts = Arrays.stream(classNamePart.split("\\."))
                .filter(part -> !Strings.isNullOrEmpty(part))
                .collect(Collectors.toList());

        String relativePathToJspFile = String.join("/", classNameParts) + JSP_EXTENSION;
        String pathToJspFile = webinfDirectory.getCanonicalPath() + "/" + relativePathToJspFile;

        File jspFile = new File(pathToJspFile);
        if (!jspFile.isFile()) {
            throw new ServletException("Can't find jsp file [jspFileName=" + relativePathToJspFile + "]");
        }

        return relativePathToJspFile;
    }


    private static class Route {

        private static final String PAGE = "Page";

        private static final String ACTION = "action";
        private static final String DEFAULT_ACTION = "action";

        private final String className;
        private final String action;

        private Route(String className, String action) {
            this.className = className;
            this.action = action;
        }

        private String getClassName() {
            return className;
        }

        private String getAction() {
            return action;
        }

        private static Route newNotFoundRoute() {
            return new Route(NotFoundPage.class.getName(), DEFAULT_ACTION);
        }

        private static Route newHomeRoute() {
            return new Route(HomePage.class.getName(), DEFAULT_ACTION);
        }

        private static Route newRoute(final HttpServletRequest request) {
            final String uri = request.getRequestURI();

            List<String> classNameParts = Arrays.stream(uri.split("/"))
                    .filter(part -> !Strings.isNullOrEmpty(part))
                    .collect(Collectors.toList());

            if (classNameParts.isEmpty()) {
                return newHomeRoute();
            }

            final StringBuilder simpleClassName = new StringBuilder(classNameParts.get(classNameParts.size() - 1));
            simpleClassName.setCharAt(0, Character.toUpperCase(simpleClassName.charAt(0)));
            simpleClassName.append(PAGE);
            classNameParts.set(classNameParts.size() - 1, simpleClassName.toString());

            final String className = BASE_PACKAGE + "." + String.join(".", classNameParts);

            String action = request.getParameter(ACTION);
            if (Strings.isNullOrEmpty(action)) {
                action = DEFAULT_ACTION;
            }

            return new Route(className, action);
        }
    }

}
