package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.simnetwork.BotByCasseb.model.entity.dialog.structure.DialogSchema;
import br.com.simnetwork.BotByCasseb.model.repository.DialogSchemaRepository;

@Service("dialogSchemaService")
public class DialogSchemaServiceImpl implements DialogSchemaService{

	@Autowired
	private DialogSchemaRepository dialogSchemaRepo;
	@Autowired
	private ContextService contextService;
	
	@Override
	public void synchronizeDialogSchema(String dialogSchemaName) {
		Object dialogSchemaObject = contextService.getObjectBean(dialogSchemaName, DialogSchema.class);
		if(dialogSchemaObject != null) {
			DialogSchema dialogSchema = (DialogSchema) dialogSchemaObject;
			dialogSchemaRepo.save(dialogSchema);
		}
	}

	@Override
	public void synchronizeAllDialogSchema() {
		dialogSchemaRepo.deleteAll();
		
		for(String dialogSchemaName : contextService.getDialogSchemasBeanDefinitionNames(true)) {
			synchronizeDialogSchema(dialogSchemaName);
		}
		
	}

	@Override
	public DialogSchema findDialogSchemabyNomeSchema(String nomeSchema) {
		DialogSchema dialogSchema = dialogSchemaRepo.findOne(nomeSchema);
		return dialogSchema;
	}

	@Override
	public List<DialogSchema> findAllDialogSchema() {
		return dialogSchemaRepo.findAll();
	}

}
