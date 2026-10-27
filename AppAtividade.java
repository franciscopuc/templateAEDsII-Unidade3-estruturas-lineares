import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Aplicação da atividade avaliativa de Estruturas Lineares.
 * Ao iniciar, carrega os produtos, os pedidos e os fornecedores a partir dos arquivos-texto.
 */
public class AppAtividade {

    /** Arquivo com os produtos cadastrados */
    static final String ARQUIVO_PRODUTOS = "produtos_10000.txt";

    /** Arquivo com os pedidos já realizados */
    static final String ARQUIVO_PEDIDOS = "pedidos.txt";

    /** Arquivo com os fornecedores cadastrados */
    static final String ARQUIVO_FORNECEDORES = "fornecedores.txt";

    /** Scanner para leitura do teclado */
    static Scanner teclado;

    /** Lista de produtos cadastrados */
    static Lista<Produto> produtosCadastrados;

    /** Fila de pedidos realizados, do mais antigo (frente) para o mais recente */
    static Fila<Pedido> pedidosCadastrados;

    /** Lista de fornecedores cadastrados */
    static Lista<Fornecedor> fornecedoresCadastrados;

    /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    /** Cabeçalho principal da CLI do sistema */
    static void cabecalho() {
        System.out.println("AEDII COMÉRCIO DE COISINHAS");
        System.out.println("===========================");
    }

