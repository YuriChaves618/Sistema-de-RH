
package projetofinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Dependentes {
    
    String nome;
    String cpf;
    String cpf_funcionario;
    Funcionario funcionario;
    
    Dependentes(){
    
    }
    
    public void cadastrarDependente(Funcionario funcionario){
        
        String sql = "INSERT INTO Dependente (cpf_funcionario, cpf, nome) VALUES (?, ?, ?)";
        
        cpf_funcionario = JOptionPane.showInputDialog(null, "Digite o CPF do funcionário que o dependente pertence.");
        if(cpf_funcionario.isEmpty()) {
            JOptionPane.showInputDialog(null, "CPF vazio!", "AVISO", 2);
            return;
        } 
        
        nome = JOptionPane.showInputDialog(null, "Digite o nome do Dependente: ","Cadastro",1);
        if(nome.isEmpty()){
            JOptionPane.showMessageDialog(null, "Nome vazio!","AVISO",2);
            return;
        }
        
        cpf = JOptionPane.showInputDialog(null, "Digite o CPF do Dependente: ","Cadastro",1);
        if(cpf.isEmpty()){
            JOptionPane.showMessageDialog(null, "CPF vazio!","AVISO",2);
            return;
        }
        
        try (Connection conect = ConexaoBanco.conectar();
            PreparedStatement statement = conect.prepareStatement(sql)) {
            statement.setString(1, cpf_funcionario);
            statement.setString(2, cpf);
            statement.setString(3, nome);
            
            statement.executeUpdate();

            System.out.println("Dados salvos com sucesso.");
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar dependente!\n");
            return;
        }
        
        this.funcionario = funcionario;
        JOptionPane.showMessageDialog(null, "Cadastro do Dependente Realizado com Sucesso!");
    }
    
    public void editarDependente(String novoNome, String novoCpf){
        nome = novoNome;
        
        String sql = "UPDATE Dependente SET nome = ? WHERE cpf = ?";
        
        try (Connection conn = ConexaoBanco.conectar(); 
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, novoCpf);
            
            int linhasAfetadas = statement.executeUpdate();
            
            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Dados do dependente atualizados!", "SUCESSO", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário com o CPF informado.");
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar os dados.", "AVISO", 2);
        }
    }
    
    public void excluirDependente(String cpf){
        
        String sql = "DELETE FROM Dependente WHERE cpf = ?";
        
        try (Connection conect = ConexaoBanco.conectar(); 
             PreparedStatement statement = conect.prepareStatement(sql)) {
            statement.setString(1, cpf);
            
            int linhasAfetadas = statement.executeUpdate();
            
            if(linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Dependente Excluído com Sucesso!");
            } else {
                JOptionPane.showMessageDialog(null,"Nenhum registro com o CPF informado.", "AVISO", 2);
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Erro ao tentar excluir dados.", "AVISO", 2);
        }
    }
    
    public String listarDependentes() {
        String sql = "SELECT * FROM Dependente";
        StringBuilder relatorioFinal = new StringBuilder();
               
        try (Connection conect = ConexaoBanco.conectar();
             PreparedStatement statement = conect.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while(rs.next()) {
                cpf_funcionario = rs.getString("cpf_funcionario");
                cpf = rs.getString("cpf");
                nome = rs.getString("nome");
                relatorioFinal.append("=== LISTA DE DEPENDENTES ===\n\n");
                relatorioFinal.append("Nome: ").append(nome).append(" | ")
                        .append("CPF: ").append(cpf).append("\n")
                        .append("–------------------------------------------------------------------------------------------------------\n");
            }
        } catch(SQLException e) {
            relatorioFinal.append("Erro ao buscar dados: ").append(e.getMessage());
        }
        return relatorioFinal.toString();
    }
    
}
