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
import model.entity.Fabricante;
import model.entity.Pessoa;
import model.entity.Unidade;
import model.entity.Vacina;
import model.seletor.AplicacaoSeletor;
import model.seletor.VacinaSeletor;

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
					 throw new SQLException("O registro de aplica��o da vacina foi salvo com sucesso, "
					 		                                            + "por�m n�o foi poss�vel dar baixa no estoque dessa unidade.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar o registro de aplica��o do usu�rio." 
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
				+ "	VACINAS.VACINA.nome, "
				+ "	VACINAS.FABRICANTE.nome as nomeFabricante, "
				+ "	VACINAS.UNIDADE.nome, "
				+ "	VACINAS.APLICACAO.dataAplicacao "
				+ "  from "
				+ "	VACINAS.APLICACAO "
				+ "  join VACINAS.VACINA on VACINAS.VACINA.id = VACINAS.APLICACAO.idVacina "
				+ "  join VACINAS.UNIDADE on VACINAS.UNIDADE.id = VACINAS.APLICACAO.idUnidade "
				+ "  join VACINAS.FABRICANTE on VACINAS.FABRICANTE.id = VACINAS.VACINA.idFabricante "
				+ "  where idpessoa = " + seletor.getIdPessoaRecebeuAplicacao();
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
			}		} catch(SQLException erro){
			System.out.println(
					"Erro durante a execu��o do m�todo \"consultarComFiltros\" ao consultar "
					+ "as aplica��es recebidas do usu�rio de acordo com o filtro selecionado."
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
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Aplicacao aplicacao = null;
		String query = " select * from VACINAS.APLICACAO where id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				aplicacao = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar a aplicacao de id " + id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacao;
	}

		@Override
	public ArrayList<Aplicacao> consultarTodos() {
		return null;
	}
	
		private String preencherFiltros(AplicacaoSeletor seletor, String sql) {
		    final String AND = " AND ";
		    if (seletor.getNomeUnidadeAplicacao() != null && seletor.getNomeUnidadeAplicacao().trim().length() > 0) {
		    	sql += AND;
		    	sql += " UPPER(VACINAS.UNIDADE.nome) LIKE UPPER ('%" + seletor.getNomeUnidadeAplicacao() + "%')";
		    }
		    if (seletor.getNomeVacinaAplicada() != null && seletor.getNomeVacinaAplicada().trim().length() > 0) {
		    	sql += AND;
		    	sql += " UPPER(VACINAS.VACINA.nome) LIKE UPPER ('%" + seletor.getNomeVacinaAplicada() + "%')";
		    }
		    if (seletor.getDataInicioPesquisaSeletor() != null && seletor.getDataFinalPesquisaSeletor() != null) {
		        sql += AND;
		        sql += " dataAplicacao BETWEEN '" + Date.valueOf(seletor.getDataInicioPesquisaSeletor())
		                + "' AND '" + Date.valueOf(seletor.getDataFinalPesquisaSeletor()) + "'";
		    } else if (seletor.getDataInicioPesquisaSeletor() != null) {
		        sql += AND;
		        sql += " dataAplicacao >= '" + Date.valueOf(seletor.getDataInicioPesquisaSeletor()) + "'";
		    } else if (seletor.getDataFinalPesquisaSeletor() != null) {
		        sql += AND;
		        sql += " dataAplicacao <= '" + Date.valueOf(seletor.getDataFinalPesquisaSeletor()) + "'";
		    }
		    return sql;
		}
		
	private AplicacaoSeletor  construirDoResultSet(ResultSet resultado) throws SQLException{
		
		AplicacaoSeletor seletor = new AplicacaoSeletor();
		
		seletor.setAplicacao(consultarPorId(resultado.getInt("id")));
		
		seletor.setFabricanteDaVacinaAplicada(resultado.getString("nomeFabricante"));
		
		return seletor;
	}
	
	private Aplicacao converterParaObjeto(ResultSet resultado) throws SQLException{
		Aplicacao aplicacao = new Aplicacao();
		aplicacao.setId(resultado.getInt("id"));
		PessoaRepository pessoaRepository = new PessoaRepository();
		Pessoa pessoaQueRecebeu = pessoaRepository.consultarPorId(resultado.getInt("idPessoa"));
		aplicacao.setPessoaQueRecebeu(pessoaQueRecebeu);
		VacinaRepository vacinaRepository = new VacinaRepository();
		Vacina vacinaAplicada = vacinaRepository.consultarPorId(resultado.getInt("idVacina"));
		aplicacao.setVacinaAplicada(vacinaAplicada);
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		Unidade unidadeOndeAplicouDose = unidadeRepository.consultarPorId(resultado.getInt("idUnidade"));
		aplicacao.setUnidadeOndeAplicou(unidadeOndeAplicouDose);
		aplicacao.setDataAplicacao(resultado.getDate("dataAplicacao").toLocalDate());
		return aplicacao;
	}

}
