package httpHandlers;

import converters.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;


import java.io.IOException;
import java.util.List;



public class MultipartHandler extends HTTPAbstractHandler {

    @Override
    protected void InitMediaType() {
        super.mediaType= MediaType.MULTIPART_FORM_DATA;
    }

    @Override
    public void proceed(HttpServletRequest request) throws IOException {
           MultiValueMap<String, MultipartFile> clientFiles= ((MultipartRequest) request).getMultiFileMap();
           for(String  name : clientFiles.keySet()){
               List<MultipartFile> files=  clientFiles.get(name);
               for(MultipartFile file : files) {

                   if (mediaType == MediaType.APPLICATION_ATOM_XML) {
                       dataParser= new XMLParser();
                   } else if ( mediaType== MediaType.APPLICATION_JSON ) {
                       dataParser= new JSonParser();
                   } else if (mediaType== MediaType.IMAGE_GIF) {
                       dataParser= new GifParser();
                   }
                   else if (mediaType== MediaType.IMAGE_JPEG) {
                       dataParser= new GifParser();
                   }
                   else if (mediaType== MediaType.IMAGE_PNG) {
                       dataParser= new PgnParser();
                   }
                   else {
                       throw new IllegalStateException("Unexpected value: " + file.getContentType());
                   }
                  dataParser.saveToFile(file.getBytes());
               }
           }
    }
}
