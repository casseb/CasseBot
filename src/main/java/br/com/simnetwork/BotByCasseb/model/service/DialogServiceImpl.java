package br.com.simnetwork.BotByCasseb.model.service;

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

	
	//private BotUserServiceImpl botUserService = new BotUserServiceImpl();
	@Autowired
	private DialogRepository dialogRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private BotUserService botUserService;

	@Override
	public void decideDialog(Message message) {
		BotUser botUser = new BotUser(message.from());
		if (!dialogRepo.exists(botUser)) {
			createDialog(message, dialogSchemaService.findDialogSchemabyNomeSchema("|D|DialogSchemaTest2|"));
		}
		executeDialog(message);
	}

	@Override
	public void createDialog(Message message, DialogSchema dialogSchema) {
		BotUser botUser = botUserService.createBotUser(message);
		Dialog dialog = new Dialog(botUser, dialogSchema);
		dialogRepo.save(dialog);
	}

	@Override
	public void executeDialog(Message message) {
		// Preparando dados para execução
		BotUser botUser = botUserService.locateBotUser(message.from().id());
		Dialog dialog = dialogRepo.findOne(botUser);
		DialogSchema dialogSchema = dialog.getDialogSchema();
		DialogStepSchema dialogStepSchema = dialogSchema.getSteps().get(dialog.getCurrentStep());
		Keyboard keyboard = dialogStepSchema.getKeyboard();

		// Execução baseado no tipo do passo
		if (dialogStepSchema.getStepType().equals(StepType.SIMPLEMESSAGE)) {
			executeSimpleMessage(botUser, dialogStepSchema, keyboard);
		}
		
		// Avanço do passo
		dialog.setCurrentStep(dialog.getCurrentStep() + 1);

		// Oficialização das mudanças do diálogo
		if (dialogSchema.getSteps().get(dialog.getCurrentStep()) == null) {
			dialogRepo.delete(botUser);
		} else {
			dialogRepo.save(dialog);
		}

	}

	@Override
	public void resetAllDialogs() {
		dialogRepo.deleteAll();	
	}
	
	private void executeSimpleMessage(BotUser botUser, DialogStepSchema dialogStepSchema, Keyboard keyboard) {
		Bot.sendMessage(botUser.getId().toString(), dialogStepSchema.getBotMessage(),keyboard);
	}

	

}
