package br.com.roberto.servicos;

import java.util.Date;

import br.com.roberto.entidades.Filme;
import br.com.roberto.entidades.Locacao;
import br.com.roberto.entidades.Usuario;
import br.com.roberto.exceptions.FilmesSemEstoqueException;
import br.com.roberto.exceptions.LocadoraException;
import br.com.roberto.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmesSemEstoqueException, LocadoraException {
		
		if (filme.getEstoque()==0) {
			throw new FilmesSemEstoqueException();
		}
		
		if(usuario==null) {
			throw new LocadoraException("Usuário Vazio");
		}
		
		if(filme==null) {
			throw new LocadoraException("Filme Vazio");
		}
		
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}

}
