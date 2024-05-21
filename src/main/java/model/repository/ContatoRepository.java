package model.repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.entity.Contato;

public class ContatoRepository  implements BaseRepository<Contato> {

	@Override
	public Contato salvar(Contato novaEntidade) {
		// TODO Stub de método gerado automaticamente
		return null;
	}

	@Override
	public boolean excluir(int id) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	@Override
	public boolean alterar(Contato entidade) {
		// TODO Stub de método gerado automaticamente
		return false;
	}

	
	
	@Override
	public Contato consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Contato contato = null;
		String query=" select id, telefone, email from VACINAS.CONTATO where id = "+id;
		try {
			resultado = stmt.executeQuery(query);
		if(resultado.next()) {
			contato = new Contato();
			contato.setId(resultado.getInt("id"));
			contato.setTelefone(resultado.getString("telefone"));
			contato.setEmail("email");
		}
		} catch (SQLException erro) {
			System.out.println("Erro ao tentar consultar o contato de id "+id);
			System.out.println("Erro: "+erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return contato;
	}

	@Override
	public ArrayList<Contato> consultarTodos() {
		// TODO Stub de método gerado automaticamente
		return null;
	}


	
}
