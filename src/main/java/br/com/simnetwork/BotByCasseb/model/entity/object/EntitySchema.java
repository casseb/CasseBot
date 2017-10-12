package br.com.simnetwork.BotByCasseb.model.entity.object;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
/**
 * Define a estrutura de uma tabela dinâmica.
 * <p>
 * É definido no arquivo dynamicContext.xml o nome da entidade e seus campos.
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
@Data
public class EntitySchema {
	
	/**
	 * Nome da entidade(tabela)
	 */
	@Id @NonNull
	private String nomeEntity;
	/**
	 * Lista de campos que esta entidade(tabela) terá.
	 */
	private List<FieldSchema> campos = new LinkedList<>();
	/**
	 * Campo que será utilizado como chave do registro
	 */
	private String chave;
	
}
