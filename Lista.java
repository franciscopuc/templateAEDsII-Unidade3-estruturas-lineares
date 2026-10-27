import java.util.function.Predicate;
import java.util.function.Function;

/**
 * Lista linear flexível (encadeada), com célula sentinela no início.
 * @param <E> Tipo dos itens armazenados na lista
 */
public class Lista<E> {

	private Celula<E> primeiro;
	private Celula<E> ultimo;
	private int tamanho;

	/** Cria uma lista vazia. */
	public Lista() {
		Celula<E> sentinela = new Celula<>();
		this.primeiro = this.ultimo = sentinela;
		this.tamanho = 0;
	}

	/** @return true se a lista não tiver itens; false, caso contrário */
	public boolean vazia() {
		return (this.primeiro == this.ultimo);
	}

	/** @return a quantidade de itens armazenados na lista */
	public int tamanho() {
		return this.tamanho;
	}

	/**
	 * Insere um item na posição informada. As posições vão de 0 (início) a tamanho (final).
	 * @param novo Item a ser inserido
	 * @param posicao Posição em que o item será inserido
	 * @throws IndexOutOfBoundsException se a posição for inválida
	 */
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

	/**
	 * Insere um item no final da lista. Usa a referência à última célula, sem percorrer a lista.
	 * @param novo Item a ser inserido
	 */
	public void inserirFinal(E novo) {
		Celula<E> novaCelula = new Celula<>(novo);
		this.ultimo.setProximo(novaCelula);
		this.ultimo = novaCelula;
		this.tamanho++;
	}

	/**
	 * Remove e retorna o item da posição informada. As posições vão de 0 a tamanho - 1.
	 * @param posicao Posição do item a ser removido
	 * @return O item removido
	 * @throws IllegalStateException se a lista estiver vazia
	 * @throws IndexOutOfBoundsException se a posição for inválida
	 */
	public E remover(int posicao) {

		Celula<E> anterior, celulaRemovida, proximaCelula;

		if (vazia())
			throw new IllegalStateException("Não foi possível remover o item da lista: "
					+ "a lista está vazia!");

		if ((posicao < 0) || (posicao >= this.tamanho))
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


	// T1 Questão 01)
	public Lista<E> filtrar(Predicate<E> condicao) {
		Lista<E> nova = new Lista<>();
		for(Celula<E> aux = primeiro.getProximo(); aux != null; aux = aux.getProximo()){
			E item = aux.getItem();
			if(condicao.test(item)){
				nova.inserirFinal(item);
			}
		}
		return nova;
	}

	// T2 Questão 01)
	public double calcularMedia(Function<E, Double> extrator) {
		if(this.vazia()) return 0; int i = 0; double total = 0;
		for(Celula<E> aux = primeiro.getProximo(); aux != null; aux = aux.getProximo()){
			total += extrator.apply(aux.getItem());
			i++;
		} return (total / (double)i);
	}



	/**
	 * Retorna o primeiro item da lista que atende ao critério informado.
	 * @param criterio Condição que o item procurado deve atender
	 * @return O item localizado, ou null se nenhum item atender ao critério
	 */
	public E localizar(Predicate<E> criterio) {

		Celula<E> atual = this.primeiro.getProximo();

		while (atual != null) {
			if (criterio.test(atual.getItem()))
				return (atual.getItem());
			atual = atual.getProximo();
		}

		return (null);
	}

	/**
	 * Representação em String da lista: um item por linha, do início para o final.
	 * @return String com os itens da lista (vazia, se a lista não tiver itens)
	 */
	@Override
	public String toString() {

		StringBuilder dados = new StringBuilder();
		Celula<E> atual = this.primeiro.getProximo();

		while (atual != null) {
			dados.append(atual.getItem()).append("\n");
			atual = atual.getProximo();
		}

		return dados.toString();
	}
}
