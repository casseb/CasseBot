package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.entity.object.Record;

public interface KeyboardService {

	public Keyboard getSimpleKeyboard(List<String> keyboardOptions);
	public Keyboard getDefaultKeyboard();
	public InlineKeyboardMarkup getSimpleInlineKeyboard(List<String> inlineOptions);
	public InlineKeyboardMarkup getRecordInlineKeyboard(List<Record> records);
	
}
