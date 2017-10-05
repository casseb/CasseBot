package br.com.simnetwork.BotByCasseb.model.entity.object;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Definição da estrutura de um campo dinâmico.
 * <p>
 * Nesta classe é definido tamanho, tipo, se é unico e se pode ser nulo,
 * servindo como referência para a inserção e recuperação dos valores.
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
@Data
public class DynamicFieldSchema {
	
	/**
	 * Nome do campo.
	 */
	@Id @NonNull
	private String nomeField;
	/**
	 * Tipo do campo.
	 * <p>
	 * Possui as seguintes opções:
	 * <ul>
	 * <li>String</li>
	 * </ul>
	 */
	private String tipo;
	/**
	 * Tamanho máximo permitido
	 */
	private String tamanho;
	/**
	 * Definição se o campo pode se repetir na coleção.
	 */
	private boolean unico;
	/**
	 * Definição se o campo pode ficar vazio.
	 */
	private boolean notNull;
	
	
}
