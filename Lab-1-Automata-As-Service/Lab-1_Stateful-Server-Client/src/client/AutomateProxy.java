package client;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.proxy.WebResourceFactory;

import rest.Automate;
import rest.Resultat;
import rest.Session;

public class AutomateProxy implements Automate {
	private WebTarget cibleInitier;
	private WebTarget cibleAccepter;
	private WebTarget cibleClore;
	private MediaType typeContenu;
	
	public AutomateProxy(String uriBase, MediaType typeContenu){
		WebTarget cible = AppliCliente.clientJAXRS().target(uriBase);
		cibleInitier = cible.path("etat/initial");
		cibleAccepter = cible.path("etat/suivant");
		cibleClore = cible.path("fin");
		this.typeContenu = typeContenu;
	}
	
	@Override
	public Session initier() {
		Builder bld = cibleInitier.request(this.typeContenu);
		return 	bld.method("POST", Session.class); 
	}

	@Override
	public Resultat accepter(char x, Session id) {
		return this.cibleAccepter.path("/"+x).request(this.typeContenu).post(Entity.entity(id, this.typeContenu), Resultat.class);
	}

	@Override
	public void clore(Session id) {
		this.cibleClore.request(this.typeContenu).post(Entity.entity(id, this.typeContenu));
	}

}
