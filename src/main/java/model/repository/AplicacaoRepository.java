package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entity.Aplicacao;
import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.seletor.AplicacaoSeletor;
import model.seletor.VacinaSeletor;

public class AplicacaoRepository implements BaseRepository<Aplicacao>{

	@Override
	public Aplicacao salvar(Aplicacao novoRegistroDaAplicacaoDaVacina) {
		String query = "insert into VACINACAO.APLICACAO_VACINA (id_Pessoa, id_Vacina, dataAplicacao, avaliacaoDaReacao) values(?,?,?,?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			preencherParametrosParaInsert(pstmt, novoRegistroDaAplicacaoDaVacina);
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				novoRegistroDaAplicacaoDaVacina.setIdAplicacao(resultado.getInt(1));
				salvarMediaDeAvaliacaoDaVacina(novoRegistroDaAplicacaoDaVacina.getVacinaAplicada().getIdVacina());
			}
		} catch(SQLException erro) {
			System.out.println("Erro na tentativa de salvar um novo registro de aplicação de vacina no banco de dados da pessoa de id "
					+novoRegistroDaAplicacaoDaVacina.getIdAplicacao());
			System.out.println("Erro: "+erro);
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novoRegistroDaAplicacaoDaVacina;
	}
	
	private void preencherParametrosParaInsert(PreparedStatement pstmt, Aplicacao registroDaAplicacaoDaVacina) throws SQLException {
		pstmt.setInt(1, registroDaAplicacaoDaVacina.getIdPessoa());
		pstmt.setInt(2, registroDaAplicacaoDaVacina.getVacinaAplicada().getIdVacina());
		if(registroDaAplicacaoDaVacina.getDataAplicacao() != null) {
			pstmt.setDate(3, Date.valueOf(registroDaAplicacaoDaVacina.getDataAplicacao()));
		}
		pstmt.setInt(4, registroDaAplicacaoDaVacina.getAvaliacaoReacao());
	}
	
	private void salvarMediaDeAvaliacaoDaVacina(int idVacina) {
		double media = calcularMediaDeAvaliacaoDaVacina(idVacina);
		String query = "update VACINACAO.VACINA set mediaVacina = ? where id_Vacina = ?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setDouble(1, media);
			pstmt.setInt(2, idVacina);
			pstmt.executeUpdate();
		}catch (Exception erro) {
			System.out.println("Erro na tentativa de atualizar o registro da média da vacina.");
			System.out.println("Erro: "+ erro);
		}finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
	}
	
	private double calcularMediaDeAvaliacaoDaVacina(int id){
		double mediaDeAvaliacaoDaVacina = 0;
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "select  *  from VACINACAO.APLICACAO_VACINA where id_Vacina = " + id;
		try{
			resultado = stmt.executeQuery(query);
			double somatorioDasAvaliacoes = 0;
			double totalAplicacoes = 0;
			while(resultado.next()){
				Aplicacao aplicacao = new Aplicacao();
				aplicacao.setAvaliacaoReacao(resultado.getInt("avaliacaoDaReacao"));
				somatorioDasAvaliacoes += aplicacao.getAvaliacaoReacao();
				totalAplicacoes ++;
			}
			mediaDeAvaliacaoDaVacina = somatorioDasAvaliacoes/totalAplicacoes;
		} catch (SQLException erro){
			System.out.println("Erro ao executar o cálculo de média das avaliações da vacina.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return mediaDeAvaliacaoDaVacina;
	}
	
	@Override
	public boolean excluir(int id) {
		boolean excluiu = false;
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = "delete from VACINACAO.APLICACAO_VACINA where id_Aplicacao  =  " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (Exception erro) {
			System.out.println("Erro ao tentar excluir a o registro de aplicação da vacina do cadastro.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu; 
	}
	
	@Override
	public boolean alterar(Aplicacao registroDaAplicacaoDaVacinaAlterado) {
		boolean alterou = false;
		String query = "update\r\n"
				+ "	VACINACAO.APLICACAO_VACINA\r\n"
				+ "set\r\n"
				+ "	id_Pessoa = ?,\r\n"
				+ "	id_Vacina = ?,\r\n"
				+ "	dataAplicacao = ?,\r\n"
				+ "	avaliacaoDaReacao = ?\r\n"
				+ "where\r\n"
				+ "	id_Aplicacao = ? ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			preencherParametrosParaUpdate(pstmt, registroDaAplicacaoDaVacinaAlterado);
			alterou = pstmt.executeUpdate() > 0;
			if(alterou) {
				salvarMediaDeAvaliacaoDaVacina(registroDaAplicacaoDaVacinaAlterado.getVacinaAplicada().getIdVacina());
			}
		}catch (Exception erro) {
			System.out.println("Erro na tentaiva de atualizar o registro da aplicação da vacina.");
			System.out.println("Erro: "+ erro);
		}finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}
	
	private void preencherParametrosParaUpdate(PreparedStatement pstmt, Aplicacao registroDaAplicacaoDaVacinaAlterado) throws SQLException {
		pstmt.setInt(1, registroDaAplicacaoDaVacinaAlterado.getIdPessoa());
		pstmt.setInt(2, registroDaAplicacaoDaVacinaAlterado.getVacinaAplicada().getIdVacina());
		if(registroDaAplicacaoDaVacinaAlterado.getDataAplicacao() != null) {
			pstmt.setDate(3, Date.valueOf(registroDaAplicacaoDaVacinaAlterado.getDataAplicacao()));
		}
		pstmt.setInt(4, registroDaAplicacaoDaVacinaAlterado.getAvaliacaoReacao());
		pstmt.setInt(5, registroDaAplicacaoDaVacinaAlterado.getIdAplicacao());
	}
	
	public Aplicacao consultarPorId(int id) {
		Connection coon = Banco.getConnection();
		Statement stmt = Banco.getStatement(coon);
		ResultSet resultado = null;
		Aplicacao aplicacaoDaPessoa = null;
		String query = "select * from VACINACAO.APLICACAO_VACINA where id_Aplicacao = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				aplicacaoDaPessoa = new Aplicacao();
				VacinaRepository vacinaRepository = new VacinaRepository();
				aplicacaoDaPessoa.setIdAplicacao(resultado.getInt("id_Aplicacao"));
				aplicacaoDaPessoa.setIdPessoa(resultado.getInt("id_Pessoa"));
				Vacina vacinaAplicada = vacinaRepository.consultarPorId(resultado.getInt("id_Vacina"));
				aplicacaoDaPessoa.setVacinaAplicada(vacinaAplicada);
				aplicacaoDaPessoa.setDataAplicacao(resultado.getDate("dataAplicacao").toLocalDate());
				aplicacaoDaPessoa.setAvaliacaoReacao(resultado.getInt("avaliacaoDaReacao"));
			}
		} catch (Exception erro) {
			System.out.println("Erro ao tentar consultar a aplicação de id: "+id);
			System.out.println("Erro: " + erro );
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(coon);
		}
		return aplicacaoDaPessoa;
	}

	@Override
	public ArrayList<Aplicacao>consultarTodos(){
		ArrayList<Aplicacao>listaDeAplicacoesDaPessoa = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = "select * from VACINACAO.APLICACAO_VACINA";
		ResultSet resultado = null;
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Aplicacao aplicacaoDaPessoa = new Aplicacao();
				VacinaRepository vacinaRepository = new VacinaRepository();
				aplicacaoDaPessoa.setIdAplicacao(resultado.getInt("id_Aplicacao"));
				aplicacaoDaPessoa.setIdPessoa(resultado.getInt("id_Pessoa"));
				Vacina vacinaAplicadaNaPessoa = vacinaRepository.consultarPorId(resultado.getInt("id_Vacina"));
				aplicacaoDaPessoa.setVacinaAplicada(vacinaAplicadaNaPessoa);
				aplicacaoDaPessoa.setDataAplicacao(resultado.getDate("dataAplicacao").toLocalDate());
				aplicacaoDaPessoa.setAvaliacaoReacao(resultado.getInt("avaliacaoDaReacao"));
				listaDeAplicacoesDaPessoa.add(aplicacaoDaPessoa);
			}
		} catch (Exception e) {
			System.out.println("Erro ao executar consultar todas as aplicações de vacina da pessoa.");
			System.out.println();
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeAplicacoesDaPessoa;
	}
	
	public ArrayList<Aplicacao> consultarTodasAplicacoesDaPessoa(int id) {
		ArrayList<Aplicacao> aplicacoesDaPessoa = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "select * from VACINACAO.APLICACAO_VACINA "
				+ "where VACINACAO.APLICACAO_VACINA.id_Pessoa  ="+id;
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Aplicacao aplicacao = new Aplicacao();
				VacinaRepository vacinaRepository = new VacinaRepository();
				aplicacao.setIdAplicacao(resultado.getInt("id_Aplicacao"));
				aplicacao.setIdPessoa(resultado.getInt("id_Pessoa"));
				Vacina vacinaAplicada = vacinaRepository.consultarPorId(resultado.getInt("id_Vacina"));
				aplicacao.setVacinaAplicada(vacinaAplicada);
				if(resultado.getDate("dataAplicacao")!=null) {
					aplicacao.setDataAplicacao(resultado.getDate("dataAplicacao").toLocalDate());
				}
				aplicacao.setAvaliacaoReacao(resultado.getInt("avaliacaoDaReacao"));
				aplicacoesDaPessoa.add(aplicacao);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as aplicações da pessoa.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoesDaPessoa;
	}
	
	public boolean validarAplicacaoDeVacinaPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean aplicou = false;
		String query="select * from VACINACAO.APLICACAO_VACINA where id_Vacina =  "+id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				aplicou = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar verificar se vacina de id "+id+" foi aplicada.");
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicou;
	}
	
	public ArrayList<Aplicacao> consultarComFiltros(AplicacaoSeletor seletor){
		
		ArrayList<Aplicacao> listaDeAplicacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String sql = 	"select * from VACINACAO.APLICACAO_VACINA";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor,sql);
		}
		/*
		  - Em SQL, a cláusula "LIMIT" é usada para restringir o número de linhas retornadas por uma consulta.
		  
		  - A cláusula OFFSET em SQL é usada em conjunto com a cláusula LIMIT para especificar a quantidade 
		  de linhas a serem "ignoradas" no início do conjunto de resultados. Isso é útil quando você deseja pular 
		  um número específico de linhas antes de começar a retornar os resultados da consulta.
		  */
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffSet(); 
		}
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
				Aplicacao aplicacao = construirDoResultSet(resultado);
				listaDeAplicacoes.add(aplicacao);
			}
		} catch(SQLException erro){
			System.out.println("Erro durante a execução do método \"consultarComFiltros\" ao consultar as aplicações do filtro selecionado.");
			System.out.println("Erro: "+erro.getMessage());
		} finally{
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeAplicacoes;
	}
	
	private String preencherFiltros(AplicacaoSeletor seletor, String sql) {
	    
		final String AND = " AND ";
		
		sql += "select ap.* from VACINACAO.APLICACAO_VACINA ap "
				+ "inner join VACINACAO.PESSOA p on ap.id_Pessoa  = p.id_Pessoa "
				+ "inner join VACINACAO.VACINA v on v.id_Vacina = ap.id_Vacina "
				+ " WHERE ";

	    boolean primeiro = true;

	    if (seletor.getVacinaUsadaNaAplicacao() != null && seletor.getVacinaUsadaNaAplicacao().trim().length() > 0) {
	        sql += " UPPER(v.nome) LIKE UPPER ('%" + seletor.getVacinaUsadaNaAplicacao() + "%')";
	        primeiro = false;
	    }
	    if (seletor.getPessoaQueRecebeu() != null && seletor.getPessoaQueRecebeu().trim().length() > 0) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " UPPER(p.nome) LIKE UPPER('%" + seletor.getPessoaQueRecebeu() + "%')";
	        primeiro = false;
	    }
	    if (seletor.getDataInicialDaAplicacaoDaVacina() != null && seletor.getDataFinalDaAplicacaoDaVacina() != null) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " ap.dataAplicacao BETWEEN '" + Date.valueOf(seletor.getDataInicialDaAplicacaoDaVacina())
	                + "' AND '" + Date.valueOf(seletor.getDataFinalDaAplicacaoDaVacina()) + "'";
	    } else if (seletor.getDataInicialDaAplicacaoDaVacina() != null) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " ap.dataAplicacao >= '" + Date.valueOf(seletor.getDataInicialDaAplicacaoDaVacina()) + "'";
	    } else if (seletor.getDataFinalDaAplicacaoDaVacina() != null) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " dataAplicacao <= '" + Date.valueOf(seletor.getDataFinalDaAplicacaoDaVacina()) + "'";
	    }
	    return sql;
	}
	
	private Aplicacao construirDoResultSet(ResultSet resultado) throws SQLException{
		
		Aplicacao aplicacao = new Aplicacao();
		aplicacao.setIdPessoa(resultado.getInt("id_Pessoa"));
		VacinaRepository vacinaRepository = new VacinaRepository();
		Vacina vacinaAplicada = vacinaRepository.consultarPorId(resultado.getInt("id_Vacina"));
		aplicacao.setVacinaAplicada(vacinaAplicada);
		if(resultado.getDate("dataAplicacao")!=null) {
			aplicacao.setDataAplicacao(resultado.getDate("dataAplicacao").toLocalDate());
		}
		aplicacao.setAvaliacaoReacao(resultado.getInt("avaliacaoDaReacao"));
		
		return aplicacao;
	
	}
	
}
