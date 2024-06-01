package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entity.Estoque;
import model.filtro.VacinaFiltro;
import model.seletor.VacinaSeletor;

public class EstoqueRepository implements BaseRepository<Estoque>{

	
	@Override
	public Estoque salvar(Estoque novoEstoque) {
	    String query = "insert into VACINAS.ESTOQUE (idUnidade, idVacina, quantidade, dataLote, validade) values (?, ?, ?, ?, ?)";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
	    try {
	        this.preencherParametrosParaInsertOuUpdate(pstmt, novoEstoque, false);
	        int linhasAfetadas = pstmt.executeUpdate();
	        if (linhasAfetadas == 1) {
	            return novoEstoque;
	        } else {
	            throw new SQLException("A tentaiva de inser��o de um registro no "
	            													+ "estoque da unidade n�o afetou nenhuma linha.");
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro na tentativa de salvar um novo estoque da unidade "
	                             + novoEstoque.getUnidade().getNome() + " no banco de dados.");
	        System.out.println("Erro: " + erro.getMessage());
	        return null;
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	}

	public boolean excluirEstoqueDaUnidade(int idUnidade, int idVacina) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "delete from VACINAS.ESTOQUE where idUnidade =  " 
									  + idUnidade +  " and idVacina = " + idVacina;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar excluir o estoque com os id�s informados.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Estoque estoqueAnterior) {
		boolean alterou = false;
	    String query = "update VACINAS.ESTOQUE set idUnidade = ?, idVacina = ?, quantidade = ?, "
	    						  + "dataLote = ?, validade = ? where idUnidade = ? and idVacina = ? ";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    try {
	    	this.preencherParametrosParaInsertOuUpdate(pstmt, estoqueAnterior, true);
	        alterou = pstmt.executeUpdate() > 0;
	    } catch (SQLException erro) {
	        System.out.println("Erro ao tentar atualizar o estoque da unidade " + estoqueAnterior.getUnidade().getNome());
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return alterou;
	}

	public List<Estoque> consultarEstoquesDaUnidade(int idUnidade) {
		ArrayList<Estoque> estoqueDaUnidade = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "select idUnidade, idVacina, quantidade, dataLote, "
								  + "validade from VACINAS.ESTOQUE where idUnidade = " 
								  + idUnidade;
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				estoqueDaUnidade.add(this.converterParaObjeto(resultado));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao executar o estoque da unidade de id " + idUnidade);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return estoqueDaUnidade;
	}
	
	@Override
	public ArrayList<Estoque> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ArrayList<Estoque> listaDeTodosEstoques = new ArrayList<>();
		ResultSet resultado = null;
		String query = "select U.id as idUnidade, E.idVacina, "
							      + "E.quantidade, E.dataLote, E.validade from VACINAS.ESTOQUE E "
							      + "inner join VACINAS.UNIDADE U on E.idUnidade = U.id order by U.nome asc";
		try{
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Estoque estoque = this.converterParaObjeto(resultado);
				listaDeTodosEstoques.add(estoque);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar a lista com os estoques de todas as unidades.");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaDeTodosEstoques;
	}

		@Override
	public Estoque consultarPorId(int id) {
		return null;
	}
	
	@Override
	public boolean excluir(int id) {
		return false;
	}
	
	public List<VacinaFiltro> consultarComFiltros(VacinaSeletor seletor){
		
		ArrayList<VacinaFiltro> listagemComFiltrosSelecionados = new ArrayList<>();
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		String sql = "select "
				+ "	VACINA.id as idVacina, "
				+ "	VACINA.nome as nomeVacina, "
				+ "	VACINA.categoria as categoriaVacina, "
				+ "	VACINA.idadeMinima as idadeMinimaVacina, "
				+ "	VACINA.idadeMaxima as idadeMaximaVacina, "
				+ "	VACINA.contraIndicacao as contraIndicacaoVacina, "
				+ "	FABRICANTE.nome as nomeFabricante, "
				+ "	FABRICANTE.id as idFabricante, "
				+ "	UNIDADE.id as idUnidade, "
				+ "	UNIDADE.nome as nomeUnidade, "
				+ "	ENDERECO.bairro as bairroUnidade, "
				+ "	ENDERECO.cep as cepUnidade "
				+ " FROM "
				+ "    VACINAS.VACINA "
				+ " JOIN "
				+ "    VACINAS.FABRICANTE ON VACINAS.VACINA.idFabricante = VACINAS.FABRICANTE.id "
				+ " JOIN "
				+ "    VACINAS.ESTOQUE ON VACINAS.VACINA.id = VACINAS.ESTOQUE.idVacina "
				+ " JOIN "
				+ "    VACINAS.UNIDADE ON VACINAS.ESTOQUE.idUnidade = VACINAS.UNIDADE.id "
				+ " JOIN "
				+ "    VACINAS.ENDERECO ON VACINAS.UNIDADE.idEndereco = VACINAS.ENDERECO.id "
				+ " JOIN "
				+ "    VACINAS.CONTATO on VACINAS.UNIDADE.idContato = VACINAS.CONTATO.id where VACINAS.ESTOQUE.quantidade > 0 ";
		
		// sql = preencherFiltros(seletor,sql);
		
		if(seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite(); 
			sql += " OFFSET " + seletor.getOffSet();
		}
		
		try {
			resultado = stmt.executeQuery(sql);
			while(resultado.next()) {
				VacinaFiltro vacinaFiltro = construirDoResultSet(resultado);
				listagemComFiltrosSelecionados.add(vacinaFiltro);
			}
		} catch(SQLException erro){
			System.out.println(
					"Erro durante a execu��o do m�todo \"consultarComFiltros\" ao consultar "
				 + "as unidades com seus respectivos estoques de vacinas de acordo com o  filtro selecionado."
					);
			System.out.println("Erro: "+erro.getMessage());
		} finally{
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listagemComFiltrosSelecionados;
	}
	
	private Estoque converterParaObjeto(ResultSet resultado) throws SQLException{
		Estoque estoque = new Estoque();
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		estoque.setUnidade(unidadeRepository.consultarPorId(resultado.getInt("idUnidade")));
		VacinaRepository vacinaRepositor = new VacinaRepository();
		estoque.setVacina(vacinaRepositor.consultarPorId(resultado.getInt("idVacina")));
		estoque.setQuantidade(resultado.getInt("quantidade"));
		if(resultado.getDate("dataLote") != null) {
			estoque.setDataLote(resultado.getDate("dataLote").toLocalDate());;
		}
		if(resultado.getDate("validade") != null) {
			estoque.setValidade(resultado.getDate("validade").toLocalDate());
		}
		return estoque;
	}
	
	private void preencherParametrosParaInsertOuUpdate	(PreparedStatement pstmt, Estoque novoEstoque,  boolean isUpdate) 
			throws SQLException  {
	    pstmt.setInt(1, novoEstoque.getUnidade().getId());
		pstmt.setInt(2, novoEstoque.getVacina().getId());
		pstmt.setInt(3, novoEstoque.getQuantidade());
		if(novoEstoque.getDataLote() != null) {
			pstmt.setDate(4, Date.valueOf(novoEstoque.getDataLote()));
		}
		if(novoEstoque.getValidade() != null) {
			pstmt.setDate(5, Date.valueOf(novoEstoque.getValidade()));
		}
		 if (isUpdate) {
	         pstmt.setInt(6, novoEstoque.getUnidade().getId());
	         pstmt.setInt(7, novoEstoque.getVacina().getId());
	      }
	}
	
	/*private String preencherFiltros(VacinaSeletor seletor, String sql) {

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
	}*/
	
	private VacinaFiltro construirDoResultSet(ResultSet resultado) throws SQLException{
		
		VacinaFiltro vacinaFiltro = new VacinaFiltro();
		
		VacinaRepository vacinaRepository = new VacinaRepository();
		vacinaFiltro.setVacina(vacinaRepository.consultarPorId(resultado.getInt("idVacina")));
		
		FabricanteRepository fabricanteRepository = new FabricanteRepository();
		vacinaFiltro.setFabricante(fabricanteRepository.consultarPorId(resultado.getInt("idFabricante")));
		
		UnidadeRepository unidadeRepository = new UnidadeRepository();
		vacinaFiltro.setUnidade(unidadeRepository.consultarPorId(resultado.getInt("idUnidade")));
		
		return vacinaFiltro;
	}

}
