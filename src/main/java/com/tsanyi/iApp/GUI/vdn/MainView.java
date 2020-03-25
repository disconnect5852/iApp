package com.tsanyi.iApp.GUI.vdn;

import java.time.ZoneId;
import java.util.Date;
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
		menuBar.addItem("About", e -> new Notification("Nem olyan jó ez a vaadin. Összeugrik, nem látszik, pixelvékony, stb...", 10000).open());
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
		var newUserDto = new UserDTO();
		var nameField = new TextField("Name");
		var birthField = new DatePicker("Birth date");
		var emailField = new TextField("Email");
		var addmail = new Button("Add email", e -> {
			newUserDto.getEmails().add(emailField.getValue());
			emailField.clear();
		});
		var submitbtn = new Button("Add", e -> {
			//validation could (also) happen here, but that's complicated for a simple test...
			newUserDto.setName(nameField.getValue());
			newUserDto.setBirthDate(Date.from(birthField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			var messages= usrService.createUser(newUserDto);
			messages.forEach(msg -> new Notification(msg, 5000).open());
			log.debug(newUserDto.toString()+" messages:"+messages.size());
			dialog.close();
		});
		var horizontalBtnLayout = new HorizontalLayout(submitbtn, new Button("Cancel", e -> dialog.close()));
		horizontalBtnLayout.setWidthFull();
		vlayout.add(horizontalBtnLayout);
		hlayout.add(nameField, birthField, emailField, addmail);
		dialog.open();
		return dialog;
	}

	private List<UserDTO> listUsers() {
		var users = usrService.getAllUsers();
		log.debug(users.stream().map(
				user -> user.toString()).
				collect(Collectors.joining(";", "{", "}"))
				);
		return users;
	}
}
