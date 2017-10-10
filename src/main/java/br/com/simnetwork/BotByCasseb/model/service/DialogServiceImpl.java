package br.com.simnetwork.BotByCasseb.model.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.Dialog;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStepSchema;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.StepType;
import br.com.simnetwork.BotByCasseb.model.entity.object.Bot;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.repository.DialogRepository;

@Service("dialogService")
public class DialogServiceImpl implements DialogService {

	@Autowired
	private DialogRepository dialogRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private BotUserService botUserService;
	@Autowired
	private KeyboardService keyboardService;

	@Override
	public void decideDialog(Message message) {
		BotUser botUser = new BotUser(message.from());
		if (dialogRepo.findByBotUserId(botUser.getId())==null) {
			createDialog(message, dialogSchemaService.findDialogSchemabyNomeSchema("|D|DialogSchemaTest2|"));
		}
		executeDialog(message);
	}

	@Override
	public void createDialog(Message message, DialogSchema dialogSchema) {
		BotUser botUser = botUserService.createBotUser(message);
		Dialog dialog;
		if(botUser.getContact()==null) {
			dialog = new Dialog(botUser,dialogSchemaService.findDialogSchemabyNomeSchema("|D|Bem Vindo|"));
		}else {
			dialog = new Dialog(botUser, dialogSchema);
		}
		dialogRepo.save(dialog);
	}

	@Override
	public void executeDialog(Message message) {
		// Preparando dados para execução
		BotUser botUser = botUserService.locateBotUser(message.from().id());
		Dialog dialog = dialogRepo.findByBotUserId(botUser.getId());
		DialogSchema dialogSchema = dialog.getDialogSchema();
		DialogStepSchema dialogStepSchema = dialogSchema.getSteps().get(dialog.getCurrentStep());
		Keyboard keyboard = dialogStepSchema.getKeyboard();
		boolean endStep = false;

		// Execução baseado no tipo do passo---------------------------------
		// Mensagem Simples
		if (dialogStepSchema.getStepType().equals(StepType.SIMPLEMESSAGE)) {
			executeSimpleMessage(botUser, dialogStepSchema, keyboard);
			endStep = true;
		}
		
		//Requisição de contato
		if (dialogStepSchema.getStepType().equals(StepType.REQUESTCONTACT)) {
			if (message.contact() == null) {
				executeRequestContact(botUser, dialogStepSchema);
			} else {
				if(!message.contact().userId().equals(botUser.getId())) {
					executeRequestContact(botUser, dialogStepSchema);
				}else {
					botUserService.updateBotUserContact(botUser, message.contact());
					endStep = true;
				}
			}
		}
		
		//Avanço do passo
		if (endStep) {
			dialog.setCurrentStep(dialog.getCurrentStep() + 1);
		}
		
		// Oficialização das mudanças do diálogo
		if (dialogSchema.getSteps().get(dialog.getCurrentStep()) == null) {
			dialogRepo.delete(dialogRepo.findByBotUserId(botUser.getId()));
		} else {
			dialogRepo.save(dialog);
		}
		
		//Executa próximo passo
		if(endStep && dialogRepo.findByBotUserId(botUser.getId())!=null) {
			executeDialog(message);
		}

	}

	@Override
	public void resetAllDialogs() {
		dialogRepo.deleteAll();
	}
	
	private void executeSimpleMessage(BotUser botUser, DialogStepSchema dialogStepSchema, Keyboard keyboard) {
		List<String> defaultOptions = new LinkedList<String>();
		defaultOptions.add("Menu");
		keyboard  = keyboardService.getSimpleKeyboard(defaultOptions);
		Bot.sendMessage(botUser.getId().toString(), dialogStepSchema.getBotMessage(), keyboard);
	}

	private void executeRequestContact(BotUser botUser, DialogStepSchema dialogStepSchema) {
		Bot.requestContact(botUser.getId().toString(), dialogStepSchema.getBotMessage());
	}

}
