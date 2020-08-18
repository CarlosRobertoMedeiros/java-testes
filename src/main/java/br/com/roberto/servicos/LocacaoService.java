package br.com.roberto.servicos;

import java.util.Date;
import java.util.List;

import br.com.roberto.entidades.Filme;
import br.com.roberto.entidades.Locacao;
import br.com.roberto.entidades.Usuario;
import br.com.roberto.exceptions.FilmesSemEstoqueException;
import br.com.roberto.exceptions.LocadoraException;
import br.com.roberto.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmesSemEstoqueException, LocadoraException {
		
		
		if(usuario==null) {
			throw new LocadoraException("Usuário Vazio");
		}
		
		if(filmes==null || filmes.isEmpty()) {
			throw new LocadoraException("Filme Vazio");
		}
		
		for(Filme filme : filmes) {
			if (filme.getEstoque()==0) {
				throw new FilmesSemEstoqueException();
			}
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		Double valorTotal = 0d;
		for(int i=0; i<filmes.size();i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			if(i==2) {
				valorFilme = valorFilme*0.75;
			}
			if(i==3) {
				valorFilme = valorFilme*0.50;
			}
			valorTotal+= valorFilme;
		}
		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}

}
