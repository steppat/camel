package br.com.caelum.estoque.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="EstoqueWS", targetNamespace="http://caelum.com.br/estoquews/v1")
public class EstoqueWS {

	private List<String> REPO = new ArrayList<String>();

	public EstoqueWS() {
		//populando alguns dados
		REPO.add("SOA");
		REPO.add("TDD");
		REPO.add("RES");
		REPO.add("LOG");
		REPO.add("WEB");
		REPO.add("ARQ");
	}

	@WebMethod(operationName="Itens")
	public void cadastra(	
			   @WebParam(name = "codigo") List<String> codigos, 
			   @WebParam(name="tokenUsuario", header=true) String token) {
		
		if(token == null || !token.equals("1234abc")) {
			throw new AutorizacaoException("Nao autorizado");
		}

		
		if(codigos == null || codigos.isEmpty()) {
			return ;
		}

		System.out.println("WebService - Adicionado " + codigos);
		
		for (String codigo : codigos) {
			if(!REPO.contains(codigo)) REPO.add(codigo);
		}
	}

}