package my.vaadin.XXSProject.webService;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class XXSWebServerConnector {
	public static String getSQLDateAsStringFromService() {
		WebResource service = Client.create().resource("http://localhost:8081/rest");
		return service.path("sqlTodayDateAsString").accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}
