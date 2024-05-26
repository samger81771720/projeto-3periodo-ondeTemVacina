package model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Endereco;
import model.entity.Estoque;
import model.entity.Unidade;

public class EstoqueRepository implements BaseRepository<Estoque>{

	@Override
	public Estoque salvar(Estoque novaEntidade) {
		return null;
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
	public boolean alterar(Estoque entidade) {
		return false;
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
		String query = "select idUnidade, idVacina, quantidade, dataLote, validade from VACINAS.ESTOQUE";
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


	@Override
	public boolean excluir(int id) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

}
