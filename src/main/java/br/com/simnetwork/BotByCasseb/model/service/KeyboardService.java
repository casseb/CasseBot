package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

import com.pengrad.telegrambot.model.request.Keyboard;

public interface KeyboardService {

	public Keyboard getSimpleKeyboard(List<String> options);
	
}
