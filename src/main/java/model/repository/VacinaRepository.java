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
									+ " FABRICANTE.id as id_Fabricante,"
									+ " UNIDADE.id as id_Unidade,"
									+ " UNIDADE.nome as nome_Unidade,"
									+ " ENDERECO.bairro as bairro_Unidade,"
									+ " ENDERECO.cep as cep_Unidade"
									+ " FROM"
									+ " VACINAS.VACINA ";
		
		sql = preencherFiltros(seletor,sql);
		
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
	    		+ " VACINAS.ENDERECO ON VACINAS.UNIDADE.idEndereco = VACINAS.ENDERECO.id"
	    		+   JOIN
	    		+ " VACINAS.CONTATO on VACINAS.UNIDADE.idContato = VACINAS.CONTATO.id where "
	    		+ " VACINAS.ESTOQUE.quantidade > 0";
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
	    
	    
	    
	    // ok between ok
	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getIdadeMinima() != 0
				&& seletor.getVacina().getIdadeMaxima() != 0
		 	) {
	        sql += AND + " VACINA.idadeMinima between " + seletor.getVacina().getIdadeMinima() + AND + seletor.getVacina().getIdadeMaxima() + AND + "VACINA.idadeMaxima between " + seletor.getVacina().getIdadeMinima() + AND + seletor.getVacina().getIdadeMaxima();
	    }
	    
	    
	    // ok ok
	    if (
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getIdadeMinima() != 0  
		 	) {
	        sql += AND + " VACINA.idadeMinima >=  " + seletor.getVacina().getIdadeMinima() +  AND + " VACINA.idadeMaxima >= " + seletor.getVacina().getIdadeMinima();
	    }
	    
	    
	    
	    if ( // OK ok
	    		seletor.getVacina() != null 
				&& seletor.getVacina().getIdadeMaxima() != 0  
		 	) {
	    	sql += AND + " VACINA.idadeMaxima <=  " + seletor.getVacina().getIdadeMaxima() +  AND + " VACINA.idadeMinima <= " + seletor.getVacina().getIdadeMaxima();
	    }
	    
	    
	    
	    if(
	    	seletor.getVacina() != null
	    	&& seletor.getVacina().isContraIndicacao()
	    	) {
	    	sql += AND + " VACINA.contraIndicacao = true ";
		    }
	    if(
	    		(seletor.getFabricanteDaVacina() != null 
	    		&& !seletor.getFabricanteDaVacina().getNome().isBlank() 
	    		&& !seletor.getFabricanteDaVacina().getNome().isEmpty())	
	    	) {
	    	sql += AND + " UPPER(FABRICANTE.nome) LIKE UPPER ('%" 
	    		   + seletor.getVacina().getFabricanteDaVacina().getNome() + "%')";
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
	    	 seletor.getEndereco() != null
	    	 && seletor.getEndereco().getBairro() != null
	    	 && !seletor.getEndereco().getBairro().isBlank()
	    	 && !seletor.getEndereco().getBairro().isEmpty()
	    	) {
	        sql += AND + " UPPER(ENDERECO.bairro) LIKE UPPER ('%" 
	        	   + seletor.getEndereco().getBairro() + "%')";
	    }
	    if (
	    	 seletor.getEndereco() != null && seletor.getEndereco().getCep() != null && !seletor.getEndereco().getCep().isBlank() && !seletor.getEndereco().getCep().isEmpty()) {
	        sql += AND + " UPPER(ENDERECO.cep) LIKE UPPER ('%"  + seletor.getEndereco().getCep() + "%')";
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
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		seletor.setUnidade(unidadeRepository.consultarPorId(resultado.getInt("id_Unidade")));
		FabricanteRepository fabricanteRepository = new FabricanteRepository();
		seletor.setFabricanteDaVacina(fabricanteRepository.consultarPorId(resultado.getInt("id_Fabricante")));
		return seletor;
	}

}
