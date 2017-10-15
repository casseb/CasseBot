package br.com.simnetwork.BotByCasseb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import br.com.simnetwork.BotByCasseb.model.entity.object.BotUser;
import br.com.simnetwork.BotByCasseb.model.repository.BotUserRepository;

@Service("botUserService")
public class BotUserServiceImpl implements BotUserService{

	@Autowired
	private BotUserRepository botUserRepo;
	
	@Override
	public BotUser createBotUser(User user) {
		if(!botUserRepo.exists(user.id())){
			BotUser botUser = new BotUser(user);
			botUserRepo.save(botUser);
			return botUser;
		}else {
			return botUserRepo.findOne(user.id());
		}
	}

	@Override
	public BotUser locateBotUser(Integer id) {
		return botUserRepo.findOne(id);
	}

	@Override
	public void updateBotUserContact(BotUser botUser, Contact contact) {
		botUser.setContact(contact.phoneNumber());
		botUserRepo.save(botUser);
	}

	@Override
	public List<BotUser> locateAllBotUsers() {
		return botUserRepo.findAll();
	}

}
