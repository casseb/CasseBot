package br.com.simnetwork.BotByCasseb.model.entity.object;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;

public class Bot {
	
	private static String token = "458418970:AAEXx8FjRFZJPMr8cUhaVkSNAC6fqabxvdU";
	private static TelegramBot bot = TelegramBotAdapter.build(token);
	
	public static void sendMessage(String chatId, String text) {
		bot.execute(new SendMessage(chatId, text));
	}

}
