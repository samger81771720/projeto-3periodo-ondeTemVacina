package model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entity.Vacina;
import model.seletor.VacinaSeletor;

public class VacinaRepository implements BaseRepository<Vacina>{

	@Override
	public Vacina salvar(Vacina novaEntidade) {
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
	
	public List<VacinaSeletor> consultarComFiltros(VacinaSeletor seletor){
		
		ArrayList<VacinaSeletor> listagemUnidadesComVacinas = new ArrayList<>();
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		String sql = "select"
									+ " VACINA.id as id_Vacina,"
									+ " VACINA.nome as nome_Vacina,"
									+ " VACINA.categoria as categoria_Vacina,"
									+ " VACINA.idadeMinima as idadeMinima_Vacina,"
									+ " VACINA.idadeMaxima as idadeMaxima_Vacina,"
									+ " VACINA.contraIndicacao as contraIndicacao_Vacina,"
									+ " FABRICANTE.nome as nome_Fabricante,"
									+ " UNIDADE.id as id_Unidade,"
									+ " UNIDADE.nome as nome_Unidade,"
									+ " ENDERECO.bairro as bairro_Unidade,"
									+ " ENDERECO.cep as cep_Unidade"
									+ " FROM"
									+ " VACINAS.VACINA ";
		
		if(seletor.temFiltro()) {
			sql = preencherFiltros(seletor,sql);
		}
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite(); 
			sql += " OFFSET " + seletor.getOffSet();
		}
		
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
			    seletor = construirDoResultSet(resultado);
			    listagemUnidadesComVacinas.add(seletor);
			}
		} catch(SQLException erro){
			System.out.println(
					"Erro durante a execução do método \"consultarComFiltros\" ao consultar "
				 + "as unidades com seus respectivos estoques de vacinas de acordo com o  filtro selecionado."
					);
			System.out.println("Erro: "+erro.getMessage());
		} finally{
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listagemUnidadesComVacinas;
	}
	
	private String preencherFiltros(VacinaSeletor seletor, String sql) {

		final String AND = " and ";
		final String JOIN = " join ";

	    sql += JOIN
	    		+ " VACINAS.FABRICANTE ON VACINAS.VACINA.idFabricante = VACINAS.FABRICANTE.id"
	    		+   JOIN
	    		+ " VACINAS.ESTOQUE ON VACINAS.VACINA.id = VACINAS.ESTOQUE.idVacina"
	    		+   JOIN
	    		+ " VACINAS.UNIDADE ON VACINAS.ESTOQUE.idUnidade = VACINAS.UNIDADE.id"
	    		+   JOIN
	    		+ "VACINAS.ENDERECO ON VACINAS.UNIDADE.idEndereco = VACINAS.ENDERECO.id where VACINAS.ESTOQUE.quantidade > 0";

	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getNome() != null  
		        && !seletor.getVacina().getNome().isBlank() 
		        && !seletor.getVacina().getNome().isEmpty()
	    	) {
	        sql += AND + " UPPER(VACINA.nome) LIKE UPPER ('%" + seletor.getVacina().getNome() + "%')";
	    }
	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getCategoria() != null  
		        && !seletor.getVacina().getCategoria().isBlank() 
		        && !seletor.getVacina().getCategoria().isEmpty()
	    	) {
	        sql += AND + " UPPER(VACINA.categoria) LIKE UPPER ('%" + seletor.getVacina().getCategoria() + "%')";
	    }
	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getIdadeMinima() != 0  
		 	) {
	        sql += AND + " VACINA.idadeMinima =  " + seletor.getVacina().getIdadeMinima();
	    }
	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getIdadeMaxima() != 0  
		 	) {
	        sql += AND + " VACINA.idadeMaxima =  " + seletor.getVacina().getIdadeMaxima();
	    }
	    if(
	    	seletor.getVacina() != null
	    	&& !seletor.getVacina().isContraIndicacao()
	    	) {
	    	sql += AND + " VACINA.contraIndicacao = false ";
	    }
	    if(
	    	seletor.getVacina() != null
	    	&& seletor.getVacina().isContraIndicacao()
	    	) {
	    	sql += AND + " VACINA.contraIndicacao = true ";
		    }
	    if(
    		seletor.getFabricante() != null  
	        && !seletor.getFabricante().isBlank() 
	        && !seletor.getFabricante().isEmpty()	
	    	) {
	    	sql += AND + " UPPER(FABRICANTE.nome) LIKE UPPER ('%" + seletor.getFabricante() + "%')";
	    }
	    if (
	    	  seletor.getUnidade() != null
			  && seletor.getUnidade().getNome() != null
			  && !seletor.getUnidade().getNome().isBlank() 
			  && !seletor.getUnidade().getNome().isEmpty()
	    	) {
	        sql += AND + " UPPER(UNIDADE.nome) LIKE UPPER ('%" + seletor.getUnidade().getNome() + "%')";
	    }
	    if (
    		seletor.getBairro() != null
    		&& !seletor.getBairro().isBlank() 
	        && !seletor.getBairro().isEmpty()
	    	) {
	        sql += AND + " UPPER(ENDERECO.bairro) LIKE UPPER ('%" 
	        	   + seletor.getBairro() + "%')";
	    }
	    if (
	    		seletor.getCep() != null
	    		&& !seletor.getCep().isBlank() 
		        && !seletor.getCep().isEmpty()
	    	) {
	        sql += AND + " UPPER(ENDERECO.cep) LIKE UPPER ('%" 
	        	   + seletor.getCep() + "%')";
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
	private VacinaSeletor construirDoResultSet(ResultSet resultado) throws SQLException{
		VacinaSeletor seletor = new VacinaSeletor();
		seletor.setVacina(consultarPorId(resultado.getInt("id_Vacina")));
		seletor.setFabricante(resultado.getString("nome_Fabricante"));
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		seletor.setUnidade(unidadeRepository.consultarPorId(resultado.getInt("id_Unidade")));
		return seletor;
	}
}
