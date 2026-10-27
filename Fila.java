import java.util.NoSuchElementException;

/**
 * Fila linear flexível (encadeada), com célula sentinela na frente.
 * @param <E> Tipo dos itens armazenados na fila
 */
public class Fila<E> {

	private Celula<E> frente;
	private Celula<E> tras;

	/** Cria uma fila vazia. */
	public Fila() {
		Celula<E> sentinela = new Celula<>();
		frente = tras = sentinela;
	}

	/** @return true se a fila não tiver itens; false, caso contrário */
	public boolean vazia() {
		return (frente == tras);
	}

	/**
	 * Insere um item no final da fila.
	 * @param item Item a ser enfileirado
	 */
	public void enfileirar(E item) {
		Celula<E> novaCelula = new Celula<>(item);
		tras.setProximo(novaCelula);
		tras = novaCelula;
	}

	/**
	 * Remove e retorna o item da frente da fila.
	 * @return O item que estava na frente da fila
	 * @throws NoSuchElementException se a fila estiver vazia
	 */
	public E desenfileirar() {

		E item = consultarPrimeiro();
		Celula<E> primeiro = frente.getProximo();

		frente.setProximo(primeiro.getProximo());
		primeiro.setProximo(null);

		// Caso o item desenfileirado seja também o último da fila.
		if (primeiro == tras)
			tras = frente;

		return item;
	}

	/**
	 * Retorna, sem remover, o item da frente da fila.
	 * @return O item da frente da fila
	 * @throws NoSuchElementException se a fila estiver vazia
	 */
	public E consultarPrimeiro() {
		if (vazia())
			throw new NoSuchElementException("Não há nenhum item na fila!");
		return frente.getProximo().getItem();
	}

	/**
	 * Representação em String da fila: um item por linha, da frente para o final.
	 * @return String com os itens da fila (vazia, se a fila não tiver itens)
	 */
	@Override
	public String toString() {

		StringBuilder dados = new StringBuilder();
		Celula<E> atual = frente.getProximo();

		while (atual != null) {
			dados.append(atual.getItem()).append("\n");
			atual = atual.getProximo();
		}

		return dados.toString();
	}
}
