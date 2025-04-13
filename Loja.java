package CRUD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Loja {
	private Estoque estoque;
	private Usuario usuarioAdmin;
	private ArrayList<Cliente> clientes;
	private Scanner sc;

	public Loja() {
		this.estoque = new Estoque();
		this.usuarioAdmin = new Usuario("Lucas", "2306");
		this.clientes = new ArrayList<>();
		this.sc = new Scanner(System.in);
	}

	public void menu() {
		boolean verificar = false;
		while (!verificar) {
			System.out.println("----- LOGIN -----");
			System.out.println("Usuário: ");
			String usuario = sc.nextLine();
			System.out.println("Senha: ");
			String senha = sc.nextLine();

			if (usuarioAdmin.autenticar(usuario, senha)) {
				verificar = true;
				System.out.println("Acesso concedido!");
			} else {
				System.out.println("Acesso negado! Usuário ou senha incorretos.");
			}
		}

		int opcao;
		do {
			System.out.println("\n----- MENU -----");
			System.out.println(
					"\n1. Adicionar produto.\n2. Remover produto.\n3. Buscar produto.\n4. Listar produtos.\n5. Atualizar produto.\n6. Adicionar cliente.\n7. Remover cliente.\n8. Listar clientes.\n9. Registrar venda.\n10. Salvar estoque.\n11. Carregar estoque.\n12. Sair.");
			System.out.println("\nEscolha uma opção: ");
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1:
				adicionarProduto();
				break;
			case 2:
				removerProduto();
				break;
			case 3:
				buscarProduto();
				break;
			case 4:
				estoque.listarProdutos();
				break;
			case 5:
				atualizarProduto();
				break;
			case 6:
				adicionarCliente();
				break;
			case 7:
				removerCliente();
				break;
			case 8:
				listarClientes();
				break;
			case 9:
				registrarVenda();
				break;
			case 10:
				estoque.salvarArquivo();
				break;
			case 11:
				estoque.carregarArquivo();
				break;
			case 12:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida.");
			}
		} while (opcao != 12);
	}

	private void adicionarProduto() {
		System.out.println("Nome: ");
		String nome = sc.nextLine();
		System.out.println("ID: ");
		String id = sc.nextLine();
		System.out.println("Valor(R$): ");
		float valor = sc.nextFloat();
		sc.nextLine();
		System.out.println("Quantidade: ");
		int quantidade = sc.nextInt();
		sc.nextLine();

		if (nome.isEmpty() || id.isEmpty() || valor <= 0) {
			System.out.println("Dados inválidos. Produto não adicionado.");
			return;
		}

		Produto novoProduto = new Produto(id, nome, valor, quantidade);
		estoque.adicionarProduto(novoProduto);

	}

	private void removerProduto() {
		System.out.println("ID do produto a remover: ");
		String id = sc.nextLine();

		ArrayList<Produto> encontrados = new ArrayList<>();

		for (Produto item : estoque.getProdutos()) {
			if (item.id.equals(id)) {
				encontrados.add(item);
			}
		}

		if (encontrados.isEmpty()) {
			System.out.println("Produto não encontrado.");
			return;
		}

		System.out.println("\n----- PRODUTOS ENCONTRADOS -----");
		for (int i = 0; i < encontrados.size(); i++) {
			System.out.println("\nOpção " + (i + 1) + ":");
			encontrados.get(i).exibirDetalhes();
		}

		System.out.println("\nEscolha a opção do produto que deseja remover (1 - " + encontrados.size() + "): ");
		int opcao = sc.nextInt();
		sc.nextLine();

		if (opcao < 1 || opcao > encontrados.size()) {
			System.out.println("Opção inválida.");
			return;
		}

		Produto produtoRemovido = encontrados.get(opcao - 1);
		estoque.getProdutos().remove(produtoRemovido);
		System.out.println("Produto removido com sucesso!");
	}

	private void buscarProduto() {
		System.out.println("ID do produto a buscar: ");
		String id = sc.nextLine();
		ArrayList<Produto> encontrados = new ArrayList<>();

		for (Produto item : estoque.getProdutos()) {
			if (item.id.equals(id)) {
				encontrados.add(item);
			}
		}

		if (encontrados.isEmpty()) {
			System.out.println("Produto não encontrado.");
		} else {
			System.out.println("\n----- PRODUTOS ENCONTRADOS -----");
			for (int i = 0; i < encontrados.size(); i++) {
				encontrados.get(i).exibirDetalhes();
			}
		}
	}

	private void atualizarProduto() {
		System.out.println("ID do produto a atualizar: ");
		String id = sc.nextLine();

		ArrayList<Produto> encontrados = new ArrayList<>();

		for (Produto item : estoque.getProdutos()) {
			if (item.id.equals(id)) {
				encontrados.add(item);
			}
		}

		if (encontrados.isEmpty()) {
			System.out.println("Produto não encontrado.");
			return;
		}

		if (encontrados.size() > 1) {
			System.out.println("Foram encontrados vários produtos com o mesmo ID. Escolha uma opção para atualizar:");
			for (int i = 0; i < encontrados.size(); i++) {
				System.out.println((i + 1) + ". " + "Nome: " + encontrados.get(i).getNome() + " | Quantidade: "
						+ encontrados.get(i).getQuantidade() + " | Valor: R$ " + encontrados.get(i).getValor());
			}

			System.out.println("Escolha o número do produto que deseja atualizar (1 - " + encontrados.size() + "): ");
			int opcao = sc.nextInt();
			sc.nextLine();

			if (opcao < 1 || opcao > encontrados.size()) {
				System.out.println("Opção inválida.");
				return;
			}

			Produto produtoSelecionado = encontrados.get(opcao - 1);
			System.out.println("Produto selecionado: ");
			produtoSelecionado.exibirDetalhes();

			System.out.println("Atualizar: ");
			System.out.println("\n1. Valor. \n2. Quantidade.");
			int escolha = sc.nextInt();
			sc.nextLine();

			if (escolha == 1) {
				System.out.println("Novo valor(R$):");
				float novoValor = sc.nextFloat();
				sc.nextLine();
				produtoSelecionado.atualizarValor(novoValor);
			} else if (escolha == 2) {
				System.out.println("Nova quantidade: ");
				int novaQuantidade = sc.nextInt();
				sc.nextLine();
				produtoSelecionado.atualizarQuantidade(novaQuantidade);
			} else {
				System.out.println("Opção inválida.");
			}

		} else {
			Produto produto = encontrados.get(0);
			System.out.println("Produto encontrado: ");
			produto.exibirDetalhes();

			System.out.println("Atualizar: ");
			System.out.println("\n1. Valor. \n2. Quantidade");
			int opcao = sc.nextInt();
			sc.nextLine();

			if (opcao == 1) {
				System.out.println("Novo valor(R$):");
				float novoValor = sc.nextFloat();
				sc.nextLine();
				produto.atualizarValor(novoValor);
			} else if (opcao == 2) {
				System.out.println("Nova quantidade: ");
				int novaQuantidade = sc.nextInt();
				sc.nextLine();
				produto.atualizarQuantidade(novaQuantidade);
			} else {
				System.out.println("Opção inválida.");
			}
		}
	}

	private void adicionarCliente() {
		System.out.println("Nome do Cliente: ");
		String nome = sc.nextLine();
		System.out.println("CPF do Cliente: ");
		String cpf = sc.nextLine();

		clientes.add(new Cliente(nome, cpf));
		System.out.println("Cliente cadastrado com sucesso!");
	}

	private void removerCliente() {
		System.out.println("CPF do Cliente a remover: ");
		String removerCPF = sc.nextLine();

		Iterator<Cliente> iterator = clientes.iterator();
		while (iterator.hasNext()) {
			Cliente cliente = iterator.next();
			if (cliente.getCpf().equals(removerCPF)) {
				iterator.remove();
				System.out.println("Cliente removido.");
				return;
			}
		}

		System.out.println("Cliente não encontrado.");
	}

	private void listarClientes() {
		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
			return;
		}
		System.out.println("\n----- LISTA DE CLIENTES -----");
		for (Cliente cliente : clientes) {
			System.out.printf("Nome: %s | CPF: %s | Saldo devedor: R$ %.2f\n", cliente.getNome(), cliente.getCpf(),
					cliente.getValorAPagar());
		}
	}

	public void registrarVenda() {
	    System.out.println("Formas de pagamento:");
	    System.out.println("1 - Dinheiro.");
	    System.out.println("2 - Pix.");
	    System.out.println("3 - Cartão.");
	    System.out.println("4 - Ficha.");
	    System.out.print("Forma de pagamento: ");
	    int opcaoPagamento = sc.nextInt();
	    sc.nextLine();

	    formaPagamento formaPagamento = null;
	    switch (opcaoPagamento) {
	        case 1:
	            formaPagamento = formaPagamento.DINHEIRO;
	            break;
	        case 2:
	            formaPagamento = formaPagamento.PIX;
	            break;
	        case 3:
	            formaPagamento = formaPagamento.CARTAO;
	            break;
	        case 4:
	            formaPagamento = formaPagamento.FICHA;
	            break;
	        default:
	            System.out.println("Opção de pagamento inválida.");
	            return;
	    }

	    Cliente clienteVenda = null;
	    if (formaPagamento == formaPagamento.FICHA) {
	        System.out.println("Clientes para venda: ");
	        listarClientes();
	        System.out.print("CPF do cliente: ");
	        String clienteCpf = sc.nextLine();
	        
	        for (Cliente cliente : clientes) {
	            if (cliente.getCpf().equals(clienteCpf)) {
	                clienteVenda = cliente;
	                break;
	            }
	        }
	        if (clienteVenda == null) {
	            System.out.println("Cliente não encontrado! Venda na forma FICHA requer um cliente.");
	            return;
	        }
	    }

	    System.out.print("ID do produto: ");
	    String produtoId = sc.nextLine();

	    ArrayList<Produto> produtosEncontrados = estoque.buscarProdutos(produtoId);
	    if (produtosEncontrados.isEmpty()) {
	        System.out.println("Nenhum produto encontrado com esse ID.");
	        return;
	    }

	    System.out.println("\nProdutos disponíveis com este ID:");
	    for (int i = 0; i < produtosEncontrados.size(); i++) {
	        Produto prod = produtosEncontrados.get(i);
	        System.out.println(i + " - " + prod.getNome() + " | Preço: R$" + prod.getValor() + " | Quantidade: " + prod.getQuantidade());
	    }

	    System.out.print("Escolha a opção do produto desejado: ");
	    int opcao = sc.nextInt();
	    sc.nextLine();

	    if (opcao < 0 || opcao >= produtosEncontrados.size()) {
	        System.out.println("Opção inválida.");
	        return;
	    }

	    Produto produtoVenda = produtosEncontrados.get(opcao);

	    System.out.print("Quantidade: ");
	    int quantidadeVenda = sc.nextInt();
	    sc.nextLine();

	    if (quantidadeVenda <= 0 || quantidadeVenda > produtoVenda.getQuantidade()) {
	        System.out.println("Quantidade inválida ou insuficiente no estoque.");
	        return;
	    }

	    Venda venda = new Venda(clienteVenda, produtoVenda, formaPagamento);
	    boolean sucesso = venda.realizarVenda(estoque, produtoId, quantidadeVenda);

	    if (sucesso) {
	        System.out.println("Venda realizada com sucesso! Total: R$" + venda.getValorTotal());
	        if (formaPagamento == formaPagamento.FICHA) {
	            System.out.println("Novo saldo do cliente: R$" + clienteVenda.getValorAPagar());
	        }
	    } else {
	        System.out.println("Falha na venda.");
	    }
	}


	public static void main(String[] args) {
		Loja loja = new Loja();
		loja.menu();
	}
}
