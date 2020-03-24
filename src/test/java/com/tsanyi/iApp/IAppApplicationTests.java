package com.tsanyi.iApp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tsanyi.iApp.DTO.UserDTO;
import com.tsanyi.iApp.service.UserService;

@SpringBootTest
class IAppApplicationTests {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private static ThreadLocalRandom rand = ThreadLocalRandom.current();
	
	@Autowired
	private UserService userService;
	
	@Test
	void addUsers() {
		var users= new HashSet<UserDTO>();
		var messages= new HashSet<String>();
		users.add(new UserDTO("Szalacsi Sándor"+rand.nextInt(1000), new Date(rand.nextLong(System.currentTimeMillis())), generateRandomEmailAddress(2)));
		users.add(new UserDTO("Árpádus"+rand.nextInt(1000), new Date(rand.nextLong(System.currentTimeMillis())), generateRandomEmailAddress(3)));
		users.addAll(generateRandomUserDTOs(rand.nextInt(20)));
		users.forEach( u -> messages.addAll(userService.createUser(u)));
		messages.forEach(msg -> log.warn(msg));
	}
	@Test
	void addUsersValidationFail() {
		var sandor= new UserDTO("", new Date(52352523), Set.of("quss@quss.qu", "asdfasfasf@afasfgasdg.com", "afasfasfasfasfdhdf@ewwtwet.hu"));
		var arpadus= new UserDTO("Árpádus", new Date(1084975630322l), Set.of("rhfdhdf@", "gsdg@dfhdf.url", "54457sdgsdgs@ewwtwet.hu"));
		var messages= userService.createUser(sandor);
		messages.addAll(userService.createUser(arpadus));
		messages.forEach(msg -> log.error(msg));
		assertEquals(2, messages.size());
	}
	@Test
	void listAll() {
		var users= userService.getAllUsers();
		log.info("listAll"+users.stream().map(user -> user.toString()).collect(Collectors.joining(";", "{", "}")));
	}
	
	private static Set<UserDTO> generateRandomUserDTOs(int count) {
		var randomUsers= new HashSet<UserDTO>();
		for (int i = 0; i < count; i++) {
			var randomUser= new UserDTO(RandomStringUtils.randomAlphabetic(6, 40), new Date(rand.nextLong(System.currentTimeMillis())), generateRandomEmailAddress(rand.nextInt(0, 8)));
			randomUsers.add(randomUser);
		}
		return randomUsers;
	}
	
	private static Set<String> generateRandomEmailAddress(int count) {
		var randomMails= new HashSet<String>();
		for (int i = 0; i < count; i++) {
			var randomMail= RandomStringUtils.randomAlphabetic(4,16)+"@"+RandomStringUtils.randomAlphabetic(4,8)+"."+RandomStringUtils.randomAlphabetic(2);
			randomMails.add(randomMail);
		}
		return randomMails;
	}
}
