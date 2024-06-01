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
