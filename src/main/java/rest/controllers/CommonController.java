package rest.controllers;

import httpHandlers.HTTPAbstractHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.mainServlet.CustomMediaType;
import server.base.config.ServiceDispatcher;


import java.io.IOException;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@RestController
public class CommonController {

    private final Logger LOGGER = LogManager.getLogger(getClass());
    
    @RequestMapping("/22")
    public String home() {
        return "Welcome to home!";
    }

    @RequestMapping(value = "/anyTypeClient")
    public String readData( HttpServletRequest request)  {


         List<String> clientParams = Collections.list( request.getParameterNames());
         for( String param : clientParams){
             LOGGER.debug(" param = {}  value = {}", param, request.getParameter(param));
         }

        String contentType=  request.getContentType();
        LOGGER.debug(" content type = {}", contentType);

        if(contentType==null)  {
           return "The content can't be empty";
        }
            CustomMediaType mediaType = CustomMediaType.valueOf(contentType);
            HTTPAbstractHandler handler = ServiceDispatcher.getInstance().getService(mediaType);
        try {
            handler.proceed(request);
            return "Success";
        } catch (IOException e) {
            e.fillInStackTrace();
            return HttpStatus.BAD_REQUEST.getReasonPhrase();
        }

    }
}
