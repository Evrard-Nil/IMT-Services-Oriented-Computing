package client;

import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import rest.Automate;
import rest.Resultat;
import rest.Session;
import rest.jaxb.FournisseurTraduction;

public class AppliCliente {

	public static Client clientJAXRS() {
		ClientConfig config = new ClientConfig();
		config.register(LoggingFeature.class);
		config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO");
		config.register(FournisseurTraduction.class);
		config.register(JacksonFeature.class);
		return ClientBuilder.newClient(config);
	}

	public static void main(String[] args) {
		
		String adresse = "http://localhost:8080/StatefulServ/automate";
		System.out.println("*************");
		AutomateProxy automate = new AutomateProxy(adresse, MediaType.APPLICATION_JSON_TYPE);
		System.out.println("*************");
		AppliCliente.test(automate);
	}

	private static void test(Automate automate) {
		char[] mot = { 'a', 'b', 'a', 'a', 'a', 'b' };
		Session session = automate.initier();
		System.out.println(session.getNumero());
		Resultat res = null;
		for (char c : mot) {
			 res= automate.accepter(c,session);
		}
		System.out.println("Status: "+res.isValide());
	}

}
