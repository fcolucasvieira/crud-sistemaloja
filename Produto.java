package CRUD;

import java.io.Serializable;

class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	public String id, nome;
	public float valor;
	public int quantidade;

	public Produto(String id, String nome, float valor, int quantidade) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
	}
	public String getNome() {
		return nome;
	}

	public float getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public String getId() {
		return id;
	}

	public void exibirDetalhes() {
		System.out.println("\n----- INFORMAÇÕES DO PRODUTO -----");
		System.out.printf("Nome: %s\nID: %s\nValor: R$ %.2f\nQuantidade: %d\n", nome, id, valor, quantidade);
	}

	public void adicionarQuantidade(int addQuantidade) {
		if (addQuantidade > 0) {
			quantidade += addQuantidade;
			System.out.println("Quantidade adicionada com sucesso!");
		} else {
			System.out.println("Quantidade inválida.");
		}
	}

	public void removerQuantidade(int removeQuantidade) {
		if (removeQuantidade <= 0) {
			System.out.println("Quantidade inválida.");
		} else if (removeQuantidade > quantidade) {
			System.out.println("Quantidade insuficiente no estoque.");
		} else {
			quantidade -= removeQuantidade;
			System.out.println("Quantidade removida com sucesso!");
		}
	}

	public void atualizarValor(float novoValor) {
		if (novoValor > 0) {
			valor = novoValor;
			System.out.println("Valor alterado com sucesso!");
		} else {
			System.out.println("Valor inválido.");
		}
	}

	public void atualizarQuantidade(int novaQuantidade) {
		if (novaQuantidade > 0) {
			quantidade = novaQuantidade;
			System.out.println("Quantidade atualizada com sucesso!");
		} else {
			System.out.println("Quantidade inválida.");
		}
	}
}

