import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa um pedido de venda do comércio.
 * Os produtos do pedido são armazenados em uma lista flexível.
 */
public class Pedido {

	/** Último código atribuído a um pedido */
	private static int ultimoID = 0;

	/** Código identificador do pedido */
	private int idPedido;

	/** Data de criação do pedido */
	private LocalDate dataPedido;

	/** Lista dos produtos do pedido */
	private Lista<Produto> produtos;

	/**
	 * Cria um pedido sem produtos, com código sequencial e a data atual do sistema como data do pedido.
	 */
	public Pedido() {
		this.idPedido = ++ultimoID;
		this.dataPedido = LocalDate.now();
		this.produtos = new Lista<>();
	}

	/**
	 * Inclui um produto no final da lista de produtos deste pedido.
	 * @param novo O produto a ser incluído no pedido
	 * @return true se o produto foi incluído; false se o produto for nulo
	 */
	public boolean incluirProduto(Produto novo) {
		if (novo == null)
			return false;
		produtos.inserirFinal(novo);
		return true;
	}

	/**
	 * Representação em String do pedido: um cabeçalho com o código, a data e a quantidade
	 * de produtos, seguido dos produtos do pedido, um por linha.
	 * @return String com os dados do pedido
	 */
	@Override
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return String.format("Pedido %02d - %s - %d produto(s)\n", idPedido, formato.format(dataPedido), produtos.tamanho())
				+ produtos;
	}
}
