
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Pedido {

    /** Quantidade máxima de produtos de um pedido */
    private static final int MAX_PRODUTOS = 10;

    /** Lista de produtos armazenados no pedido */
    private Lista<Produto> produtos;

    /** Lista de pedidos cadastrados */
    static Lista<Pedido> pedidosCadastrados = new Lista<>();

    /** Quantidade de produtos no pedido */
    private int quantProdutos;

    /** Data de criação do pedido */
    private LocalDate dataPedido;

    /** Construtor do pedido */
    public Pedido() {
        produtos = new Lista<>();
        this.dataPedido = LocalDate.now();
        this.quantProdutos = 0;
    }

    /** Retorna a lista de produtos do pedido */
    public Lista<Produto> getProdutos() {
        return produtos;
    }

    /** Verifica se o pedido contém determinado produto */
    public boolean contemProduto(Produto produto) {
        if (produto == null) {
            return false;
        }

        for (int i = 0; i < quantProdutos; i++) {
            if (produtos.obter(i).equals(produto)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Inclui um produto no pedido.
     * @param novo Produto a ser incluído
     * @return Quantidade de produtos após a operação
     */
    public int incluirProduto(Produto novo) {
        if (novo != null && quantProdutos < MAX_PRODUTOS) {
            produtos.inserir(novo, quantProdutos);
            quantProdutos++;
        } else {
            System.out.println(
                "Produto nulo ou excedeu a quantidade de produtos permitida."
            );
        }

        return quantProdutos;
    }

    /** Calcula o valor total do pedido */
    public double valorFinal() {
        double total = 0.0;

        for (int i = 0; i < quantProdutos; i++) {
            total += produtos.obter(i).valorDeVenda();
        }

        return total;
    }

    /**
     * Retorna os dados completos do pedido.
     */
    @Override
    public String toString() {
        DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder retorno = new StringBuilder(
            "Pedido na data " + formato.format(dataPedido) + "\n"
        );

        for (int i = 0; i < quantProdutos; i++) {
            retorno.append(produtos.obter(i).toString()).append("\n");
        }

        retorno.append(
            String.format(
                Locale.forLanguageTag("pt-BR"),
                "Valor a pagar: R$ %.2f",
                valorFinal()
            )
        );

        return retorno.toString();
    }

    /**
     * Retorna o resumo do pedido em uma única linha.
     */
    public String resumo() {
        DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String valorFinalFormatado = String.format(
            Locale.forLanguageTag("pt-BR"),
            "%.2f",
            valorFinal()
        );

        return "Pedido com " + quantProdutos
            + " produtos em " + formato.format(dataPedido)
            + ", valor total de R$ " + valorFinalFormatado;
    }
}
