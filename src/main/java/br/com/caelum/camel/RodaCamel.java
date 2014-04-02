package br.com.caelum.camel;

import javax.xml.ws.Endpoint;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import br.com.caelum.estoque.ws.EstoqueWS;

public class RodaCamel {

	private static final int DEZ_SEGUNDOS = 10 * 1000;

	public static void main(String[] args) throws Exception {

		Endpoint webService = sobeWebServiceParaTestarComCamel();

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RotaFileParaSoap());
		
		context.start();
		Thread.sleep(DEZ_SEGUNDOS); 
		
		context.stop();
		webService.stop();
	}

	private static Endpoint sobeWebServiceParaTestarComCamel() {
		return Endpoint.publish("http://localhost:8080/EstoqueWS", new EstoqueWS());
	}
	
	

}
