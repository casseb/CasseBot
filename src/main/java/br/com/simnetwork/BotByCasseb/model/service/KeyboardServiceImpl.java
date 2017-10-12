package br.com.simnetwork.BotByCasseb.model.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

@Service("keyboardService")
public class KeyboardServiceImpl implements KeyboardService {

	@Override
	public Keyboard getSimpleKeyboard(List<String> keyboardOptions) {
		Map<Integer, String[]> map = prepareKeyboard(keyboardOptions);
		Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]));
		return replyKeyboardMarkup;
	}
	
	@Override
	public Keyboard getDefaultKeyboard() {
		List<String> options = new LinkedList<String>();
		options.add("Menu");
		Map<Integer, String[]> map = prepareKeyboard(options);
		Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(map.values().toArray(new String[map.size()][20]));
		return replyKeyboardMarkup;
	}


	@Override
	public InlineKeyboardMarkup getSimpleInlineKeyboard(Map<String, String> inlineOptions) {
		InlineKeyboardButton[] buttons = new InlineKeyboardButton[inlineOptions.size()];
		int i = 0;
		for(String inlineOption : inlineOptions.keySet()) {
			buttons[i++] = new InlineKeyboardButton(inlineOptions.get(inlineOption)).callbackData(inlineOption);
		}
		return new InlineKeyboardMarkup(buttons);
	}

	private Map<Integer, String[]> prepareKeyboard(List<String> strings) {
		Map<Integer, String[]> map = new HashMap<Integer, String[]>();
		if (strings.size() != 0) {
			int linha = 0;
			int item = 0;

			int biggerString = biggerString(strings);

			int size1 = 32;
			int size2 = 15;
			int size3 = 9;
			int size4 = 5;

			int n;

			if (biggerString >= size1) {
				n = 1;
			} else {
				if (biggerString >= size2) {
					n = 1;
				} else {
					if (biggerString >= size3) {
						n = 2;
					} else {
						if (biggerString >= size4) {
							n = 3;
						} else {
							n = 4;
						}
					}
				}
			}

			// Caso a lista seja menor que a quantidade de colunas
			if (n > strings.size()) {
				String[] conteudoUnico = new String[strings.size()];
				for (int i = 0; i < strings.size(); i++) {
					conteudoUnico[i] = strings.get(item++);
				}
				map.put(linha++, conteudoUnico);
			} else {
				// Caso haja sobra na distribuição dos botões
				if (strings.size() % n != 0) {
					int sobra = strings.size() % n;
					if (sobra > 0) {
						String[] conteudoSobra = new String[sobra];
						for (int i = 0; i < sobra; i++) {
							conteudoSobra[i] = strings.get(item++);
						}
						map.put(linha++, conteudoSobra);
					}
				}
				// Distribuição final
				while (item < strings.size()) {
					String[] conteudo = new String[n];
					for (int i = 0; i < n; i++) {
						conteudo[i] = strings.get(item++);
					}
					map.put(linha++, conteudo);
				}
			}
		}
		return map;

	}

	private int biggerString(List<String> strings) {
		int result = 0;
		for (String string : strings) {
			if (string.length() > result) {
				result = string.length();
			}
		}
		return result;
	}

	

	


	

}
