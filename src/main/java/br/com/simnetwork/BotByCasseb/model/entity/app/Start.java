package br.com.simnetwork.BotByCasseb.model.entity.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogService;

@Component
public class Start {

	@Autowired
	DialogSchemaService dialogSchemaService;
	@Autowired
	DialogService dialogService;
	
	@PostConstruct
	public void executeSynchronizeAllDialogSchema() {
		dialogSchemaService.synchronizeAllDialogSchema();
	}
	
	@PostConstruct
	public void resetAllDialogs() {
		dialogService.resetAllDialogs();
	}
	
}
