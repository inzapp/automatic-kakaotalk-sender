package com.kakaosender.core;

import java.util.List;

import com.kakaosender.domain.HWND;
import com.kakaosender.util.PRes;
import com.kakaosender.view.View;

import javafx.application.Platform;
import javafx.scene.control.Toggle;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * GUI상의 모든 이벤트에 관한 기능을 관리하는 클래스
 * @author root
 *
 */
public class KakaoSendEvent {
	private KakaoSendEvent() {}
	
	private static class Singleton {
		static KakaoSendEvent INSTANCE = new KakaoSendEvent();
	}
	
	public static KakaoSendEvent getInstance() {
		return Singleton.INSTANCE;
	}
	
	/**
	 * 전송버튼의 이벤트로서 할당되는 함수
	 * 현재 선택된 Toggle 값을 받아와 해당 기능을 수행한다
	 * @throws Exception
	 */
	public void send() throws Exception {
		String content = View.content.getText();
		if(content == null || content.equals("")) {
			return;
		}
		
		ClipboardContent clipboardContent = new ClipboardContent();
		clipboardContent.putString(content);
		Platform.runLater(() -> {
			Clipboard.getSystemClipboard().setContent(clipboardContent);
		});
		
		List<HWND> hWndList = null;
		hWndList = HWndManager.getInstance().getAutoDetectHWndList();
		
		if(hWndList.size() == 0) {
			return;
		}
		
		Toggle sendCountToggle = View.sendCountToggleGroup.getSelectedToggle();
		if(sendCountToggle.equals(View.loopRd)) {
			InfiniteSend(hWndList);
		}
		else if(sendCountToggle.equals(View.dummy5Rd)) {
			dummySend(hWndList);
		}
		else if(sendCountToggle.equals(View.customInputRd)) {
			UserInputSend(hWndList);
		}
	}
	
	/**
	 * 무한반복 전송
	 * @param hWndList
	 * @throws Exception
	 */
	private void InfiniteSend(List<HWND> hWndList) throws Exception {
		PRes.isThreadActive = true;
		while(PRes.isThreadActive) {
			System.out.println("loop");
			HWndManager.getInstance().send(hWndList);
			Thread.sleep(81);
		}
	}
	
	/**
	 * 더미(5회 빠르게) 전송
	 * @param hWndList
	 * @throws Exception
	 */
	private void dummySend(List<HWND> hWndList) throws Exception {
		for(int i=0; i<5; i++) {
			HWndManager.getInstance().send(hWndList);
		}
	}
	
	/**
	 * 사용자 입력 직접 전송
	 * @param hWndList
	 * @throws Exception
	 */
	private void UserInputSend(List<HWND> hWndList) throws Exception {
		int userInputCount = Integer.parseInt(View.customInputText.getText());
		PRes.isThreadActive = true;
		for(int i=0; i<userInputCount; i++) {
			if(!PRes.isThreadActive) {
				break;
			}
			
			HWndManager.getInstance().send(hWndList);
			Thread.sleep(81);
		}
		PRes.isThreadActive = false;
	}
}
