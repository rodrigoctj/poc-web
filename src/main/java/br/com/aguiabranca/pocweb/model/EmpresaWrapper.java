package br.com.aguiabranca.pocweb.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaWrapper {
 
	private Dados _embedded;
	
	@Getter
	@Setter
	public class Dados{	
		private List<Empresa> empresas;
	}
}

