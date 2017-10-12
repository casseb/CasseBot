package br.com.simnetwork.BotByCasseb.model.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;

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
	private DialogRepository dialogRepo;
	@Autowired
	private DialogSchemaService dialogSchemaService;
	@Autowired
	private BotUserService botUserService;
	@Autowired
	private KeyboardService keyboardService;

	@Override
	public void decideDialog(Update update) {
		Message message;
		if (update.message() != null) {
			message = update.message();
		} else {
			message = update.callbackQuery().message();
		}
		BotUser botUser = new BotUser(message.from());
		if (dialogRepo.findByBotUserId(botUser.getId()) == null) {
			createDialog(message, dialogSchemaService.findDialogSchemabyNomeSchema("|D|Menu|"));
		}
		executeDialog(message);

	}

	@Override
	public void createDialog(Message message, DialogSchema dialogSchema) {
		BotUser botUser = botUserService.createBotUser(message);
		Dialog dialog;
		if (botUser.getContact() == null) {
			dialog = new Dialog(botUser, dialogSchemaService.findDialogSchemabyNomeSchema("|D|Bem Vindo|"));
		} else {
			dialog = new Dialog(botUser, dialogSchema);
		}
		dialogRepo.save(dialog);
	}

	@Override
	public void executeDialog(Message message) {

		boolean executeAgain = false;

		// Preparando dados para execução
		BotUser botUser = botUserService.locateBotUser(message.from().id());
		Dialog dialog = dialogRepo.findOne(botUser);
		DialogSchema dialogSchema = dialog.getDialogSchema();
		DialogStepSchema dialogStepSchema = dialogSchema.getSteps().get(dialog.getCurrentStep());
		Keyboard keyboard = dialogStepSchema.getKeyboard();
		InlineKeyboardMarkup inlineKeyboard = dialogStepSchema.getInlineKeyboard();

		do {

			// Execução baseado no tipo do passo---------------------------------
			// Mensagem Simples
			if (dialogStepSchema.getStepType().equals(StepType.SIMPLEMESSAGE)) {
				executeSchemaSimpleMessage(botUser, dialogStepSchema, keyboard);
				executeAgain = true;
			}

			// Requisição de contato
			if (dialogStepSchema.getStepType().equals(StepType.REQUESTCONTACT)) {
				if (message.contact() == null) {
					executeRequestContact(botUser, dialogStepSchema);
				} else {
					if (!message.contact().userId().equals(botUser.getId())) {
						executeRequestContact(botUser, dialogStepSchema);
					} else {
						botUserService.updateBotUserContact(botUser, message.contact());
						executeAgain = true;
					}
				}
			}

			// Requisição de String
			if (dialogStepSchema.getStepType().equals(StepType.REQUESTSTRING)) {
				if (!dialog.getDialogStatus().equals(DialogStatus.AGUARDANDO)) {
					executeSchemaSimpleMessage(botUser, dialogStepSchema, keyboard);
					dialog.setDialogStatus(DialogStatus.AGUARDANDO);
				} else {
					dialog.addDecision(dialogStepSchema.getKey(), message.text());
					dialog.setDialogStatus(DialogStatus.INICIO);
					executeAgain = true;
				}
			}

			// Mensagem Customizada
			if (dialogStepSchema.getStepType().equals(StepType.CUSTOMMESSAGE)) {
				String text = dialogStepSchema.getBotMessage();
				for (String decision : dialog.getDecisions().keySet()) {
					String decisionChanged = "{{{" + decision + "}}}";
					text = text.replace(decisionChanged, dialog.getDecisions().get(decision));
				}
				executeCustomSimpleMessage(botUser, text, keyboard);
				executeAgain = true;
			}

			// Requisição de escolha inline
			if (dialogStepSchema.getStepType().equals(StepType.REQUESTINLINEOPTION)) {
				if(!dialog.getDialogStatus().equals(DialogStatus.AGUARDANDO)) {
					executeRequestInlineOption(botUser, dialogStepSchema, inlineKeyboard);
					dialog.setDialogStatus(DialogStatus.AGUARDANDO);
				}else {
					dialog.addDecision(dialogStepSchema.getKey(), message.text());
					dialog.setDialogStatus(DialogStatus.INICIO);
					executeAgain = true;
				}
			}

			// Avanço do passo
			if (executeAgain) {
				dialog.setCurrentStep(dialog.getCurrentStep() + 1);
			}

			// Oficialização das mudanças do diálogo
			if (dialogSchema.getSteps().get(dialog.getCurrentStep()) == null) {
				dialogRepo.delete(dialogRepo.findByBotUserId(botUser.getId()));
				executeAgain = false;
			} else {
				dialogRepo.save(dialog);
				dialogStepSchema = dialog.getDialogSchema().getSteps().get(dialog.getCurrentStep());
				keyboard = dialogStepSchema.getKeyboard();
				inlineKeyboard = dialogStepSchema.getInlineKeyboard();
			}

		} while (executeAgain);

	}

	@Override
	public void resetAllDialogs() {
		dialogRepo.deleteAll();
	}

	private void executeSchemaSimpleMessage(BotUser botUser, DialogStepSchema dialogStepSchema, Keyboard keyboard) {
		Bot.sendMessage(botUser.getId().toString(), dialogStepSchema.getBotMessage(), keyboard);
	}

	private void executeCustomSimpleMessage(BotUser botUser, String text, Keyboard keyboard) {
		Bot.sendMessage(botUser.getId().toString(), text, keyboard);
	}

	private void executeRequestContact(BotUser botUser, DialogStepSchema dialogStepSchema) {
		Bot.requestContact(botUser.getId().toString(), dialogStepSchema.getBotMessage());
	}
	
	private void executeRequestInlineOption(BotUser botUser, DialogStepSchema dialogStepSchema, InlineKeyboardMarkup keyboard){
		Bot.requestInlineOption(botUser.getId().toString(), dialogStepSchema.getBotMessage(), keyboard);
	}

}
