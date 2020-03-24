package com.tsanyi.iApp.GUI.vdn;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsanyi.iApp.DTO.UserDTO;
import com.tsanyi.iApp.service.UserService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
@CssImport(value = "./styles/appLayout.css", themeFor = "vaadin-app-layout")
public class MainView extends VerticalLayout {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService usrService;

	private static final long serialVersionUID = 747317936894781245L;

	public MainView() {
		setWidthFull();		
	}

	@PostConstruct
	public void init() {
		var appLayout = new AppLayout();
		var menuBar = new MenuBar();

		menuBar.addItem("New user", e -> add(createUserDialog()));
		menuBar.addItem("About", e -> new Notification("Nem olyan jó ez a vaadin. Összeugrik, nem látszik, pixelvékony, stb", 10000).open());
		appLayout.addToNavbar(menuBar);
		var btn = new Button("List users");
		btn.setSizeFull();
		appLayout.addToDrawer(btn);

		var grid = new Grid<UserDTO>(UserDTO.class);
		grid.setSizeFull();
		btn.addClickListener(e -> {
			grid.setItems(listUsers());
		});
		var verti = new VerticalLayout(grid);
		//nincs dinamikus méretezés? Ha % van beállítva akkor 1 pixel széles lesz...
		verti.setMinWidth("60em");
		verti.setMinHeight("40em");
		appLayout.setContent(verti);
		add(appLayout);
	}

	private Dialog createUserDialog() {
		var hlayout = new HorizontalLayout();
		var vlayout = new VerticalLayout(hlayout);
		var dialog = new Dialog(vlayout);
		var userDto = new UserDTO();
		var cancelbtn = new Button("Cancel", e -> dialog.close());
		var name = new TextField("Name");
		var birth = new DatePicker("Birth date");
		var email = new TextField("Email");
		var addmail = new Button("Add email", e -> {
			userDto.getEmails().add(email.getValue());
			email.clear();
		});
		var submitbtn = new Button("Add", e -> {
			userDto.setName(name.getValue());
			userDto.setBirthDate(Date.valueOf(birth.getValue()));
			var messages= usrService.createUser(userDto);
			messages.forEach(msg -> new Notification(msg, 5000).open());
			log.debug(userDto.toString()+" messages:"+messages.size());
			dialog.close();
		});
		var div = new HorizontalLayout(submitbtn, cancelbtn);
		div.setWidthFull();
		vlayout.add(div);
		hlayout.add(name, birth, email, addmail);
		dialog.open();
		return dialog;
	}

	private List<UserDTO> listUsers() {
		var users = usrService.getAllUsers();
		log.debug(users.stream().map(user -> user.toString()).collect(Collectors.joining(";", "{", "}")));
		return users;
	}
}
