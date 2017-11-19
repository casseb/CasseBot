package br.com.simnetwork.BotByCasseb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogStepSchema;

@Service("dialogStepSchemaService")
public class DialogStepSchemaServiceImpl implements DialogStepSchemaService{
	
	@Autowired
	KeyboardService keyboardService;
	@Autowired
	DynamicListService dynamicListService;
	
	@Override
	public Keyboard getKeyboard(DialogStepSchema dialogStepSchema) {
		if(!dialogStepSchema.getKeyboardOptions().isEmpty()) {
			return keyboardService.getSimpleKeyboard(dialogStepSchema.getKeyboardOptions());
		}else {
			return keyboardService.getDefaultKeyboard();
		}
	}

	@Override
	public InlineKeyboardMarkup getInlineKeyboard(DialogStepSchema dialogStepSchema) {
		if(!dialogStepSchema.getInlineKeyboard().isEmpty()) {
			return keyboardService.getSimpleInlineKeyboard(dialogStepSchema.getInlineKeyboard());
		}else if(dialogStepSchema.getDynamicList() != null){
			return keyboardService.getSimpleInlineKeyboard(dynamicListService.getFormattedValues(dialogStepSchema.getDynamicList()));
		}else {
			return null;
		}
	}
	
}
