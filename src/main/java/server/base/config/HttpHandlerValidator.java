package server.base.config;

public interface HttpHandlerValidator {
    boolean isValidHandler(Class<?> clazz);
}

