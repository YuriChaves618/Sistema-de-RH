
package projetofinal;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Funcionario {
    String nome;
    String cpf;
    String cargo;
    double salario;
    
    public void cadastrar(){
        
        String sql = "INSERT INTO Funcionario (cpf, nome, salario, cargo) VALUES (?, ?, ?, ?)";
        
        JOptionPane.showMessageDialog(null,"Funcionário cadastrado com sucesso!\n\n"
                + "Nome: "+ nome
                + "\nCPF: "+ cpf
                + "\nCargo: "+ cargo
                + "\nSalário: "+ salario);
        try (Connection conect = ConexaoBanco.conectar();
            PreparedStatement statement = conect.prepareStatement(sql)) {
            String salarioString = String.valueOf(salario);
            statement.setString(1, cpf);
            statement.setString(2, nome);
            statement.setString(3, salarioString);
            statement.setString(4, cargo);
            
            statement.executeUpdate();
            
            System.out.println("Dados salvos com sucesso.");
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Funcionário!", "AVISO", 2);
            return;
        }
    }
    public void editar(String novoNome,String novoCargo, double novoSalario, String novoCpf){
        String sql = "UPDATE Funcionario SET nome = ?, salario = ?, cargo = ? WHERE cpf = ?";
        
        nome = novoNome;
        cargo = novoCargo;
        salario = novoSalario;
        
        String salarioString = String.valueOf(salario);
        
        try (Connection conect = ConexaoBanco.conectar(); 
             PreparedStatement statement = conect.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, salarioString);
            statement.setString(3, cargo);
            statement.setString(4, novoCpf);
            
            int linhasAfetadas = statement.executeUpdate();
            
            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Dados do funcionário atualizados!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário com o CPF informado.");
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar os dados.", "AVISO", 2);
        }
    }
    
    public void excluir(String cpf){
        String sql = "DELETE FROM Funcionario WHERE cpf = ?";
        
        try (Connection conect = ConexaoBanco.conectar(); 
             PreparedStatement statement = conect.prepareStatement(sql)) {
            statement.setString(1, cpf);
            
            int linhasAfetadas = statement.executeUpdate();
            
            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null,"Funcionário excluído.");   
            } else {
                JOptionPane.showMessageDialog(null,"Nenhum registro com o CPF informado.", "AVISO", 2);
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Erro ao tentar excluir dados.", "AVISO", 2);
        }
    }
    
    public String exibir() {
        String sql = "SELECT * FROM Funcionario";
        StringBuilder relatorioFinal = new StringBuilder();
        
        try (Connection conect = ConexaoBanco.conectar();
             PreparedStatement statement = conect.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            
            while(rs.next()) {
                cpf = rs.getString("cpf");
                nome = rs.getString("nome");
                salario = rs.getDouble("salario");
                cargo = rs.getString("cargo"); 
                relatorioFinal.append("=== LISTA DE FUNCIONÁRIOS ===\n\n");
                relatorioFinal.append("Nome: ").append(nome).append(" | ")
                        .append("CPF: ").append(cpf).append(" | ")
                        .append("Cargo: ").append(cargo).append(" | ")
                        .append("Salário: ").append(salario).append("\n")
                        .append("–------------------------------------------------------------------------------------------------------------\n");
            }
        } catch(SQLException e) {
            relatorioFinal.append("Erro ao buscar dados: ").append(e.getMessage());
        }
        return relatorioFinal.toString();
    }
}
