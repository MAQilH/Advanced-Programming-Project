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
	requires org.json;

	exports ir.sharif.view to javafx.graphics;
	exports ir.sharif.view.controllers to javafx.fxml;

	opens ir.sharif.view to javafx.fxml;
	opens ir.sharif.view.controllers to javafx.fxml;
}