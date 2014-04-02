package br.com.caelum.camel;

import org.apache.camel.Exchange;

public class ValidacaoBean {
	
	public void valida(Exchange exchange) {
		
		String mensagem =  exchange.getIn().getBody(String.class);
		
		if(!mensagem.contains("<codigo>")) {
			System.out.println("Deu problema na mensagam " + exchange.getExchangeId());
			throw new ValidationException("Codigo do item invalido");
		}
	}

}
