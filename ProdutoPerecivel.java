import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Produto com data de validade. Recebe desconto quando a validade está próxima.
 */
public class ProdutoPerecivel extends Produto {

    /** Desconto para proximidade de validade: 25% */
    private static final double DESCONTO = 0.25;

    /** Prazo, em dias, para conceder o desconto por proximidade da validade */
    private static final int PRAZO_DESCONTO = 7;

    /** Data de validade do produto. Não pode ser anterior à data da criação */
    private LocalDate dataDeValidade;

    /**
     * Construtor completo. Causa exceção em caso de valores inválidos.
     * @param descricao Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de compra do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para a venda (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     * @param validade Data de validade do produto, que não pode ser anterior à data atual.
     * @throws IllegalArgumentException em caso dos limites acima serem desrespeitados.
     */
    public ProdutoPerecivel(String descricao, double precoCusto, double margemLucro, int quant, int estoqueMinimo, LocalDate validade) {
        super(descricao, precoCusto, margemLucro, quant, estoqueMinimo);
        if (validade.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Validade anterior ao dia de hoje!");
        dataDeValidade = validade;
    }

    /**
     * Retorna o valor de venda do produto, considerando seu preço de custo, margem de lucro e
     * dias de validade. Se faltarem 7 dias ou menos para o fim da validade, é concedido desconto de 25%.
     * @return Valor de venda do produto (double, positivo)
     */
    @Override
    public double valorDeVenda() {
        double desconto = 0d;
        long diasValidade = LocalDate.now().until(dataDeValidade, ChronoUnit.DAYS);
        if (diasValidade <= PRAZO_DESCONTO)
            desconto = DESCONTO;
        return valorBase() * (1 - desconto);
    }

    /**
     * Descrição em string do produto, contendo seu código, descrição, valor de venda e data de validade.
     * @return String com o formato: [CÓDIGO] - [DESCRIÇÃO]: [VALOR DE VENDA] - Válido até [DD/MM/AAAA]
     */
    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return super.toString() + " - Válido até " + formato.format(dataDeValidade);
    }
}
