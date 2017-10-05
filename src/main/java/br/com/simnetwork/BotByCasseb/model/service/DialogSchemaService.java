package br.com.simnetwork.BotByCasseb.model.service;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;

public interface DialogSchemaService {

	public void synchronizeDialogSchema(String dialogSchemaName);
	
	public void synchronizeAllDialogSchema();
	
	public DialogSchema findDialogSchemabyNomeSchema(String nomeSchema);
	
}
