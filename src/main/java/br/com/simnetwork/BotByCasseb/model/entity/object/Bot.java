package br.com.simnetwork.BotByCasseb.model.entity.object;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import br.com.simnetwork.BotByCasseb.model.service.KeyboardService;

public class Bot {
	
	private static String token = "458418970:AAHfzd0xqpFUByfL4EOcDvIna75V6VAeVRQ";
	private static TelegramBot bot = TelegramBotAdapter.build(token);
	
	public static void sendMessage(String chatId, String text, Keyboard keyboard) {
		if(keyboard == null) {
			bot.execute(new SendMessage(chatId, text).replyMarkup(new ReplyKeyboardRemove()));
		}else {
			bot.execute(new SendMessage(chatId, text).replyMarkup(keyboard));
		}
		
	}
	
	public static void requestContact(String chatId, String text) {
		KeyboardButton[] keyboardButton = new KeyboardButton[]{
                new KeyboardButton("Disponibilizar Contato").requestContact(true)
        };
		Keyboard keyboard = new ReplyKeyboardMarkup(keyboardButton);
		bot.execute(new SendMessage(chatId, text).replyMarkup(keyboard));
	}

}
