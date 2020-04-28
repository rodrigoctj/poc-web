package br.com.aguiabranca.pocweb.model;

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
public class Empresa {
 
	private Long id;
		
	private String cnpj;

	private String razaoSocial;

	private String nomeFantasia;
	
	private Integer inscricaoEstadual;

	
}

