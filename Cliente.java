package CRUD;

public class Cliente {
	public String nome, cpf;
	public float valorAPagar;

	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
		this.valorAPagar = 0;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public float getValorAPagar() {
		return valorAPagar;
	}

	public void adicionarValorAPagar(float valor) {
		valorAPagar += valor;
	}
}
