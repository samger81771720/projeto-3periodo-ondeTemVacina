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
	            throw new SQLException("A tentaiva de inserção não afetou nenhuma linha.");
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
			System.out.println("Erro ao tentar excluir o estoque com os id´s informados.");
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
}
