import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa um produto à venda no comércio.
 * Cada produto recebe um código único e sequencial no momento da sua criação, a partir de 20000.
 */
public abstract class Produto implements Comparable<Produto> {

    /** Margem de lucro padrão do sistema */
    private static final double MARGEM_PADRAO = 0.2;

    /** Último código atribuído a um produto */
    private static int ultimoID = 20_000;

    /** Código identificador do produto */
    private int idProduto;

    /** Descrição do produto */
    private String descricao;

    /** Preço de custo do produto */
    private double precoCusto;

    /** Margem de lucro do produto */
    private double margemLucro;

    /** Quantidade atual em estoque */
    private int quant;

    /** Estoque mínimo do produto */
    private int estoqueMinimo;

    /**
     * Inicializador privado.
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de custo do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para o produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     * @throws IllegalArgumentException em caso de valores inválidos
     */
    private void init(String desc, double precoCusto, double margemLucro, int quant, int estoqueMinimo) {
        if (desc.length() < 3 || precoCusto <= 0 || margemLucro <= 0 || quant < 0 || estoqueMinimo < 0)
            throw new IllegalArgumentException("Valores inválidos para o produto");
        this.descricao = desc;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
        this.quant = quant;
        this.estoqueMinimo = estoqueMinimo;
        this.idProduto = ultimoID++;
    }

    /**
     * Construtor completo.
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de custo do produto (mínimo 0.01)
     * @param margemLucro Margem de lucro para o produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     * @param estoqueMinimo Estoque mínimo (mínimo 0)
     */
    protected Produto(String desc, double precoCusto, double margemLucro, int quant, int estoqueMinimo) {
        init(desc, precoCusto, margemLucro, quant, estoqueMinimo);
    }

    /**
     * Construtor sem estoque mínimo (fica considerado como 0) e sem margem de lucro
     * (usa a margem de lucro padrão do sistema).
     * @param desc Descrição do produto (mínimo 3 caracteres)
     * @param precoCusto Preço de custo do produto (mínimo 0.01)
     * @param quant Quantidade atual no estoque (mínimo 0)
     */
    protected Produto(String desc, double precoCusto, int quant) {
        init(desc, precoCusto, MARGEM_PADRAO, quant, 0);
    }

    /**
     * Calcula o valor de venda base do produto: preço de custo acrescido da margem de lucro.
     * As subclasses usam este valor para calcular o seu valor de venda.
     * @return Valor de venda sem descontos
     */
    protected double valorBase() {
        return precoCusto * (1 + margemLucro);
    }

    /**
     * Retorna o valor de venda do produto.
     * @return Valor de venda do produto (double, positivo)
     */
    public abstract double valorDeVenda();

    /**
     * Retorna o código identificador do produto. É um valor único para cada produto (chave).
     * @return Inteiro positivo com o identificador do produto
     */
    @Override
    public int hashCode() {
        return idProduto;
    }

    /**
     * Descrição em string do produto, contendo seu código, sua descrição e o valor de venda.
     * @return String com o formato: [CÓDIGO] - [DESCRIÇÃO]: [VALOR DE VENDA]
     */
    @Override
    public String toString() {
        NumberFormat moeda = NumberFormat.getCurrencyInstance();
        return String.format("%05d - %s: %s", idProduto, descricao, moeda.format(valorDeVenda()));
    }

    /**
     * Comparação padrão do produto: descrição, em ordem alfabética.
     * @param outro Produto a ser comparado
     * @return Valor negativo, zero ou positivo, de acordo com a regra padrão de Comparable
     */
    @Override
    public int compareTo(Produto outro) {
        return this.descricao.compareTo(outro.descricao);
    }

    /**
     * Igualdade de produtos: caso possuam o mesmo código.
     * @param obj Outro produto a ser comparado
     * @return true se o parâmetro for um produto com o mesmo código identificador deste objeto
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Produto))
            return false;
        Produto outro = (Produto) obj;
        return this.idProduto == outro.idProduto;
    }

    /**
     * Cria um produto a partir de uma linha de dados em formato texto. A linha deve estar no formato
     * "tipo;descrição;preçoDeCusto;margemDeLucro;quant;estoqueMinimo;[dataDeValidade]".
     * Os tipos são 1 para produto não perecível e 2 para perecível.
     * @param linha Linha com os dados do produto a ser criado.
     * @return Um produto com os dados recebidos
     */
    static Produto criarDoTexto(String linha) {
        String[] detalhes = linha.split(";");
        String descr = detalhes[1];
        double precoCusto = Double.parseDouble(detalhes[2].replace(",", "."));
        double margem = Double.parseDouble(detalhes[3].replace(",", "."));
        int quant = Integer.parseInt(detalhes[4]);
        int minimo = Integer.parseInt(detalhes[5]);

        if (detalhes[0].equals("1"))
            return new ProdutoNaoPerecivel(descr, precoCusto, margem, quant, minimo);

        LocalDate dataValidade = LocalDate.parse(detalhes[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return new ProdutoPerecivel(descr, precoCusto, margem, quant, minimo, dataValidade);
    }
}
