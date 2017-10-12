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
 * Item de uma tabela dinâmica.
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
@Data
public class Record {

	/**
	 * Chave do registro dinâmico.
	 */
	@Id @NonNull
	private String chave;
	/**
	 * Lista de campos que este registro possui.
	 */
	private List<Field> campos = new LinkedList<Field>();

}

