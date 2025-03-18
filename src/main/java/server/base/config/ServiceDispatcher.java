package server.base.config;

import httpHandlers.HTTPAbstractHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.mainServlet.CustomMediaType;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceDispatcher implements HttpHandlerValidator {
     Logger LOGGER = LogManager.getLogger(ServiceDispatcher.class);

    @Override
    public boolean isValidHandler(Class<?> clazz) {
        {
            if (clazz.getSuperclass() != HTTPAbstractHandler.class) {
                return false;
            }
            boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
            return !isAbstract || clazz.isInterface() || clazz.isEnum();
        }
    }

    static class Singleton {
        private static  final ServiceDispatcher  instance = new ServiceDispatcher();
    }

    private final Map<CustomMediaType, HTTPAbstractHandler>  mapServices = new HashMap<>();
    protected String baseHandlerPackage="httpHandlers";

    public static ServiceDispatcher getInstance() {
        return Singleton.instance;
    }


    private ServiceDispatcher()  {
            setAllTypeHandlers();
    }

    protected void  setAllTypeHandlers() {

           Class<?> baseClassPath= HTTPAbstractHandler.class;
           URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
           String relativeLocation=  baseClassPath.getPackageName();
           String path= rootLocation.getPath()+relativeLocation;

           File[] fileHandlers= new File(path).listFiles();
           if(fileHandlers ==null)  {
               throw  new RuntimeException(" folder for file is empty ");
           }
           collect(fileHandlers);
    }


    private void collect(File[] fileHandlers)  {
        for (File file: fileHandlers) {
            if (file.isDirectory()) {
                collect(Objects.requireNonNull(file.listFiles()));
            }
            if (!file.getName().endsWith(".class")) {
                continue;
            }



            int iStart = file.getAbsolutePath().indexOf(baseHandlerPackage);
            String filePath;
            try {
               filePath = file.getCanonicalPath().substring(iStart);
               filePath = filePath.replace(".class", "").replace(File.separator, ".");
               Class<?> clazz= Class.forName(filePath);
               if(!isValidHandler(clazz)) {
                   continue;
               }
                HTTPAbstractHandler httpHandler = (HTTPAbstractHandler) clazz.getDeclaredConstructor().newInstance();
                mapServices.put(httpHandler.getMediaType(), httpHandler);
                
            } catch(Exception e ){
                   LOGGER.error(e);
                }
        }
    }
    public HTTPAbstractHandler getService(CustomMediaType key){
         return mapServices.get(key);
      }
    public Map<CustomMediaType, HTTPAbstractHandler>  getAll(){
        return mapServices;
    }

}
