package br.com.simnetwork.BotByCasseb.model.entity.object;

import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Definição da estrutura de um campo dinâmico.
 * <p>
 * Nesta classe é definido tamanho, tipo, se é unico e se pode ser nulo,
 * servindo como referência para a inserção e recuperação dos valores.
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class FieldSchema {
	
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
	@NonNull
	private String tipo;
	/**
	 * Tamanho máximo permitido
	 */
	private String tamanho;
	/**
	 * Definição se o campo pode ficar vazio.
	 */
	@NonNull
	private Boolean notNull;
	private Object defaultValue;
	
	
}
