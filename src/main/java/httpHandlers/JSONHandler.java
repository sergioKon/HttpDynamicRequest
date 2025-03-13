package httpHandlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;

import java.io.IOException;


public class JSONHandler  extends  HTTPAbstractHandler{

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.APPLICATION_JSON;
    }

    @Override
    public void proceed(HttpServletRequest request) throws IOException {
        byte[] jSonStream= request.getInputStream().readAllBytes();
        String jsonData = new String(jSonStream);
    }
}
