/**
 * Representa um fornecedor responsável pelo fornecimento de produtos.
 *
 * A classe mantém os dados básicos do fornecedor e uma lista flexível
 * com os produtos que ele fornece.
 *
 * A ordenação natural dos fornecedores é realizada pelo código do
 * fornecedor, conforme implementado no método {@link #compareTo(Fornecedor)}
 */
public class Fornecedor implements Comparable<Fornecedor> {

    /** Código identificador do fornecedor. */
    private int codFornecedor;

    /** Nome do fornecedor. */
    private String nome;

    /** CNPJ do fornecedor. */
    private String cnpj;

    /** Lista dos produtos fornecidos. */
    private Lista<Produto> produtos;

    /**
     * Cria um novo fornecedor, ainda sem produtos.
     * @param codigo código identificador do fornecedor
     * @param nome nome do fornecedor
     * @param cnpj CNPJ do fornecedor
     */
    public Fornecedor(int codigo, String nome, String cnpj) {
        this.codFornecedor = codigo;
        this.nome = nome;
        this.cnpj = cnpj;
        this.produtos = new Lista<>();
    }

    /**
     * Inclui um produto no final da lista de produtos do fornecedor.
     * @param produto produto a ser incluído
     * @return {@code true} se o produto foi incluído com sucesso;
     *         {@code false} se o produto informado for {@code null}
     */
    public boolean incluirProduto(Produto produto) {
        if (produto == null)
            return false;
        produtos.inserirFinal(produto);
        return true;
    }

	// T1 Questão 02)
	public boolean forneceProduto(int codigo) {
        Lista<Produto> l = produtos.filtrar(p -> p.hashCode() == codigo);
        if(!l.vazia()) return true; else return false;
    }

    // T2 Questão 02)
    public int quantidadeProdutos(){
        return produtos.tamanho();
    }

    

    /**
     * Compara este fornecedor com outro fornecedor pelo código.
     * @param outro fornecedor que será comparado com este objeto
     * @return valor negativo se este fornecedor possuir código menor que o
     *         outro; zero se os códigos forem iguais; ou valor positivo se
     *         este fornecedor possuir código maior
     * @throws NullPointerException se {@code outro} for {@code null}
     */
    @Override
    public int compareTo(Fornecedor outro) {
        return codFornecedor - outro.codFornecedor;
    }

    /**
     * Retorna uma representação textual do fornecedor: um cabeçalho com código, nome,
     * CNPJ e quantidade de produtos, seguido dos produtos fornecidos, um por linha.
     * @return texto com os dados do fornecedor
     */
    @Override
    public String toString() {
        return String.format("Fornecedor %03d - %s - CNPJ %s - %d produto(s)\n",
                codFornecedor, nome, cnpj, produtos.tamanho())
                + produtos;
    }
}
