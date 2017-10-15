package br.com.simnetwork.BotByCasseb.model.entity.object;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Define um campo dinâmico.
 * <p>
 * Contêm nome do campo e conteúdo para armazenar o conteúdo/campo de um
 * deperminado registro dinâmico.
 * 
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
@Data
public class Field {

	/**
	 * Nome do campo.
	 */
	@Id @NonNull
	private String nomeField;
	/**
	 * Conteúdo do campo.
	 * <p>
	 * Sempre é gravado como String, a definição do tipo realizado pelo
	 * dynamicFieldSchema é usado como referência para realizar um cast tanto na
	 * entrada quanto no uso do dado.
	 */
	@NonNull
	private Object value;
	/**
	 * Schema que é usado como referência
	 */
	@NonNull
	private FieldSchema fieldSchema;
}
