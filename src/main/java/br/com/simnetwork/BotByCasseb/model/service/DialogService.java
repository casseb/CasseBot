package br.com.simnetwork.BotByCasseb.model.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;

public interface DialogService {

	public void decideDialog(Update update);
	
	public void createDialog(Message message, DialogSchema dialogSchema);
	
	public void executeDialog(Message message);
	
	public void resetAllDialogs();
	
}
