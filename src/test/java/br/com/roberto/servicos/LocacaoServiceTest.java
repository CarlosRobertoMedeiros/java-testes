package br.com.roberto.servicos;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

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
import junit.framework.Assert;


public class LocacaoServiceTest{
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception{
		
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificação
		error.checkThat(locacao.getValor(),is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}
	
	//Solução Elegante
	@Test(expected = FilmesSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception{
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

	}
	
	//Solução Robusta Para Checar Usuário
	@Test
	public void testeLocacao_usuarioVazio() throws FilmesSemEstoqueException {
		// cenário
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);
		
		//ação
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário Vazio"));
		}
		
	}
	
	
	
}
