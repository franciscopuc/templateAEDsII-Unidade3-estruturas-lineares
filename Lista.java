import java.util.function.Function;
import java.util.function.Predicate;

public class Lista<E> {

	private Celula<E> primeiro;
	private Celula<E> ultimo;
	private int tamanho;
	
	public Lista() {
		
		Celula<E> sentinela = new Celula<>();
		
		this.primeiro = this.ultimo = sentinela;
		this.tamanho = 0;
	}
	
	public boolean vazia() {
		
		return (this.primeiro == this.ultimo);
	}
	
	public void inserir(E novo, int posicao) {
		
		Celula<E> anterior, novaCelula, proximaCelula;
		
		if ((posicao < 0) || (posicao > this.tamanho))
			throw new IndexOutOfBoundsException("Não foi possível inserir o item na lista: "
					+ "a posição informada é inválida!");
		
		anterior = this.primeiro;
		for (int i = 0; i < posicao; i++)
			anterior = anterior.getProximo();
				
		novaCelula = new Celula<>(novo);
			
		proximaCelula = anterior.getProximo();
			
		anterior.setProximo(novaCelula);
		novaCelula.setProximo(proximaCelula);
			
		if (posicao == this.tamanho)  // a inserção ocorreu na última posição da lista
			this.ultimo = novaCelula;
			
		this.tamanho++;		
	}
	
	public E remover(int posicao) {
		
		Celula<E> anterior, celulaRemovida, proximaCelula;
		
		if (vazia())
			throw new IllegalStateException("Não foi possível remover o item da lista: "
					+ "a lista está vazia!");
		
		if ((posicao < 0) || (posicao >= this.tamanho ))
			throw new IndexOutOfBoundsException("Não foi possível remover o item da lista: "
					+ "a posição informada é inválida!");
			
		anterior = this.primeiro;
		for (int i = 0; i < posicao; i++)
			anterior = anterior.getProximo();
				
		celulaRemovida = anterior.getProximo();
				
		proximaCelula = celulaRemovida.getProximo();
				
		anterior.setProximo(proximaCelula);
		celulaRemovida.setProximo(null);
				
		if (celulaRemovida == this.ultimo)
			this.ultimo = anterior;
				
		this.tamanho--;
				
		return (celulaRemovida.getItem());	
	}

	public E localizar(Predicate<E> criterio) {
		
		Celula<E> atual = this.primeiro.getProximo();
		
		while (atual != null) {
			if (criterio.test(atual.getItem()))
				return (atual.getItem());
			
			atual = atual.getProximo();
		}
		
		return (null);
	}

	public int tamanho() {

		return (this.tamanho);
	}

	public E obter(int posicao) {

		if ((posicao < 0) || (posicao >= this.tamanho))
			throw new IndexOutOfBoundsException("Não foi possível obter o item da lista: "
					+ "a posição informada é inválida!");

		Celula<E> atual = this.primeiro.getProximo();
		for (int i = 0; i < posicao; i++)
			atual = atual.getProximo();

		return (atual.getItem());
	}

	/**
	 * Calcula o valor médio de um atributo numérico dos primeiros elementos da lista.
	 * @param extrator Função que extrai o valor numérico de cada elemento
	 * @param quantidade Quantidade de primeiros elementos considerados
	 * @return A média dos valores extraídos (0 se não houver elementos considerados)
	 */
	public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {

		Celula<E> atual = this.primeiro.getProximo();
		double soma = 0d;
		int contados = 0;

		while (atual != null && contados < quantidade) {
			soma += extrator.apply(atual.getItem());
			contados++;
			atual = atual.getProximo();
		}

		return (contados == 0) ? 0d : soma / contados;
	}

	/**
	 * Cria uma nova lista com os elementos, entre os primeiros da lista, que satisfazem a condição.
	 * @param condicional Teste que decide se o elemento entra na nova lista
	 * @param quantidade Quantidade de primeiros elementos submetidos ao teste
	 * @return Nova lista com os elementos que satisfazem a condição
	 */
	public Lista<E> filtrar(Predicate<E> condicional, int quantidade) {

		Lista<E> filtrada = new Lista<>();
		Celula<E> atual = this.primeiro.getProximo();
		int testados = 0;

		while (atual != null && testados < quantidade) {
			if (condicional.test(atual.getItem()))
				filtrada.inserir(atual.getItem(), filtrada.tamanho);

			testados++;
			atual = atual.getProximo();
		}

		return filtrada;
	}

	public static void main(String[] args) {
		Lista<Integer> l = new Lista<>();
		l.inserir(4, 0);
		l.inserir(8, 0);
		Integer result = l.localizar(a -> a == 3);
		System.out.println(result);
	}
}
