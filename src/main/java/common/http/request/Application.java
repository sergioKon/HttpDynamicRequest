package common.http.request;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import rest.mainServlet.SimpleHttpServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Application {

	public static void main(String[] args) throws IOException {
		Yaml yaml = new Yaml();
		String address;
		Integer port;
		Logger  LOGGER = LogManager.getLogger(Application.class);
		try (InputStream input = Application.class.getClassLoader().getResourceAsStream("config.yaml")) {
			if (input == null) {
				throw new FileNotFoundException("YAML file not found!");
			}
			Map<String, Object> config = yaml.load(input);
		//	address =  config.get("address").toString();
	//		port = Integer.getInteger( config.get("port").toString());
      //      LOGGER.info("server: {} port{}", address, port );
		}
		SimpleHttpServer simpleHttpServer = new SimpleHttpServer(8080,  0);
		simpleHttpServer.start();
	}
}

