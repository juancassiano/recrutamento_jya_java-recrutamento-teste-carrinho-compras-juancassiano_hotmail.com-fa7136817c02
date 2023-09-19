package br.com.improving.carrinho;

import static br.com.improving.carrinho.commons.ProdutoConstant.PRODUTO_VALIDO;
import static br.com.improving.carrinho.commons.ProdutoConstant.PRODUTO_VALIDO_2;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarrinhoComprasTest {
	private CarrinhoCompras carrinho;

	@BeforeEach
	public void setUp(){
		carrinho = new CarrinhoCompras();
	}

	@Test
	public void test_AdicionarItem(){
		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;

		carrinho.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);

		Collection<Item> itens = carrinho.getItens();
		assertThat(1).isEqualTo(itens.size());

		Item item = itens.iterator().next();
		assertThat(PRODUTO_VALIDO).isEqualTo(item.getProduto());
		assertThat(valorUnitario).isEqualTo(item.getValorUnitario());
		assertThat(quantidade).isEqualTo(item.getQuantidade());
	}

	@Test
	public void test_RetornaVerdadeiroParaRemoverItemPorProduto(){
		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;
		carrinho.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);

		assertThat(carrinho.removerItem(PRODUTO_VALIDO)).isTrue();
		assertThat(carrinho.getItens()).isEmpty();
	}

	@Test
	public void test_RetornaFalsooParaRemoverItemPorProduto(){
		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;
		carrinho.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);

		assertThat(carrinho.removerItem(PRODUTO_VALIDO_2)).isFalse();
		assertThat(carrinho.getItens()).isNotEmpty();
	}

	@Test
	public void test_RemoveItemPorPosicao(){
		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;
		carrinho.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);
		carrinho.adicionarItem(PRODUTO_VALIDO_2, valorUnitario, quantidade);

		assertThat(carrinho.removerItem(0)).isTrue();
		assertThat(carrinho.getItens()).hasSize(1);
	}

	@Test
	public void test_RetornaValorTotal(){
		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;
		carrinho.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);
		carrinho.adicionarItem(PRODUTO_VALIDO_2, valorUnitario, quantidade);

		BigDecimal valorTotalEsperado = valorUnitario.multiply(BigDecimal.valueOf(quantidade*2));
		assertThat(carrinho.getValorTotal()).isEqualTo(valorTotalEsperado);
	}

	@Test
	public void test_RetornaTodosOsItens(){
		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;
		carrinho.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);
		carrinho.adicionarItem(PRODUTO_VALIDO_2, valorUnitario, quantidade);

		Collection<Item> itens = carrinho.getItens();
		assertThat(itens).hasSize(2);

		Item primeiroItem = itens.stream().filter(item -> item.getProduto().equals(PRODUTO_VALIDO)).findFirst().orElse(null);
		assertThat(primeiroItem).isNotNull();
		assertThat(primeiroItem.getValorUnitario()).isEqualTo(valorUnitario);
		assertThat(primeiroItem.getQuantidade()).isEqualTo(quantidade);

		Item segundoItem = itens.stream().filter(item -> item.getProduto().equals(PRODUTO_VALIDO_2)).findFirst().orElse(null);
		assertThat(segundoItem).isNotNull();
		assertThat(segundoItem.getValorUnitario()).isEqualTo(valorUnitario);
		assertThat(segundoItem.getQuantidade()).isEqualTo(quantidade);

	}
}
