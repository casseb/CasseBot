package br.com.simnetwork.BotByCasseb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import br.com.simnetwork.BotByCasseb.model.entity.object.Bot;
import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogService;
@RestController
public class MessageController {

	@Autowired
	private DialogService dialogService;
	
	@RequestMapping("/readMessages")
	public void readMessages(@RequestBody String stringRequest) {
		Bot.sendMessage("336050938", stringRequest, null);
		Update update = BotUtils.parseUpdate(stringRequest);
		dialogService.decideDialog(update);
	}
	
}
