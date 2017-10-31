package br.com.simnetwork.BotByCasseb.model.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Switch;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.Dialog;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStatus;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStepSchema;
import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.StepType;
import br.com.simnetwork.BotByCasseb.model.entity.object.Bot;
import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.entity.object.Field;
import br.com.simnetwork.BotByCasseb.model.entity.object.Record;
import br.com.simnetwork.BotByCasseb.model.entity.object.RecordStatus;
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
	@Autowired
	private DialogStepSchemaService dialogStepSchemaService;
	@Autowired
	private EntityService entityService;
	@Autowired
	private DecisionService decisionService;

	@Override
	public void decideDialog(Update update) {
		User user;
		String callBackData = null;
		Message message;
		if (update.message() != null) {
			user = update.message().from();
			message = update.message();
		} else {
			user = update.callbackQuery().from();
			callBackData = update.callbackQuery().data();
			message = update.callbackQuery().message();
		}
		BotUser botUser = new BotUser(user);
		if (dialogRepo.findByBotUserId(botUser.getId()) == null) {
			createDialog(user, dialogSchemaService.findDialogSchemabyNomeSchema("|D|Menu|"));
		}
		executeDialog(user, message, callBackData);

	}

	@Override
	public void createDialog(User user, DialogSchema dialogSchema) {
		BotUser botUser = botUserService.createBotUser(user);
		Dialog dialog;
		if (botUser.getContact() == null) {
			dialog = new Dialog(botUser, dialogSchemaService.findDialogSchemabyNomeSchema("|D|Bem Vindo|"));
		} else {
			dialog = new Dialog(botUser, dialogSchema);
		}
		dialogRepo.save(dialog);
	}

	@Override
	public void executeDialog(User user, Message message, String callBackData) {

		boolean executeAgain = false;

		// Preparando dados para execução
		BotUser botUser = botUserService.locateBotUser(user.id());
		Dialog dialog = dialogRepo.findOne(botUser);
		DialogSchema dialogSchema = dialog.getDialogSchema();
		DialogStepSchema dialogStepSchema = dialogSchema.getSteps().get(dialog.getCurrentStep());
		Keyboard keyboard = dialogStepSchemaService.getKeyboard(dialogStepSchema);
		InlineKeyboardMarkup inlineKeyboard = dialogStepSchemaService.getInlineKeyboard(dialogStepSchema);

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
					executeAgain = false;
				} else {
					if (!message.contact().userId().equals(botUser.getId())) {
						executeRequestContact(botUser, dialogStepSchema);
						executeAgain = false;
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
					executeAgain = false;
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
				if (!dialog.getDialogStatus().equals(DialogStatus.AGUARDANDO)) {
					executeRequestInlineOption(botUser, dialogStepSchema, inlineKeyboard);
					dialog.setDialogStatus(DialogStatus.AGUARDANDO);
					executeAgain = false;
				} else {
					if (callBackData != null) {
						dialog.addDecision(dialogStepSchema.getKey(), callBackData);
						dialog.setDialogStatus(DialogStatus.INICIO);
						executeAgain = true;
					} else {
						executeRequestInlineOption(botUser, dialogStepSchema, inlineKeyboard);
						executeAgain = false;
					}

				}
			}

			// Requisição de confirmação dos dados
			if (dialogStepSchema.getStepType().equals(StepType.REQUESTCONFIRMATION)) {
				if (!dialog.getDialogStatus().equals(DialogStatus.AGUARDANDO)) {

					StringBuilder updatedMessage = new StringBuilder();
					updatedMessage.append(dialogStepSchema.getBotMessage());
					updatedMessage.append("\n\n");
					for (String decisionKey : dialog.getDecisions().keySet()) {
						updatedMessage.append(decisionKey + " : " + dialog.getDecisions().get(decisionKey) + "\n");
					}
					dialogStepSchema.setBotMessage(updatedMessage.toString());

					List<String> options = new LinkedList<String>();
					options.add("Sim");
					options.add("Não");
					inlineKeyboard = keyboardService.getSimpleInlineKeyboard(options);

					executeRequestInlineOption(botUser, dialogStepSchema, inlineKeyboard);
					dialog.setDialogStatus(DialogStatus.AGUARDANDO);
					executeAgain = false;
				} else {
					if (callBackData != null) {
						if (callBackData.equals("Sim")) {
							dialog.setDialogStatus(DialogStatus.INICIO);
							executeAgain = true;
						} else {
							executeCustomSimpleMessage(botUser, "Ação cancelada", keyboard);
							dialog.setDialogStatus(DialogStatus.FIM);
						}
					} else {
						executeRequestInlineOption(botUser, dialogStepSchema, inlineKeyboard);
						executeAgain = false;
					}
				}
			}

			// Insert de registro no banco
			if (dialogStepSchema.getStepType().equals(StepType.INSERT)) {
				RecordStatus recordStatus = entityService.insertRecordString(dialogStepSchema.getEntity(),
						dialog.getDecisions());
				
				switch (recordStatus) {
				case SUCESSO:
					executeCustomSimpleMessage(botUser, "Registro salvo com sucesso", null);
					break;
				case ENTIDADE_INEXISTENTE:
					executeCustomSimpleMessage(botUser, "Não foi definido a estrutura desta entidade no XML", null);
					break;
				case CAMPO_OBRIGATORIO_NULL:
					executeCustomSimpleMessage(botUser, "Não foi informado todos os campos obrigatórios", null);
					break;
				case CHAVE_NULL:
					executeCustomSimpleMessage(botUser, "Não foi informado a chave do registro", null);
					break;
				default:
					break;
				}
				
				executeAgain = true;
				
			}
			
			// Requisição de Record
			if (dialogStepSchema.getStepType().equals(StepType.REQUESTRECORD)) {
				if (!dialog.getDialogStatus().equals(DialogStatus.AGUARDANDO)) {
					List<Record> records = entityService.findByFields(dialogStepSchema.getEntity(), dialog.getDecisions());
					executeRequestInlineOption(botUser, dialogStepSchema, keyboardService.getRecordInlineKeyboard(records));
					dialog.setDialogStatus(DialogStatus.AGUARDANDO);
					executeAgain = false;
				} else {
					if (callBackData != null) {
						dialog.addDecision(dialogStepSchema.getKey(), callBackData);
						dialog.setDialogStatus(DialogStatus.INICIO);
						executeAgain = true;
					} else {
						executeRequestInlineOption(botUser, dialogStepSchema, inlineKeyboard);
						executeAgain = false;
					}

				}
			}
			
			// Link de um diálogo para outro
			if (dialogStepSchema.getStepType().equals(StepType.LINK)) {
				String dialogName = decisionService.getDecisionsFilter(dialog.getDecisions(), "dialog:").get("unico");
				dialogRepo.delete(dialogRepo.findByBotUserId(botUser.getId()));
				if(dialogStepSchema.getEntity()==null) {
					dialogName = "|D|"+dialogName+"|";
				}else {
					dialogName = "|D|"+dialogName+" "+dialogStepSchema.getEntity()+"|";
				}
				dialogSchema = dialogSchemaService.findDialogSchemabyNomeSchema(dialogName);
				createDialog(user, dialogSchema);
				dialog = dialogRepo.findOne(botUser);
				dialogSchema = dialog.getDialogSchema();
				dialogStepSchema = dialogSchema.getSteps().get(dialog.getCurrentStep());
				keyboard = dialogStepSchemaService.getKeyboard(dialogStepSchema);
				inlineKeyboard = dialogStepSchemaService.getInlineKeyboard(dialogStepSchema);
				dialog.setCurrentStep(0);
				executeAgain = true;
			}
			
			// Mostra os dados de um record
			if (dialogStepSchema.getStepType().equals(StepType.SHOWRECORD)) {
				String recordKey = decisionService.getDecisionsFilter(dialog.getDecisions(), "record:").get("unico");
				String entityName = dialogStepSchema.getEntity();
				Record record = entityService.findByKey(entityName, recordKey);
				StringBuilder resposta = new StringBuilder();
				resposta.append("Registro "+record.getChave()+"\n\n");
				for(String key : record.getCampos().keySet()) {
					resposta.append(key);
					resposta.append(" : ");
					if(record.getCampos().get(key).getValue().equals("True")) {
						resposta.append("Sim");
					}else if(record.getCampos().get(key).getValue().equals("False")) {
						resposta.append("Não");
					}else {
						resposta.append(record.getCampos().get(key).getValue());
					}
					resposta.append("\n");
				}
				executeCustomSimpleMessage(botUser, resposta.toString(), null);
			}
			
			// Inserir registro na lista de decision
			if (dialogStepSchema.getStepType().equals(StepType.INSERTDECISION)) {
				for(String key : dialogStepSchema.getParameters().keySet()) {
					dialog.addDecision(key, dialogStepSchema.getParameters().get(key));
				}
				executeAgain = true;
			}
			
			// Atualizar dados de um record
			if (dialogStepSchema.getStepType().equals(StepType.UPDATE)) {
				Map<String,String> updates = new HashMap<String,String>();
				String recordKey = decisionService.getDecisionsFilter(dialog.getDecisions(), "record:").get("unico");
				String entityName = dialogStepSchema.getEntity();
				Record record = entityService.findByKey(entityName, recordKey);
				updates = decisionService.getDecisionsFilter(dialog.getDecisions(), "update:");
				for(String key : updates.keySet()) {
					
					boolean ok = true;
					if(!key.equals("unico")) {
						if(entityService.getType(record,key).equals("Boolean")) {
							ok = entityService.setValue(record, key, Boolean.parseBoolean(updates.get(key)));
						}else {
							ok = entityService.setValue(record, key, updates.get(key));
						}
						
						if(!ok) {
							executeCustomSimpleMessage(botUser,"Algo deu errado",null);
						}
					}
					
				}
				executeCustomSimpleMessage(botUser, "Registro atualizado", inlineKeyboard);
				executeAgain = true;
				
			}
			
			// Conferindo fim do diálogo
			if (dialog.getDialogStatus().equals(DialogStatus.FIM)) {
				dialogRepo.delete(dialogRepo.findByBotUserId(botUser.getId()));
			}

			// Avanço do passo
			if (executeAgain) {
				dialog.setCurrentStep(dialog.getCurrentStep() + 1);
			}

			// Oficialização das mudanças do diálogo
			if (!dialog.getDialogStatus().equals(DialogStatus.FIM)) {
				if (dialogSchema.getSteps().get(dialog.getCurrentStep()) == null) {
					dialogRepo.delete(dialogRepo.findByBotUserId(botUser.getId()));
					executeAgain = false;
				} else {
					dialogRepo.save(dialog);
					dialogStepSchema = dialog.getDialogSchema().getSteps().get(dialog.getCurrentStep());
					keyboard = dialogStepSchemaService.getKeyboard(dialogStepSchema);
					inlineKeyboard = dialogStepSchemaService.getInlineKeyboard(dialogStepSchema);
				}
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

	private void executeRequestInlineOption(BotUser botUser, DialogStepSchema dialogStepSchema,
			InlineKeyboardMarkup keyboard) {
		Bot.requestInlineOption(botUser.getId().toString(), dialogStepSchema.getBotMessage(), keyboard);
	}

}
