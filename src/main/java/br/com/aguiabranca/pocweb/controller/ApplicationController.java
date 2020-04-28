package br.com.aguiabranca.pocweb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import br.com.aguiabranca.pocweb.auth.JwtToken;
import br.com.aguiabranca.pocweb.auth.SsoWrapper;
import br.com.aguiabranca.pocweb.model.Empresa;
import br.com.aguiabranca.pocweb.model.EmpresaWrapper;

@Controller
public class ApplicationController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping(value = "/")
	public String getHome() {
		return "index";
	}
	
	@GetMapping(value = "/logout")
	public RedirectView logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.logout();
		response.sendRedirect("/");
		return null;
	}
	
	@GetMapping(value = "/list-empresa")
	public String getEmpresas(Model model) {	
				
		ResponseEntity<EmpresaWrapper> responseEntity = restTemplate.exchange(
			    "http://localhost:8080/api/v1/empresas", 
			    HttpMethod.GET, 
			    buildHeader(), 
			    new ParameterizedTypeReference<EmpresaWrapper>() {
			    });
		
		List<Empresa> empresas = responseEntity.getBody().get_embedded().getEmpresas();
		model.addAttribute("empresas", empresas);
		
		return "list-empresa";
	}
		
	
	@GetMapping("/show-create")
	public String showCreateForm(Empresa empresa) {
		return "create-empresa";
	}
	
	@PostMapping("/create")
	public String create(@Valid Empresa empresa, BindingResult result, Model model, HttpServletResponse response) throws IOException {
		
		if (result.hasErrors()) {	
			return "empresas";
		}

		restTemplate.exchange("http://localhost:8080/api/v1/empresas/",			     
								HttpMethod.POST, 
								buildHeader(empresa), 
								new ParameterizedTypeReference<Empresa>() {});
		
		response.sendRedirect("/list-empresa");
		return "list-empresa";

	}
	
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") Long id, @Valid Empresa empresa, BindingResult result, Model model, HttpServletResponse response) throws IOException {		
		
		restTemplate.exchange("http://localhost:8080/api/v1/empresas/"+id,			     
			    					HttpMethod.PUT, 
			    					buildHeader(empresa), 
			    					new ParameterizedTypeReference<Empresa>() {});
		
		response.sendRedirect("/list-empresa");
		
		return "list-empresa";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
	
		ResponseEntity<Empresa> responseEntity = restTemplate.exchange(
			    "http://localhost:8080/api/v1/empresas/"+id, 
			    HttpMethod.GET, 
			    buildHeader(), 
			    new ParameterizedTypeReference<Empresa>() {
			    });
		
		model.addAttribute("empresa", responseEntity.getBody());
		return "update-empresa";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model, HttpServletResponse response) throws IOException {
					
		restTemplate.exchange(
			    "http://localhost:8080/api/v1/empresas/"+id, 
			    HttpMethod.DELETE, 
			    buildHeader(), 
			    new ParameterizedTypeReference<Empresa>() {
			    });
		
		response.sendRedirect("/list-empresa");
		return "list-empresa";
	}
	
	private String getToken() {
		JwtToken jwt = new JwtToken(SsoWrapper
				.builder()
					.clientId("poc-web")
					.clientSecret("9d2bacb9-2f67-4509-9924-c410b90916f2")
					.grantType("password")
					.password("1234")
					.realm("poc")
					.serverUrl("http://localhost:8180/auth")
					.username("rodrigoctj")
				.build());
		
		return jwt.getToken().getAccessToken();
	}
	
	private HttpEntity<String> buildHeader() {
		HttpHeaders headers = new HttpHeaders();		
		headers.setBearerAuth(getToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		return entity;
	}
	
	private HttpEntity<Empresa> buildHeader(Empresa empresa) {
		HttpHeaders headers = new HttpHeaders();		
		headers.setBearerAuth(getToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Empresa> entity = new HttpEntity<>(empresa, headers);
		
		return entity;
	}
}	
