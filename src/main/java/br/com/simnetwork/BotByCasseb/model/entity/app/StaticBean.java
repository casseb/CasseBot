package br.com.simnetwork.BotByCasseb.model.entity.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.MongoDbFactoryParser;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import lombok.Data;

/**
 * Responsável por injetar as dependências do que esta definido nos xml.
 * <p>
 * <b>Observações de uso</b>:
 * <ul>
 * <li>Classe com métodos estáticos, deve ser usado diretamente para injetar os
 * beans da aplicação.</li>
 * </ul>
 * 
 * @author <a href="mailto:felipe.casseb@gmail.com">By Casseb</a>
 * @since 1.5
 */
@Data
public class StaticBean {
	
	/**
	 * Context com as injeções dos diálogos estáticos.
	 * <p>
	 * Context utilizado para injetar os diálogos estáticos (Diálogos obrigatórios
	 * para a execução mínima da aplicação, como definição do nome na aplicação e
	 * controle de permissões) presentes em staticDialogContext.xml.
	 */
	private static ClassPathXmlApplicationContext staticDialogContext = new ClassPathXmlApplicationContext(
			"staticDialogContext.xml");
	
	/**
	 * Context com as injeções dos diálogos dinâmicos.
	 * <p>
	 * Context utilizado para injetar os diálogos e entidades dinâmicas (Diálogos e
	 * entidades definidas baseado na regra de negócio) presente em dynamicDialogContext.
	 */
	private static ClassPathXmlApplicationContext dynamicDialogContext = new ClassPathXmlApplicationContext(
			"dynamicDialogContext.xml");
	
	private static ClassPathXmlApplicationContext testContext = new ClassPathXmlApplicationContext(
			"testContext.xml");

	public static ClassPathXmlApplicationContext getStaticDialogContext() {
		return staticDialogContext;
	}

	public static ClassPathXmlApplicationContext getDynamicDialogContext() {
		return dynamicDialogContext;
	}

	public static ClassPathXmlApplicationContext getTestContext() {
		return testContext;
	}
	
	
}
