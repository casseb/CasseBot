package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;

public interface DialogSchemaService {

	public void synchronizeDialogSchema(String dialogSchemaName);
	
	public void synchronizeAllDialogSchema();
	
	public DialogSchema findDialogSchemabyNomeSchema(String nomeSchema);
	
	public List<DialogSchema> findAllDialogSchema();
	
	public List<String> findByNoPermissionRequired();
	
}
