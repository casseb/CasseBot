package br.com.simnetwork.BotByCasseb.model.entity.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.simnetwork.BotByCasseb.model.service.DialogSchemaService;
import br.com.simnetwork.BotByCasseb.model.service.DialogService;
import br.com.simnetwork.BotByCasseb.model.service.EntityService;

@Component
public class Start {

	@Autowired
	DialogSchemaService dialogSchemaService;
	@Autowired
	DialogService dialogService;
	@Autowired
	EntityService entityService;
	
	@PostConstruct
	public void executeSynchronizeAllDialogSchema() {
		dialogSchemaService.synchronizeAllDialogSchema();
	}
	
	@PostConstruct
	public void resetAllDialogs() {
		dialogService.resetAllDialogs();
	}
	
	@PostConstruct
	public void executeSynchronizeStaticEntities() {
		entityService.synchronizeStaticEntities();
	}
	
}
