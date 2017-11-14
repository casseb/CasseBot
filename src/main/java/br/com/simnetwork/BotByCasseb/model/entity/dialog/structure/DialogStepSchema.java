package br.com.simnetwork.BotByCasseb.model.entity.dialog.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.entity.object.DynamicList;
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
	private ObjectId id;
	private String botMessage;
	private StepType stepType;
	private List<String> keyboardOptions = new LinkedList<>();
	private List<String> inlineKeyboard = new LinkedList<>();
	private DynamicList dynamicList;
	private String key;
	private String entity;
	private Map<String,String> parameters = new HashMap<String,String>();
	
	
}
