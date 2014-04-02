package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				errorHandler(deadLetterChannel("file:erro").
						maximumRedeliveries(2).redeliveryDelay(2000));

				from("file:itens?noop=true").
					log("${body}").
					bean(TransformerBean.class, "transform").
					setHeader("tokenUser", constant("1234abc")).
					split().xpath("/itens/item/livro/codigo/text()[1]").
					unmarshal().string().
				to("velocity:soap_request.vm").
					log("${body}");

			}
		});

		context.start();

		Thread.sleep(20 * 1000);

		context.stop();
	}

}
