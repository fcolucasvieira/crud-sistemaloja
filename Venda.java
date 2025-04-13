package CRUD;

import java.util.ArrayList;
import java.util.List;

public class Venda {
	public Cliente clienteVenda;
	public List<Produto> produtosVenda;
	public float valorTotal;
	public formaPagamento formaPagamento;

	public Venda(Cliente clienteVenda, Produto produtoVenda, formaPagamento formaPagamento) {
		this.clienteVenda = clienteVenda;
		this.produtosVenda = new ArrayList<>();
		this.formaPagamento = formaPagamento;
	}

	
	public Cliente getClienteVenda() {
		return clienteVenda;
	}
	public List<Produto> getProdutosVenda() {
		return produtosVenda;
	}
	public float getValorTotal() {
		return valorTotal;
	}
	public formaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	
	public boolean realizarVenda(Estoque estoque, String idProduto, int quantidadeVenda) {
	    ArrayList<Produto> prodEncontrados = estoque.buscarProdutos(idProduto);
	    
	    if (prodEncontrados.isEmpty()) {
	        System.out.println("Nenhum produto encontrado com este ID.");
	        return false;
	    }
	    
	    if (formaPagamento == CRUD.formaPagamento.FICHA && clienteVenda == null) {
	        System.out.println("A venda na forma de pagamento FICHA requer um cliente associado.");
	        return false;
	    }
	    
	    for (Produto prod : prodEncontrados) {
	        if (prod.getQuantidade() >= quantidadeVenda) {
	            double totalVenda = prod.getValor() * quantidadeVenda;
	            prod.removerQuantidade(quantidadeVenda);
	            produtosVenda.add(prod);
	            valorTotal += totalVenda;
	            
	            if (formaPagamento == CRUD.formaPagamento.FICHA) {
	                clienteVenda.adicionarValorAPagar(valorTotal);
	                System.out.println("Venda adicionada ao saldo do cliente: " + clienteVenda.getNome());
	            }
	            
	            String nomeCliente = (clienteVenda != null) ? clienteVenda.getNome() : "Venda sem cliente";
	            System.out.println("Venda realizada: " + quantidadeVenda + "x " + prod.getNome() + " para " + nomeCliente + " via " + formaPagamento);
	            return true;
	        }
	    }
	    
	    System.out.println("Nenhum produto disponível com estoque suficiente para a quantidade desejada.");
	    return false;
	}
}
