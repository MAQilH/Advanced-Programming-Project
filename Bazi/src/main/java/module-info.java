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

	exports ir.sharif.view to javafx.graphics, com.google.gson;
	exports ir.sharif.view.controllers to javafx.fxml;
	exports ir.sharif.model to com.google.gson;
	exports ir.sharif.messages to com.google.gson;
	exports ir.sharif.enums to com.google.gson;
	exports ir.sharif.model.game to com.google.gson;

	opens ir.sharif.view to javafx.fxml, com.google.gson;
	opens ir.sharif.view.controllers to javafx.fxml;
	opens ir.sharif.model to com.google.gson;
	exports ir.sharif.controller to javafx.fxml;
	opens ir.sharif.controller to javafx.fxml;
	opens ir.sharif.messages to com.google.gson;
	exports ir.sharif.messages.Chat to com.google.gson;
	opens ir.sharif.messages.Chat to com.google.gson;
	exports ir.sharif.messages.Friends to com.google.gson;
	opens ir.sharif.messages.Friends to com.google.gson;
	opens ir.sharif.enums to com.google.gson;
	opens ir.sharif.model.game to com.google.gson;
}