package model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Unidade;
import model.entity.Vacina;

public class VacinaRepository implements BaseRepository<Vacina>{

	@Override
	public Vacina salvar(Vacina novaEntidade) {
		// TODO Stub de m�todo gerado automaticamente
		return null;
	}

	@Override
	public boolean excluir(int id) {
		// TODO Stub de m�todo gerado automaticamente
		return false;
	}

	@Override
	public boolean alterar(Vacina entidade) {
		// TODO Stub de m�todo gerado automaticamente
		return false;
	}
	
	/*
	 O ResultSet � uma tabela de resultados da consulta SQL.
	Ap�s executeQuery, o ResultSet cont�m as linhas e colunas retornadas pela consulta.
	Voc� usa next() para iterar sobre as linhas do ResultSet.
	Voc� pode acessar os dados da linha atual do ResultSet usando m�todos como getInt, getString, etc.
	A fun��o converterParaObjeto mapeia esses valores para um objeto da sua classe de dom�nio.
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

	@Override
	public ArrayList<Vacina> consultarTodos() {
		// TODO Stub de m�todo gerado automaticamente
		return null;
	}
	
}
