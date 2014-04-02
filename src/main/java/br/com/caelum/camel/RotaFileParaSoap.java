package br.com.caelum.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;

public class RotaFileParaSoap extends RouteBuilder {

		@Override
		public void configure() throws Exception {

			//tratamento da excecao de validacao
			onException(ValidationException.class).
				maximumRedeliveries(2).
					redeliveryDelay(2000).
				log("Enviando mensgagem para file:erro").
					handled(true).
			to("file:erro");
			

			//rota que le da pasta itens, valida e extrai informacoes com xpath
			from("file:itens?noop=true").
				routeId("rota inicial(file:itens)").
				bean(ValidacaoBean.class).
				split().xpath("/itens/item/livro/codigo/text()[1]").
				unmarshal().string().
				log("Corpo da msg: ${body}").
			to("direct:gera-soap");
				
			
			
			//rota que gera uma mensagem soap
			from("direct:gera-soap").
				routeId("rota gera soap").
				setHeader("tokenAutorizacao", constant("1234abc")).
			to("velocity:soap_request.vm").
				log("Corpo da msg: ${body}").
			to("direct:envia-request");
			
			
			
			//rota que envia request soap
			from("direct:envia-request").
				log("enviando mensagem soap").
				setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST)).
				setHeader(Exchange.CONTENT_TYPE, constant("text/xml")).
			to("http4://localhost:8080/EstoqueWS");
		}
}
