package model.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Contato;
import model.entity.Endereco;
import model.entity.Unidade;

public class UnidadeRepository implements BaseRepository<Unidade>{

	@Override
	public Unidade salvar(Unidade novaEntidade) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	@Override
	public boolean excluir(int id) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	@Override
	public boolean alterar(Unidade entidade) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	@Override
	public Unidade consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Unidade unidade = null;
		String query = "select id, idEndereco, idContato, nome from VACINAS.UNIDADE where id = " + id;
		try {
			resultado = stmt.executeQuery(query);
			if(resultado.next()) {
				unidade = this.converterParaObjeto(resultado);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar a unidade de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return unidade;
	}

	@Override
	public ArrayList<Unidade> consultarTodos() {
		// TODO Stub de método gerado automaticamente
		return null;
	}
	
	private Unidade converterParaObjeto(ResultSet resultado) throws SQLException {
		Unidade unidade = new Unidade();
		unidade.setId(resultado.getInt("id"));
		unidade.setNome(resultado.getString("nome"));
		EnderecoRepository enderecoRepository = new EnderecoRepository();
		Endereco enderecoDaUnidade = enderecoRepository.consultarPorId(resultado.getInt("idEndereco"));
		unidade.setEnderecoDaUnidade(enderecoDaUnidade);
		ContatoRepository contatoRepository = new ContatoRepository();
		Contato contatoDaUnidade = contatoRepository.consultarPorId(resultado.getInt("idContato"));
		unidade.setContatoDaUnidade(contatoDaUnidade);
		return unidade;
	}
	
}
