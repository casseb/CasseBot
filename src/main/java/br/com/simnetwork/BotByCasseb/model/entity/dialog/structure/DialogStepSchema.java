package br.com.simnetwork.BotByCasseb.model.entity.dialog.structure;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.service.KeyboardService;
import br.com.simnetwork.BotByCasseb.model.service.KeyboardServiceImpl;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class DialogStepSchema {

	@Id @NonNull
	private String nomeStep;
	private String botMessage;
	private StepType stepType;
	private List<String> options = new LinkedList<>();
	
	
	public Keyboard getKeyboard() {
		KeyboardServiceImpl keyboardService = new KeyboardServiceImpl();
		if(!options.isEmpty()) {
			return keyboardService.getSimpleKeyboard(options);
		}else {
			return null;
		}
	}
	
}
