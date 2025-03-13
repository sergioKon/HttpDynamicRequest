package server.base.config;

import httpHandlers.HTTPAbstractHandler;
import org.springframework.http.MediaType;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ServiceDispatcher {

    static class Singleton {
        private static  final ServiceDispatcher  instance = new ServiceDispatcher();
    }

     private final Map<MediaType, HTTPAbstractHandler>  mapServices = new HashMap<>();
     protected String baseHandlerPackage="httpHandlers";


    public static ServiceDispatcher getInstance() {
        return Singleton.instance;
    }


    private ServiceDispatcher()  {
        try {
            setAllTypeHandlers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    protected void  setAllTypeHandlers() throws Exception{

           Class<?> baseClassPath= HTTPAbstractHandler.class;
           URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
           String relativeLocation=  baseClassPath.getPackageName();
           String path= rootLocation.getPath()+relativeLocation;

           File[] fileHandlers= new File(path).listFiles();
           if(fileHandlers ==null)  {
               throw  new IllegalAccessException(" folder for file is empty ");
           }
           for (File file: fileHandlers) {

               Class<?> clazz = Class.forName(baseHandlerPackage + '.' + file.getName().substring(0, file.getName().length() - 6));
               if(clazz.getName().contains("Abstract")) {
                   continue;
               }
               HTTPAbstractHandler httpHandler = (HTTPAbstractHandler) clazz.getDeclaredConstructor().newInstance();
               mapServices.put(httpHandler.getMediaType(), httpHandler);
            }
        }

      public HTTPAbstractHandler getService(MediaType key){
         return mapServices.get(key);
      }
}
