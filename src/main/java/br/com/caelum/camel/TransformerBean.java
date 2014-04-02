package br.com.caelum.camel;

import org.apache.camel.Exchange;

public class TransformerBean {
	
	public void transform(Exchange exchange) {
		
		String mensagem =  exchange.getIn().getBody(String.class);
		System.out.println(exchange.getIn().getMessageId()); 
		
		if(mensagem.contains("XXX")) {
			throw new RuntimeException();
		}
	}

}
