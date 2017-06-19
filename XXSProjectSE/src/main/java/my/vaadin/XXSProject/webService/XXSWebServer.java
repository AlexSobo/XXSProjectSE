package my.vaadin.XXSProject.webService;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class XXSWebServer {

	public static void main(String[] args) {
		HttpServer server = null;
		try {
			server = HttpServerFactory.create("http://localhost:8081/rest");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("Eingegebene Daten invalide für Webservice");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Webservice konnte nicht gestartet werden");
		}
		server.start();
	}

}
