package br.com.simnetwork.BotByCasseb.model.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStepSchema;

public interface DialogStepSchemaService {
	
	public Keyboard getKeyboard(DialogStepSchema dialogStepSchema);
	public InlineKeyboardMarkup getInlineKeyboard(DialogStepSchema dialogStepSchema);
	

}
