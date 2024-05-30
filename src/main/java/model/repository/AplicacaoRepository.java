package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.entity.Aplicacao;

public class AplicacaoRepository implements BaseRepository<Aplicacao>{

	@Override
	public Aplicacao salvar(Aplicacao aplicacao) {
		String sql = "INSERT INTO VACINAS.APLICACAO (idPessoa, idVacina, idUnidade, dataAplicacao) VALUES (?, ?, ?,?)";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		try {
			stmt.setInt(1, aplicacao.getPessoaQueRecebeu().getId());
			stmt.setInt(2, aplicacao.getVacinaAplicada().getId());
			stmt.setInt(3, aplicacao.getUnidadeOndeAplicou().getId());
			if(aplicacao.getDataAplicacao() != null) {
				stmt.setDate(4, Date.valueOf(LocalDate.now()));
			}
			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				aplicacao.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova aplicacao do usuário " 
											      + aplicacao.getPessoaQueRecebeu().getNome());
			System.out.println("Erro: " + e.getMessage());
		}
		return aplicacao;
	}

	@Override
	public boolean excluir(int id) {
		return false;
	}

	@Override
	public boolean alterar(Aplicacao entidade) {
		return false;
	}

	@Override
	public Aplicacao consultarPorId(int id) {
		return null;
	}

	@Override
	public ArrayList<Aplicacao> consultarTodos() {
		return null;
	}

}
