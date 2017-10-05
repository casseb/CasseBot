package br.com.simnetwork.BotByCasseb.model.entity.object;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;

/**
 * Responsável por representar uma tabela dinâmica.
 * <p>
 * Nesta classe é representado uma tabela definida no dynamicContext.xml,
 * servindo para persistir no banco a tabela em si com seus respectivos itens
 * (que seria os registros de um banco)
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
 
@Data
public class DynamicEntity {

	/**
	 * Nome da entidade(tabela).
	 */
	@Id @NonNull
	private String nomeEntity;
	/**
	 * Lista dos itens que estão presentes na entidade(tabela).
	 */
	private List<DynamicItem> itens = new LinkedList<>();
	/**
	 * Schema que é usado como referência
	 */
	private DynamicEntitySchema dynamicEntitySchema;
}
