package servicos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CsvExportacao {

    public CsvExportacao(ArrayList<String> lista, String nome) {
        String url = "jdbc:sqlite:src/databases/" + nome + ".db";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conexao = DriverManager.getConnection(url);
            if (conexao != null) {
                StringBuilder criar = new StringBuilder();
                String[] texto = lista.get(0).split(",");
                criar.append("CREATE TABLE IF NOT EXISTS " + nome + " (");
                for (Integer i = 0; i < lista.get(0).split(",").length; i++) {
                    criar.append(texto[Integer.parseInt(String.valueOf(i))] + " VARCHAR (45),");
                }
                criar.setLength(criar.length() - 1);
                criar.append(");");
                try (PreparedStatement stmt = conexao.prepareStatement(criar.toString())) {
                    stmt.execute();
                    System.out.println("Banco de dados atualizado com sucesso.");
                } catch (SQLException e) {
                    throw new RuntimeException("Erro ao salvar os dados: " + e);
                }

                StringBuilder inserir = new StringBuilder();
                Integer contar = 0;
                for (Integer i = 0; i < lista.size(); i++) {
                    if (contar == 0) {
                        inserir.setLength(0);
                        inserir.append("INSERT INTO " + nome + " (");
                        inserir.append(lista.get(0));
                        inserir.append(")");
                        contar++;
                    }
                    else {
                        inserir.append(" VALUES (" + lista.get(i));
                        inserir.append(");");
                        try (PreparedStatement stmt = conexao.prepareStatement(inserir.toString())) {
                            stmt.execute();
                            System.out.println("Exportação efetuada com sucesso.");
                        } catch (SQLException e) {
                            throw new RuntimeException("Erro ao salvar os dados: " + e);
                        }
                        i--;
                        contar = 0;
                    }
                }
                conexao.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
