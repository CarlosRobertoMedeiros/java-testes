package br.com.roberto.servicos;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.roberto.entidades.Filme;
import br.com.roberto.entidades.Locacao;
import br.com.roberto.entidades.Usuario;
import br.com.roberto.exceptions.FilmesSemEstoqueException;
import br.com.roberto.exceptions.LocadoraException;
import br.com.roberto.utils.DataUtils;
import org.junit.Assert;


public class LocacaoServiceTest{
	
	private LocacaoService service;
	
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	@Test
	public void deveAlugarFilme() throws Exception{
		
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(),is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}
	
	//Solução Elegante
	@Test(expected = FilmesSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception{
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));
		

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

	}
	
	//Solução Robusta Para Checar Usuário
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {
		// cenário
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		//ação
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário Vazio"));
		}
		
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio");
		
		//ação
		service.alugarFilme(usuario, null);
		
	}
	
	@Test
	public void devePagar75PctoNoFilme3() throws FilmesSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
										   new Filme("Filme 2",2,4.0),
										   new Filme("Filme 3",2,4.0));
		
		//ação
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificação
		//4+4+3 = 11
		assertThat(resultado.getValor(),is(11.0));
		
	} 
	
	@Test
	public void devePagar50PctoNoFilme4() throws FilmesSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
										   new Filme("Filme 2",2,4.0),
										   new Filme("Filme 3",2,4.0),
										   new Filme("Filme 4",2,4.0));
		
		//ação
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificação
		//4+4+3+2 = 13
		assertThat(resultado.getValor(),is(13.0));
		
	}
	
	
	
}
