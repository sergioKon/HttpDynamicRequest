package server.base.config;

import httpHandlers.HTTPAbstractHandler;
import lombok.SneakyThrows;
import rest.mainServlet.CustomMediaType;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceDispatcher {

    static class Singleton {
        private static  final ServiceDispatcher  instance = new ServiceDispatcher();
    }

     private final Map<CustomMediaType, HTTPAbstractHandler>  mapServices = new HashMap<>();
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
        collect(fileHandlers);
    }

    @SneakyThrows
    private void collect(File[] fileHandlers)  {
        for (File file: fileHandlers) {
            if(file.isDirectory()) {
                collect(Objects.requireNonNull(file.listFiles()));
            }
            if (!file.getName().endsWith(".class")) {
                continue;
            }

            int iStart= file.getAbsolutePath().indexOf(baseHandlerPackage);
            String filePath = file.getCanonicalPath().substring(iStart);
            filePath= filePath.replace(".class","").replace(File.separator,".");
            Class<?> clazz = Class.forName(filePath);
            boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
            if(isAbstract && !clazz.isInterface() && !clazz.isEnum()) {
                continue;
            }
            HTTPAbstractHandler httpHandler = (HTTPAbstractHandler) clazz.getDeclaredConstructor().newInstance();
            mapServices.put(httpHandler.getMediaType(), httpHandler);
         }
    }

    public HTTPAbstractHandler getService(CustomMediaType key){
         return mapServices.get(key);
      }
    public Map<CustomMediaType, HTTPAbstractHandler>  getAll(){
        return mapServices;
    }

}
