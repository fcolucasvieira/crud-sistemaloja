package CRUD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

class Estoque {
	private ArrayList<Produto> produtos;

	public Estoque() {
		this.produtos = new ArrayList<>();
	}

	public ArrayList<Produto> getProdutos() {
		return produtos;
	}

	public void adicionarProduto(Produto addProduto) {
		for (Produto item : produtos) {
			if (item.nome.equals(addProduto.nome)) {
				System.out.println("Produto com este nome já consta no estoque.");
				return;
			}
		}
		produtos.add(addProduto);
		System.out.println("Produto adicionado com sucesso!");
	}

	public void removerProduto(String idProduto) {
		Iterator<Produto> iterator = produtos.iterator();
		while (iterator.hasNext()) {
			Produto item = iterator.next();
			if (item.id.equals(idProduto)) {
				iterator.remove();
				System.out.println("Produto removido com sucesso!");
				return;
			}
		}
		System.out.println("Produto não consta no estoque.");
	}

	public void listarProdutos() {
		if (produtos.isEmpty()) {
			System.out.println("O estoque está vazio.");
			return;
		}
		System.out.println("\n----- ESTOQUE -----");
		for (Produto item : produtos) {
			item.exibirDetalhes();
		}
	}

	public ArrayList<Produto> buscarProdutos(String id) {
	    ArrayList<Produto> produtosEncontrados = new ArrayList<>();
	    for (Produto produtoBusca : produtos) {
	    	if (produtoBusca.getId().equals(id)) {
	            produtosEncontrados.add(produtoBusca);
	        }
	    }
	    return produtosEncontrados;
	}

	public void salvarArquivo() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("estoque.dat"))) {
			out.writeObject(produtos);
			System.out.println("Dados salvos com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao salvar os dados: " + e.getMessage());
		}
	}

	public void carregarArquivo() {
		File file = new File("estoque.dat");
		if (!file.exists()) {
			System.out.println("Arquivo de estoque não encontrado.");
			return;
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			@SuppressWarnings("unchecked")
			ArrayList<Produto> produtosCarregados = (ArrayList<Produto>) in.readObject();
			produtos = produtosCarregados;
			System.out.println("Dados carregados com sucesso!");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erro ao carregar os dados. Criando um novo estoque...");
			produtos = new ArrayList<>();
		}
	}
}

