/**
 * Célula (nó) das estruturas lineares flexíveis: armazena um item e a referência
 * para a próxima célula.
 * @param <T> Tipo do item armazenado na célula
 */
public class Celula<T> {

	private final T item;
	private Celula<T> proximo;

	/** Cria uma célula sem item (usada como sentinela). */
	public Celula() {
		this.item = null;
		this.proximo = null;
	}

	/**
	 * Cria uma célula com o item informado e sem próxima célula.
	 * @param item Item a ser armazenado
	 */
	public Celula(T item) {
		this.item = item;
		this.proximo = null;
	}

	/**
	 * Cria uma célula com o item e a próxima célula informados.
	 * @param item Item a ser armazenado
	 * @param proximo Próxima célula
	 */
	public Celula(T item, Celula<T> proximo) {
		this.item = item;
		this.proximo = proximo;
	}

	/** @return o item armazenado na célula */
	public T getItem() {
		return item;
	}

	/** @return a próxima célula */
	public Celula<T> getProximo() {
		return proximo;
	}

	/**
	 * Altera a referência para a próxima célula.
	 * @param proximo Nova próxima célula
	 */
	public void setProximo(Celula<T> proximo) {
		this.proximo = proximo;
	}
}
