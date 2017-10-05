package br.com.simnetwork.BotByCasseb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.Dialog;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStatus;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStepSchema;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.StepType;
import br.com.simnetwork.BotByCasseb.model.entity.object.Bot;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.repository.DialogRepository;

@Service("dialogService")
public class DialogServiceImpl implements DialogService {

	@Autowired
	private BotUserService botUserService;
	@Autowired
	private DialogRepository dialogRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;

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

		// Execução baseado no tipo do passo
		if (dialogStepSchema.getStepType().equals(StepType.SIMPLEMESSAGE)) {
			executeSimpleMessage(botUser, dialogStepSchema);
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

	private void executeSimpleMessage(BotUser botUser, DialogStepSchema dialogStepSchema) {
		Bot.sendMessage(botUser.getId().toString(), dialogStepSchema.getBotMessage());
	}

	@Override
	public void resetAllDialogs() {
		dialogRepo.deleteAll();
		
	}

}
