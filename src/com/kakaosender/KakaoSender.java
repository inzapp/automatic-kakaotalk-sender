package com.kakaosender;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

import com.kakaosender.core.KakaoSendEvent;
import com.kakaosender.util.PRes;
import com.kakaosender.view.View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class KakaoSender extends Application implements Initializable{
	
	@FXML
	private RadioButton loopRd, dummy5Rd, customInputRd;
	@FXML
	private TextField customInputText;
	@FXML
	private TextArea content;
	
	@FXML
	private Button startBt, stopBt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PRes.threadPool = Executors.newSingleThreadExecutor();
		
		View.loopRd = this.loopRd;
		View.dummy5Rd = this.dummy5Rd;
		View.customInputRd = this.customInputRd;
		View.customInputText = this.customInputText;
		View.content = this.content;
		
		View.startBt = this.startBt;
		View.stopBt = this.stopBt;
		
		View.sendCountToggleGroup = new ToggleGroup();
		View.loopRd.setToggleGroup(View.sendCountToggleGroup);
		View.dummy5Rd.setToggleGroup(View.sendCountToggleGroup);
		View.customInputRd.setToggleGroup(View.sendCountToggleGroup);
		View.loopRd.setSelected(true);
		
		View.startBt.setOnMouseClicked(event -> {
			PRes.threadPool.execute(() -> {
				try {
					KakaoSendEvent.getInstance().send();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		
		View.stopBt.setOnMouseClicked(event -> {
				PRes.isThreadActive = false;
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent fxml = FXMLLoader.load(getClass().getResource("kakaosender.fxml"));
		Scene scene = new Scene(fxml);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("KakaoSender");
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(event -> System.exit(0));
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
