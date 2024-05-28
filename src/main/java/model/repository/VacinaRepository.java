package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Unidade;
import model.entity.Vacina;
import model.seletor.VacinaSeletor;

public class VacinaRepository implements BaseRepository<Vacina>{

	@Override
	public Vacina salvar(Vacina novaEntidade) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	@Override
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINAS.VACINA where id = " + id;
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

	@Override
	public boolean alterar(Vacina entidade) {
		// TODO Stub de método gerado automaticamente
		return false;
	}
	
	/*
	 O ResultSet é uma tabela de resultados da consulta SQL.
	Após executeQuery, o ResultSet contém as linhas e colunas retornadas pela consulta.
	Você usa next() para iterar sobre as linhas do ResultSet.
	Você pode acessar os dados da linha atual do ResultSet usando métodos como getInt, getString, etc.
	A função converterParaObjeto mapeia esses valores para um objeto da sua classe de domínio.
	*/
	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Vacina vacina = null;
		String query = "select id, idFabricante, nome, categoria, idadeMinima, idadeMaxima,	"
				                  + "contraIndicacao from VACINAS.VACINA where id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				vacina = this.converterParaObjeto(resultado);
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
		return null;
	}
	
	public List<Vacina> consultarComFiltros(VacinaSeletor seletor){
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String sql = "select"
									+ " VACINA.nome,"
									+ " VACINA.categoria,"
									+ " VACINA.idadeMinima,"
									+ " VACINA.idadeMaxima,"
									+ " VACINA.contraIndicacao,"
									+ " FABRICANTE.nome,"
									+ " UNIDADE.id,"
									+ " UNIDADE.nome,"
									+ " ENDERECO.bairro,"
									+ " ENDERECO.cep"
									+ " FROM"
									+ " VACINAS.VACINA ";
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

		final String AND = " and ";

	    sql += " JOIN"
	    		+ " VACINAS.FABRICANTE ON VACINAS.VACINA.idFabricante = VACINAS.FABRICANTE.id"
	    		+ " JOIN"
	    		+ " VACINAS.ESTOQUE ON VACINAS.VACINA.id = VACINAS.ESTOQUE.idVacina"
	    		+ " JOIN"
	    		+ " VACINAS.UNIDADE ON VACINAS.ESTOQUE.idUnidade = VACINAS.UNIDADE.id"
	    		+ " JOIN"
	    		+ "VACINAS.ENDERECO ON VACINAS.UNIDADE.idEndereco = VACINAS.ENDERECO.id where VACINAS.ESTOQUE.quantidade > 0";

	    boolean primeiro = true;

	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getNome() != null  
		        && !seletor.getVacina().getNome().isBlank() 
		        && !seletor.getVacina().getNome().isEmpty()
	    	) {
	        sql += " UPPER(VACINA.nome) LIKE UPPER ('%" + seletor.getVacina().getNome() + "%')";
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

	private Vacina converterParaObjeto(ResultSet resultado) throws SQLException{
		Vacina vacina = new Vacina();
		vacina.setId(resultado.getInt("id"));
		vacina.setNome(resultado.getString("nome"));
		FabricanteRepository fabricanteRepository = new FabricanteRepository();
		vacina.setFabricanteDaVacina(fabricanteRepository.consultarPorId(resultado.getInt("id")));
		vacina.setCategoria(resultado.getString("categoria"));
		vacina.setIdadeMinima(resultado.getInt("idadeMinima"));
		vacina.setIdadeMaxima(resultado.getInt("idadeMaxima"));
		vacina.setContraIndicacao(resultado.getBoolean("contraIndicacao"));
		return vacina;
	}
	
}
