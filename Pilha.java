import java.util.NoSuchElementException;

/**
 * Pilha linear flexível (encadeada), com célula sentinela no fundo.
 * @param <E> Tipo dos itens armazenados na pilha
 */
public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;

	/** Cria uma pilha vazia. */
	public Pilha() {
		Celula<E> sentinela = new Celula<>();
		fundo = sentinela;
		topo = sentinela;
	}

	/** @return true se a pilha não tiver itens; false, caso contrário */
	public boolean vazia() {
		return fundo == topo;
	}

	/**
	 * Insere um item no topo da pilha.
	 * @param item Item a ser empilhado
	 */
	public void empilhar(E item) {
		topo = new Celula<>(item, topo);
	}

	/**
	 * Remove e retorna o item do topo da pilha.
	 * @return O item que estava no topo da pilha
	 * @throws NoSuchElementException se a pilha estiver vazia
	 */
	public E desempilhar() {
		E desempilhado = consultarTopo();
		Celula<E> antigoTopo = topo;
		topo = topo.getProximo();
		antigoTopo.setProximo(null);
		return desempilhado;
	}

	/**
	 * Retorna, sem remover, o item do topo da pilha.
	 * @return O item do topo da pilha
	 * @throws NoSuchElementException se a pilha estiver vazia
	 */
	public E consultarTopo() {
		if (vazia())
			throw new NoSuchElementException("Não há nenhum item na pilha!");
		return topo.getItem();
	}

	/**
	 * Representação em String da pilha: um item por linha, do topo para o fundo.
	 * @return String com os itens da pilha (vazia, se a pilha não tiver itens)
	 */
	@Override
	public String toString() {

		StringBuilder dados = new StringBuilder();
		Celula<E> atual = topo;

		while (atual != fundo) {
			dados.append(atual.getItem()).append("\n");
			atual = atual.getProximo();
		}

		return dados.toString();
	}
}
