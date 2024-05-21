package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.seletor.VacinaSeletor;

public class VacinaRepository implements BaseRepository<Vacina>{
	
	public int validarPesquisador(Vacina vacina) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		int tipoDaPessoa = 0;
		String query ="select \r\n"
				+ "	VACINACAO.PESSOA.tipo \r\n"
				+ "from\r\n"
				+ "	VACINACAO.PESSOA\r\n"
				+ "where 	\r\n"
				+ "	VACINACAO.PESSOA.nome ='"
				+vacina.getPesquisadorResponsavel().getNome()+"'";
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				tipoDaPessoa = resultado.getInt("tipo");
				}
			}catch (SQLException erro) {
				System.out.println("Erro ao tentar consultar o pesquisador "
						+vacina.getPesquisadorResponsavel().getNome());
				System.out.println("Erro: "+erro.getMessage());
			} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
			}
		return tipoDaPessoa;
}
	
	@Override
	public Vacina salvar(Vacina novaVacina) {
	   String query = "insert into VACINACAO.VACINA (id_Pesquisador, id_Pais, nome, estagio_Da_Pesquisa, dataInicioDaPesquisa) "
	   		+ "values (?, ? , ? , ?, ? )";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
	    try {
	    	    preencherParametrosParaInsertOuUpdate(pstmt, novaVacina);
	            pstmt.execute();
	            ResultSet resultado = pstmt.getGeneratedKeys();
	            if(resultado.next()) {
	            	novaVacina.setIdVacina(resultado.getInt(1));
	        }
	    } catch(SQLException erro){
	        System.out.println("Erro na tentativa de salvar uma nova vacina no banco de dados.");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return novaVacina; 
	}
	
		// OK!
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Vacina novaVacina) throws SQLException  {
	    pstmt.setInt(1, novaVacina.getPesquisadorResponsavel().getIdPessoa());
		pstmt.setInt(2, novaVacina.getPais().getId_Pais());
		pstmt.setString(3, novaVacina.getNome());
		pstmt.setInt(4, novaVacina.getEstagioDaVacina());
		if(novaVacina.getDataInicioPesquisa()!=null) {
			pstmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
		}
	}
	
	
	
	// OK!
	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINACAO.VACINA where VACINA.id_Vacina = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar excluir a vacina do cadastro.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	// OK!
	@Override
	public boolean alterar(Vacina vacinaParaAlterar) {
	    boolean alterou = false;
	    String query = "update \r\n"
	    		+ "	VACINACAO.VACINA \r\n"
	    		+ "set\r\n"
	    		+ "	VACINACAO.VACINA.id_Pesquisador = ?, \r\n"
	    		+ "	VACINACAO.VACINA.id_Pais = ?,\r\n"
	    		+ "	VACINACAO.VACINA.nome = ?,\r\n"
	    		+ "	VACINACAO.VACINA.estagio_Da_Pesquisa = ?,\r\n"
	    		+ "	VACINACAO.VACINA.dataInicioDaPesquisa = ? \r\n"
	    		+ "where\r\n"
	    		+ "	VACINA.id_Vacina = ?";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	pstmt.setInt(1, vacinaParaAlterar.getPesquisadorResponsavel().getIdPessoa());
	    	pstmt.setInt(2, vacinaParaAlterar.getPais().getId_Pais());
	        pstmt.setString(3, vacinaParaAlterar.getNome());
	        pstmt.setInt(4, vacinaParaAlterar.getEstagioDaVacina());
	        pstmt.setDate(5, Date.valueOf(vacinaParaAlterar.getDataInicioPesquisa()));
	        pstmt.setInt(6, vacinaParaAlterar.getIdVacina());
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao atualizar Vacina");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}
	
	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Vacina vacina = null;
		String query="select * from VACINACAO.VACINA where id_Vacina = "+id;
		try {
			resultado = stmt.executeQuery(query);
			
			if(resultado.next()) {
				
				vacina = new Vacina();
				PaisRepository paisRepository = new PaisRepository();
				
				vacina.setIdVacina(resultado.getInt("id_Vacina"));
				vacina.setNome(resultado.getString("nome"));
				vacina.setEstagioDaVacina(resultado.getInt("estagio_Da_Pesquisa"));
				vacina.setDataInicioPesquisa(resultado.getDate("dataInicioDaPesquisa").toLocalDate());
				
				// FUNCIONAVA SEM LOOPING DESSA FORMA
				Pessoa pesquisadorResponsavelPelaVacina = new Pessoa();
				
				pesquisadorResponsavelPelaVacina.setIdPessoa(resultado.getInt("id_Pesquisador"));
				vacina.setPesquisadorResponsavel(pesquisadorResponsavelPelaVacina);
				
				//DESSA FORMA ENTRA EM LOOPING
				//PessoaRepository pessoaRepository = new PessoaRepository();
				//Pessoa pesquisadorResponsavelPelaVacina = pessoaRepository .consultarPorId(resultado.getInt("id_Pesquisador"));
				
				Pais paisDaVacina = paisRepository.consultarPorId(resultado.getInt("id_Pais"));
				vacina.setPais(paisDaVacina);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar a vacina de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}
	
	@Override
	public ArrayList<Vacina> consultarTodos() {
		ArrayList<Vacina> vacinas = new ArrayList<Vacina>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "select * from VACINACAO.VACINA";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Vacina vacina = new Vacina();
				PessoaRepository pessoaRepository = new PessoaRepository();
				PaisRepository paisRepository = new PaisRepository();
				vacina.setIdVacina(resultado.getInt("id_Vacina"));
				vacina.setNome(resultado.getString("nome"));
				Pessoa pesquisadorResponsavel = pessoaRepository.consultarPorId(resultado.getInt("id_Pesquisador"));
				vacina.setPesquisadorResponsavel(pesquisadorResponsavel);
				vacina.setEstagioDaVacina(resultado.getInt("estagio_Da_Pesquisa"));
				vacina.setDataInicioPesquisa(resultado.getDate("dataInicioDaPesquisa").toLocalDate());
				Pais paisDaVacina = paisRepository.consultarPorId(resultado.getInt("id_Pais"));
				vacina.setPais(paisDaVacina);
				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar consultar todas as vacinas.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
	
	public ArrayList<Vacina> consultarComFiltros(VacinaSeletor seletor){
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String sql = 	"select v.* from VACINACAO.VACINA v";
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor,sql);
		}
		/*
		  - Em SQL, a cláusula "LIMIT" é usada para restringir o número de linhas(registros) retornadas por uma consulta.
		  
		  - A cláusula OFFSET em SQL é usada em conjunto com a cláusula LIMIT para especificar a quantidade 
		  de linhas(registros) a serem "ignoradas" no início do conjunto de resultados. Isso é útil quando você deseja pular 
		  um número específico de linhas antes de começar a retornar os resultados da consulta.
		  */
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite(); 
			sql += " OFFSET " + seletor.getOffSet();
		}
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
				Vacina vacina = construirDoResultSet(resultado);
				vacinas.add(vacina);
			}
		} catch(SQLException erro){
			System.out.println(
					"Erro durante a execução do método \"consultarComFiltros\" ao consultar as vacinas do filtro selecionado."
					);
			System.out.println("Erro: "+erro.getMessage());
		} finally{
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
	
	private String preencherFiltros(VacinaSeletor seletor, String sql) {
	    
		final String AND = " AND ";

	    sql += " INNER JOIN VACINACAO.PAIS p ON v.id_Pais = p.id_Pais "
	            + " INNER JOIN VACINACAO.PESSOA pe ON pe.id_Pessoa = v.id_Pesquisador WHERE ";

	    boolean primeiro = true;

	    if (seletor.getNomeVacina() != null && seletor.getNomeVacina().trim().length() > 0) {
	        sql += " UPPER(v.nome) LIKE UPPER ('%" + seletor.getNomeVacina() + "%')";
	        primeiro = false;
	    }
	    if (seletor.getNomePesquisador() != null && seletor.getNomePesquisador().trim().length() > 0) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " UPPER(pe.nome) LIKE UPPER('%" + seletor.getNomePesquisador() + "%')";
	        primeiro = false;
	    }
	    if (seletor.getNomePais() != null && seletor.getNomePais().trim().length() > 0) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " UPPER(p.nome) LIKE UPPER('%" + seletor.getNomePais() + "%')";
	    }
	    if (seletor.getDataInicioPesquisaSeletor() != null && seletor.getDataFinalPesquisaSeletor() != null) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " v.dataInicioDaPesquisa BETWEEN '" + Date.valueOf(seletor.getDataInicioPesquisaSeletor())
	                + "' AND '" + Date.valueOf(seletor.getDataFinalPesquisaSeletor()) + "'";
	    } else if (seletor.getDataInicioPesquisaSeletor() != null) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " v.dataInicioDaPesquisa >= '" + Date.valueOf(seletor.getDataInicioPesquisaSeletor()) + "'";
	    } else if (seletor.getDataFinalPesquisaSeletor() != null) {
	        if (!primeiro) {
	            sql += AND;
	        }
	        sql += " v.dataInicioDaPesquisa <= '" + Date.valueOf(seletor.getDataFinalPesquisaSeletor()) + "'";
	    }
	    return sql;
	}

	private Vacina construirDoResultSet(ResultSet resultado) throws SQLException{
		
		Vacina vacina = new Vacina();
		
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
		vacina.setPesquisadorResponsavel(pesquisadorResponsavel);
		
		return vacina;
	
	}
	
	public int contarTotalRegistros(VacinaSeletor seletor) {
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		int totalRegistros = 0;
		ResultSet resultado = null;
		String query = "select count(v.id_Vacina) from VACINACAO.VACINA v "
								  + "inner join VACINACAO.PAIS p on P.id_Pais  = V.id_Pais "
								  + "inner join VACINACAO.PESSOA pe on pe.id_Pessoa = v.id_Pesquisador";
		
		if(seletor.temFiltro()) {
			query +=  preencherFiltros(seletor, query);
		}
		
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				totalRegistros = resultado.getInt(1);
			}
		} catch (SQLException erro){
				System.out.println("Erro ao contar as vacinas filtradas");
				System.out.println("Erro: " + erro.getMessage());
			} finally {
				Banco.closeResultSet(resultado);
				Banco.closeStatement(stmt);
				Banco.closeConnection(conn);
			}
		return totalRegistros;
	}
	
	public int contarPaginas(VacinaSeletor seletor) {
		
		int totalPaginas = 0;
		int totalRegistros = this.contarTotalRegistros(seletor);
		totalPaginas = totalRegistros  / seletor.getLimite();
		int resto = totalRegistros % seletor.getLimite();
		
		if(resto > 0) {
			totalPaginas ++;
		}
		
		return totalPaginas;
	}

}
