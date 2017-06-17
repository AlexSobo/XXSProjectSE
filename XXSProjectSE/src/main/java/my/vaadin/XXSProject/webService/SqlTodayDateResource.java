package my.vaadin.XXSProject.webService;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sqlTodayDateAsString")
public class SqlTodayDateResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sqlTodayDateAsString() {
		Date javaDate = new Date();
		java.sql.Date sqlDate = new java.sql.Date(javaDate.getTime());
		return sqlDate.toString();
	}
}