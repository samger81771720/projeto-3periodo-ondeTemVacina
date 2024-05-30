package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.entity.Aplicacao;
import model.seletor.AplicacaoSeletor;

public class AplicacaoRepository implements BaseRepository<Aplicacao>{

	@Override
	public Aplicacao salvar(Aplicacao aplicacao) {
		String sql = "INSERT INTO VACINAS.APLICACAO (idPessoa, idVacina, idUnidade, dataAplicacao) VALUES (?, ?, ?,?)";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		try {
			stmt.setInt(1, aplicacao.getPessoaQueRecebeu().getId());
			stmt.setInt(2, aplicacao.getVacinaAplicada().getId());
			stmt.setInt(3, aplicacao.getUnidadeOndeAplicou().getId());
			if(aplicacao.getDataAplicacao() != null) {
				stmt.setDate(4, Date.valueOf(LocalDate.now()));
			}
			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				aplicacao.setId(resultado.getInt(1));
				UnidadeRepository unidadeRepository = new UnidadeRepository(); 
				boolean deuBaixa = unidadeRepository.darBaixaEstoqueUnidade(aplicacao); 
				if(deuBaixa) {
					return aplicacao;
				} else {
					 throw new SQLException("O registro de aplicação da vacina foi salvo com sucesso, "
					 		                                            + "porém não foi possível dar baixa no estoque dessa unidade.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar o registro de aplicação do usuário." 
											      + aplicacao.getPessoaQueRecebeu().getNome());
			System.out.println("Erro: " + e.getMessage());
		}
		return aplicacao;
	}
	
	public List<AplicacaoSeletor> consultarComFiltros(AplicacaoSeletor seletor){
		ArrayList<AplicacaoSeletor> listaDasAplicacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String sql =	 "select "
										+ "	VACINAS.APLICACAO.id, "
										+ "	VACINAS.APLICACAO.idPessoa, "
										+ "	VACINAS.VACINA.nome, "
										+ "	VACINAS.APLICACAO.idVacina, "
										+ "	VACINAS.APLICACAO.idUnidade, "
										+ "	VACINAS.UNIDADE.nome, \r\n"
										+ "	VACINAS.APLICACAO.dataAplicacao "
										+ "  from "
										+ "	VACINAS.APLICACAO "
										+ "  join VACINAS.VACINA on VACINAS.VACINA.id = VACINAS.APLICACAO.idVacina "
										+ "  join VACINAS.UNIDADE on VACINAS.UNIDADE.id = VACINAS.APLICACAO.idUnidade "
										+ "  where idpessoa = " + seletor.getAplicacao().getPessoaQueRecebeu().getId();
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor,sql);
		} else {
			sql += " order by dataAplicacao ";
		}
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite(); 
			sql += " OFFSET " + seletor.getOffSet();
		}
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
				seletor = construirDoResultSet(resultado);
				listaDasAplicacoes.add(seletor);
			}
		} catch(SQLException erro){
			System.out.println(
					"Erro durante a execução do método \"consultarComFiltros\" ao consultar "
					+ "as aplicações recebidas da pessoa com o filtro selecionado."
					);
			System.out.println("Erro: "+erro.getMessage());
		} finally{
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDasAplicacoes;
	}

		@Override
	public boolean excluir(int id) {
		return false;
	}

	@Override
	public boolean alterar(Aplicacao entidade) {
		return false;
	}

	@Override
	public Aplicacao consultarPorId(int id) {
		return null;
	}

	@Override
	public ArrayList<Aplicacao> consultarTodos() {
		return null;
	}
	
	private String preencherFiltros(AplicacaoSeletor seletor, String sql) {
	    
		final String AND = " AND ";
		
		//1º OK
	    if (seletor.getAplicacao().getVacinaAplicada() != null 
			 && seletor.getAplicacao().getVacinaAplicada().getNome() != null 
			 && seletor.getAplicacao().getVacinaAplicada().getNome().isBlank() 
			 && seletor.getAplicacao().getVacinaAplicada().getNome().isEmpty()) {
	    	sql += AND;
	        sql += " UPPER(VACINAS.VACINA.nome) LIKE UPPER ('%" + seletor.getAplicacao().getVacinaAplicada().getNome() + "%')";
	    }
		
	    // 1º OK 
	    if (seletor.getAplicacao().getUnidadeOndeAplicou() != null 
			 && seletor.getAplicacao().getUnidadeOndeAplicou().getNome() != null 
			 && seletor.getAplicacao().getUnidadeOndeAplicou().getNome().isEmpty()
			 && seletor.getAplicacao().getUnidadeOndeAplicou().getNome().isBlank()) {
	    	sql += AND;
	        sql += " UPPER(VACINAS.UNIDADE.nome) LIKE UPPER('%" + seletor.getAplicacao().getUnidadeOndeAplicou().getNome() + "%')";
	    }
	    
	    //1º OK
	    if (seletor.getDataInicioPesquisaSeletor() != null && seletor.getDataFinalPesquisaSeletor() != null) {
	    	sql += AND;
	        sql += " VACINAS.APLICACAO.dataAplicacao BETWEEN '" + Date.valueOf(seletor.getDataInicioPesquisaSeletor())
	                + "' AND '" + Date.valueOf(seletor.getDataFinalPesquisaSeletor()) + "'";
	    } else if (seletor.getDataInicioPesquisaSeletor() != null) {
	        sql += " VACINAS.APLICACAO.dataAplicacao >= '" + Date.valueOf(seletor.getDataInicioPesquisaSeletor()) + "'";
	    } else if (seletor.getDataFinalPesquisaSeletor() != null) {
	    	sql += AND;
	        sql += " VACINAS.APLICACAO.dataAplicacao <= '" + Date.valueOf(seletor.getDataFinalPesquisaSeletor()) + "'";
	    }
	    return sql;
	}
	
	private AplicacaoSeletor  construirDoResultSet(ResultSet resultado) throws SQLException{
		
		AplicacaoSeletor seletor = new AplicacaoSeletor();
		
	// implementar o método "consultarPorId" da classe "AplicacaoRepository" 
		seletor.setAplicacao(consultarPorId(resultado.getInt("id")));
		
		
		/*
		vacina.setIdVacina(resultado.getInt("id_Vacina"));
		vacina.setNome(resultado.getString("nome"));
		if(resultado.getDate("dataInicioDaPesquisa") != null) {
			vacina.setDataInicioPesquisa(resultado.getDate("dataInicioDaPesquisa").toLocalDate());	
		}
		vacina.setEstagioDaVacina(resultado.getInt("estagio_Da_Pesquisa"));
		vacina.setMediaDaVacina(resultado.getDouble("mediaVacina"));
		PaisRepository paisRepository = new PaisRepository();
		Pais paisDaVacina = paisRepository.consultarPorId(resultado.getInt("id_Pais"));
		vacina.setPais(paisDaVacina);
		PessoaRepository pessoaRepository = new PessoaRepository();
		Pessoa pesquisadorResponsavel = pessoaRepository.consultarPorId(resultado.getInt("id_Pesquisador"));
		vacina.setPesquisadorResponsavel(pesquisadorResponsavel);*/
		
		return seletor;
	
	}

}
