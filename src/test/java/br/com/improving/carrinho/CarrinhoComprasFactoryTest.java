package br.com.improving.carrinho;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;


import static br.com.improving.carrinho.commons.ProdutoConstant.PRODUTO_VALIDO;
import static br.com.improving.carrinho.commons.ProdutoConstant.PRODUTO_VALIDO_2;

public class CarrinhoComprasFactoryTest {
	private CarrinhoComprasFactory factory;

	@BeforeEach
	public void setUp(){
		factory = new CarrinhoComprasFactory();
	}

	@Test
	public void test_CriaCarrinhoParaNovoCliente(){
		String identificacaoCliente = "cliente1";
		CarrinhoCompras carrinhoCompras = factory.criar(identificacaoCliente);

		assertThat(carrinhoCompras).isNotNull();
		assertThat(factory.criar(identificacaoCliente)).isEqualTo(carrinhoCompras);
	}

	@Test
	public void test_RetornaCarrinhoParaClienteExistente(){
		String identificacaoCliente = "cliente2";
		CarrinhoCompras carrinho = factory.criar(identificacaoCliente);

		CarrinhoCompras carrinhoExistente = factory.criar(identificacaoCliente);

		assertThat(carrinhoExistente).isEqualTo(carrinho);
	}

	@Test
	public void testInvalidarCarrinhoExistente() {
		String identificacaoCliente = "cliente3";
		factory.criar("cliente3");
		boolean invalidado = factory.invalidar(identificacaoCliente);
		assertThat(invalidado).isTrue();
	}
	@Test
	public void testInvalidarCarrinhoInexistente() {
		String identificacaoCliente = "cliente4";

		boolean invalidado = factory.invalidar(identificacaoCliente);

		assertThat(invalidado).isFalse();
	}

	@Test
	public void test_RetornaZeroParaTicketZerado(){
		String cliente1 = "cliente1";
		CarrinhoCompras carrinho1 = factory.criar(cliente1);
		assertThat(factory.getValorTicketMedio()).isZero();

	}

	@Test
	public void test_RetornaValorTicketMedio(){
		String cliente1 = "cliente1";
		String cliente2 = "cliente2";

		CarrinhoCompras carrinho1 = factory.criar(cliente1);
		CarrinhoCompras carrinho2 = factory.criar(cliente2);

		BigDecimal valorUnitario = new BigDecimal("10.00");
		int quantidade = 3;
		carrinho1.adicionarItem(PRODUTO_VALIDO, valorUnitario, quantidade);
		carrinho2.adicionarItem(PRODUTO_VALIDO_2, valorUnitario, quantidade);

		BigDecimal valorTicketMedioEsperado = valorUnitario.multiply(BigDecimal.valueOf(quantidade * 2))
				.divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP);
		assertThat(factory.getValorTicketMedio()).isEqualTo(valorTicketMedioEsperado);
	}
}