    /**
     * Imprime o menu principal, lê a opção do usuário e a retorna.
     * @return Um inteiro com a opção do usuário.
     */
    static int menu() {
        cabecalho();
        System.out.println("1 - Listar produtos");
        System.out.println("2 - Listar pedidos");
        System.out.println("3 - Listar fornecedores");
        System.out.println("4 - Listar os fornecedores de um produto");
        System.out.println("5 - Exibir a quantidade média de produtos por fornecedor");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    /**
     * Lê os produtos de um arquivo-texto no formato <br/>
     * N (quantidade de produtos) <br/>
     * tipo;descrição;preçoDeCusto;margemDeLucro;quant;estoqueMinimo;[dataDeValidade] <br/>
     * Os códigos dos produtos são atribuídos na ordem de leitura, a partir de 20000.
     * @param nomeArquivo Nome do arquivo de produtos.
     * @return Lista com os produtos lidos (vazia em caso de problemas com o arquivo).
     */
    static Lista<Produto> lerProdutos(String nomeArquivo) {
        Lista<Produto> produtos = new Lista<>();
        try (Scanner arquivo = new Scanner(new File(nomeArquivo), Charset.forName("UTF-8"))) {
            int quantidade = Integer.parseInt(arquivo.nextLine());
            for (int i = 0; i < quantidade; i++)
                produtos.inserirFinal(Produto.criarDoTexto(arquivo.nextLine()));
        } catch (IOException excecao) {
            System.out.println("Problemas ao ler o arquivo " + nomeArquivo + ".");
        }
        return produtos;
    }

    /**
     * Localiza um produto cadastrado a partir do seu código.
     * @param codigo Código do produto.
     * @return O produto com o código informado, ou null se ele não existir.
     */
    static Produto localizarProduto(int codigo) {
        return produtosCadastrados.localizar(produto -> produto.hashCode() == codigo);
    }

    /**
     * Lê os pedidos de um arquivo-texto no formato <br/>
     * N (quantidade de pedidos) <br/>
     * códigoProduto;códigoProduto;...; <br/>
     * Há uma linha para cada pedido, com os códigos dos seus produtos.
     * Os pedidos são enfileirados na ordem do arquivo.
     * @param nomeArquivo Nome do arquivo de pedidos.
     * @return Fila com os pedidos lidos (vazia em caso de problemas com o arquivo).
     */
    static Fila<Pedido> lerPedidos(String nomeArquivo) {
        Fila<Pedido> pedidos = new Fila<>();
        try (Scanner arquivo = new Scanner(new File(nomeArquivo), Charset.forName("UTF-8"))) {
            int quantidade = Integer.parseInt(arquivo.nextLine());
            for (int i = 0; i < quantidade; i++) {
                String[] codigos = arquivo.nextLine().split(";");
                Pedido pedido = new Pedido();
                for (String codigo : codigos)
                    pedido.incluirProduto(localizarProduto(Integer.parseInt(codigo.trim())));
                pedidos.enfileirar(pedido);
            }
        } catch (IOException excecao) {
            System.out.println("Problemas ao ler o arquivo " + nomeArquivo + ".");
        }
        return pedidos;
    }

    /**
     * Lê os fornecedores de um arquivo-texto no formato <br/>
     * N (quantidade de fornecedores) <br/>
     * código;nome;CNPJ;códigoProduto;códigoProduto;...; <br/>
     * Há uma linha para cada fornecedor, com os códigos dos produtos que ele fornece.
     * @param nomeArquivo Nome do arquivo de fornecedores.
     * @return Lista com os fornecedores lidos (vazia em caso de problemas com o arquivo).
     */
    static Lista<Fornecedor> lerFornecedores(String nomeArquivo) {
        Lista<Fornecedor> fornecedores = new Lista<>();
        try (Scanner arquivo = new Scanner(new File(nomeArquivo), Charset.forName("UTF-8"))) {
            int quantidade = Integer.parseInt(arquivo.nextLine());
            for (int i = 0; i < quantidade; i++) {
                String[] dados = arquivo.nextLine().split(";");
                Fornecedor fornecedor = new Fornecedor(Integer.parseInt(dados[0]), dados[1], dados[2]);
                for (int j = 3; j < dados.length; j++)
                    fornecedor.incluirProduto(localizarProduto(Integer.parseInt(dados[j].trim())));
                fornecedores.inserirFinal(fornecedor);
            }
        } catch (IOException excecao) {
            System.out.println("Problemas ao ler o arquivo " + nomeArquivo + ".");
        }
        return fornecedores;
    }

    /** Lista todos os produtos cadastrados, um por linha. */
    static void listarProdutos() {
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:\n");
        if (produtosCadastrados.vazia())
            System.out.println("Nenhum produto cadastrado.");
        else
            System.out.print(produtosCadastrados);
    }

    /** Lista todos os pedidos, do mais antigo para o mais recente. */
    static void listarPedidos() {
        cabecalho();
        System.out.println("\nPEDIDOS REALIZADOS:\n");
        if (pedidosCadastrados.vazia())
            System.out.println("Nenhum pedido realizado.");
        else
            System.out.print(pedidosCadastrados);
    }

    /** Lista todos os fornecedores cadastrados, com os produtos que cada um fornece. */
    static void listarFornecedores() {
        cabecalho();
        System.out.println("\nFORNECEDORES CADASTRADOS:\n");
        if (fornecedoresCadastrados.vazia())
            System.out.println("Nenhum fornecedor cadastrado.");
        else
            System.out.print(fornecedoresCadastrados);
    }

    /** Lista os fornecedores de um produto */
    static void listarFornecedoresDeProduto(){
        cabecalho();
        System.out.println("\nFORNECEDORES DE PRODUTO:\n");
        if (fornecedoresCadastrados.vazia())
            System.out.println("Nenhum fornecedor cadastrado.");
        else
            
    }

    /** Exibe a quantidade média de produtos por fornecedor */
    static void exibirMediaDeProdutos() {
        cabecalho();
        System.out.println("\nMEDIA DE PRODUTOS:\n");
        if (fornecedoresCadastrados.vazia())
            System.out.println("Nenhum fornecedor cadastrado.");
        else {
            double media = fornecedoresCadastrados.calcularMedia(f -> (double)f.quantidadeProdutos());
            System.out.print(String.format("%2.f", media));
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in, Charset.forName("UTF-8"));

        produtosCadastrados = lerProdutos(ARQUIVO_PRODUTOS);
        pedidosCadastrados = lerPedidos(ARQUIVO_PEDIDOS);
        fornecedoresCadastrados = lerFornecedores(ARQUIVO_FORNECEDORES);

        int opcao;
        do {
            opcao = menu();
            switch (opcao) {
                case 1 -> listarProdutos();
                case 2 -> listarPedidos();
                case 3 -> listarFornecedores();
                case 4 -> listarFornecedoresDeProduto();
                case 5 -> exibirMediaDeProdutos();
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opção inválida.");
            }
            pausa();
        } while (opcao != 0);

        teclado.close();
    }
}
