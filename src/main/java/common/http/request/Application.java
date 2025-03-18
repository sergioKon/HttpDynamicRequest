package common.http.request;


import httpHandlers.HTTPAbstractHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import rest.mainServlet.CustomMediaType;
import rest.mainServlet.SimpleHttpServer;
import server.base.config.ServiceDispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Application {
	private static final Logger  LOGGER = LogManager.getLogger(Application.class);
	private String address;
	private Integer port;
	MimeHttpHandler mimeHttpHandler;
	public static void main(String[] args) throws IOException {
        Application app = new Application();
		app.readConfig();
		app.setHandlers();
		app.serverStart();

	}
	public void readConfig() throws IOException {
		try (InputStream input = Application.class.getClassLoader().getResourceAsStream("config.yaml")) {
			if (input == null) {
				throw new FileNotFoundException("YAML file not found!");
			}
			Yaml yaml = new Yaml();
			Map<String, Object> config = yaml.load(input);
			Map<String, Object> params = (Map<String, Object>) config.get("serverData");
			address = params.get("address").toString();
			port = (Integer) params.get("port");

			LOGGER.info("server: {} port{}", address, port);
		}

	}
	public void setHandlers() {
		ServiceDispatcher serviceDispatcher = ServiceDispatcher.getInstance();
		Map<CustomMediaType, HTTPAbstractHandler> handlersMap = serviceDispatcher.getAll();
		handlersMap.forEach((key, value) -> {
            LOGGER.info("Media type  = {}, HttpHandler =  {}", key, value.getClass().getName());
		});
		mimeHttpHandler =  new MimeHttpHandler(handlersMap);
	}
	public void serverStart() throws IOException {
		SimpleHttpServer simpleHttpServer = new SimpleHttpServer(address, port);
		simpleHttpServer.start(mimeHttpHandler);
	}
}

