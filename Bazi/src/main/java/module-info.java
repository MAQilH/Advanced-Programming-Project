module Bazi {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires net.synedra.validatorfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	requires eu.hansolo.tilesfx;
	requires com.almasb.fxgl.all;
	requires javafx.media;
	requires annotations;
	requires java.desktop;
	requires com.google.gson;
	requires org.json;
	requires jakarta.mail;
    requires java.sql;

	requires junit;
	requires jdk.httpserver;


	exports ir.sharif.view to javafx.graphics, com.google.gson;
	exports ir.sharif.view.controllers to javafx.fxml;
	exports ir.sharif.model to com.google.gson;
	exports ir.sharif.messages to com.google.gson;
	exports ir.sharif.enums to com.google.gson;
	exports ir.sharif.model.game to com.google.gson;
	exports ir.sharif.messages.react to com.google.gson;
	exports ir.sharif.messages.friends to com.google.gson;
	exports ir.sharif.messages.chat to com.google.gson;
	exports ir.sharif.model.server to com.google.gson;
	exports ir.sharif.messages.Game to com.google.gson;

	opens ir.sharif.view to javafx.fxml, com.google.gson;
	opens ir.sharif.view.controllers to javafx.fxml;
	opens ir.sharif.model to com.google.gson;
	exports ir.sharif.controller to javafx.fxml;
	opens ir.sharif.controller to javafx.fxml;
	opens ir.sharif.messages to com.google.gson;
	opens ir.sharif.messages.chat to com.google.gson;
	opens ir.sharif.messages.friends to com.google.gson;
	opens ir.sharif.enums to com.google.gson;
	opens ir.sharif.model.game to com.google.gson;
	opens ir.sharif.messages.react to com.google.gson;
    opens ir.sharif.messages.Game to com.google.gson;
	opens ir.sharif.model.server to com.google.gson;
    exports ir.sharif.messages.tournament to com.google.gson;
    opens ir.sharif.messages.tournament to com.google.gson;

	exports ir.sharif.tests to junit;
	opens ir.sharif.tests to junit;
	exports ir.sharif.server to com.google.gson, jakarta.mail, jdk.httpserver;
	opens ir.sharif.server to com.google.gson, jakarta.mail, jdk.httpserver;
}