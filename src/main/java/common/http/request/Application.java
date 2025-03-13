package common.http.request;

import httpHandlers.HTTPAbstractHandler;
import rest.mainServlet.CustomMediaType;
import rest.mainServlet.SimpleHttpServer;
import server.base.config.ServiceDispatcher;

import java.io.IOException;
import java.util.Map;

public class Application {

	public static void main(String[] args) throws IOException {

		SimpleHttpServer simpleHttpServer = new SimpleHttpServer(8080, 0);

		ServiceDispatcher serviceDispatcher = ServiceDispatcher.getInstance();
		Map<CustomMediaType, HTTPAbstractHandler> handlers = serviceDispatcher.getAll();
		handlers.forEach((key, value) -> {
			System.out.println("Media type  = " + key + ", HttpHandler =  " + value.getClass().getName());
		});


		MimeHandler mimeHandler =  new MimeHandler();
		mimeHandler.setHttpHandlers(handlers);

		simpleHttpServer.setMimeHandler(mimeHandler);
		simpleHttpServer.start();
	}
}

