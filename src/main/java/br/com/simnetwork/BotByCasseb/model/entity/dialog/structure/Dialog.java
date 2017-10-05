package br.com.simnetwork.BotByCasseb.model.entity.dialog.structure;

import org.springframework.data.annotation.Id;

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
	private JsonObject decisions = new JsonObject();
	private Integer currentStep = 1;
}
