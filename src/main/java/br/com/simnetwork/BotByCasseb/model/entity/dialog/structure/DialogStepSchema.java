package br.com.simnetwork.BotByCasseb.model.entity.dialog.structure;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class DialogStepSchema {

	@Id @NonNull
	private String nomeStep;
	private String botMessage;
	private StepType stepType;
	
}
