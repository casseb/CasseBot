package br.com.simnetwork.BotByCasseb.model.entity.dialog.structure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
public class Dialog {
	
	@Id @NonNull
	private BotUser botUser;
	@NonNull
	private DialogSchema dialogSchema;
	private DialogStatus dialogStatus = DialogStatus.INICIO;
	private Map<String,String> decisions = new HashMap<>();
	private Integer currentStep = 1;
	
	public void addDecision(String key, String value) {
		decisions.put(key, value);
	}
}
