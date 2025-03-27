package  rest.mainServlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/dataReader")

public class ServletReader  extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextType;
        if(request.getContentType()==null) {
            contextType="null";
        }
        else {
            contextType= request.getContentType();
        }
        response.getWriter().write(contextType);
    }
}

