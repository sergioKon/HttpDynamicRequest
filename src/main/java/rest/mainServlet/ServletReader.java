package  rest.mainServlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/dataReader")

public class ServletReader  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("Hello from Custom Servlet -- ! "+ "'"+ request.getContentType()+ "'");
    }
}

