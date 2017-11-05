package br.com.simnetwork.BotByCasseb.model.entity.object;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecordSchema {

	@Id
	private ObjectId objectId;
	
	private int order;
	private String entityName;
	private String fieldName;
	private RecordType type;
	private String defaultValue;
	private boolean isKey;
	private boolean notNull;
	
	public RecordSchema(int order, String entityName, String fieldName, RecordType type, String defaultValue,
			boolean isKey, boolean notNull) {
		super();
		this.objectId = new ObjectId();
		this.order = order;
		this.entityName = entityName;
		this.fieldName = fieldName;
		this.type = type;
		this.defaultValue = defaultValue;
		this.isKey = isKey;
		this.notNull = notNull;
	}
	
	
	
}
