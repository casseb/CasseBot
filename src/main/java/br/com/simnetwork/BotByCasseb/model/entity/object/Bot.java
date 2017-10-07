package br.com.simnetwork.BotByCasseb.model.entity.object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.request.SendMessage;

public class Bot {
	
	private static String token = "458418970:AAEXx8FjRFZJPMr8cUhaVkSNAC6fqabxvdU";
	private static TelegramBot bot = TelegramBotAdapter.build(token);
	
	public static void sendMessage(String chatId, String text, Keyboard keyboard) {
		if(keyboard == null) {
			bot.execute(new SendMessage(chatId, text));
		}else {
			bot.execute(new SendMessage(chatId, text).replyMarkup(keyboard));
		}
		
	}

}
