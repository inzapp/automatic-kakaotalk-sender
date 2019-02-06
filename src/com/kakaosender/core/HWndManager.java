package com.kakaosender.core;

import java.util.ArrayList;
import java.util.List;

import com.kakaosender.domain.HWND;
import com.kakaosender.user32.User32;

/**
 * 카카오톡 채팅방의 핸들을 탐색해 리스트형태로 리턴하는 클래스
 * 모든 핸들을 탐색하되 카카오톡 채팅클래스 영역인 RichEdit20W을
 * 자식 핸들로서 가지고 있는지에대한 여부로 
 * 해당 창이 카카오톡 창인지를 판단한다
 * @author root
 *
 */
public class HWndManager {
	private HWndManager() {}
	
	private static class Singleton {
		static final HWndManager INSTANCE = new HWndManager();
	}
	
	public static HWndManager getInstance() {
		return Singleton.INSTANCE;
	}
	
	
	/**
	 * 모든 핸들을 탐색해 카카오톡 채팅방 핸들만을 추출해 리스트로 리턴한다
	 * @return
	 */
	public List<HWND> getAutoDetectHWndList() {
		List<HWND> hWndList = new ArrayList<>();
		
		long hWnd = User32.INSTANCE.FindWindowA(0, 0);
		long cWnd;
		while(true) {
			cWnd = User32.INSTANCE.FindWindowExA(hWnd, 0, "RichEdit20W", 0);
			if(cWnd != 0) {
				hWndList.add(new HWND(hWnd, cWnd));
			}
			
			hWnd = User32.INSTANCE.GetWindow(hWnd, User32.GW_HWNDNEXT);
			if(hWnd == 0) {
				break;
			}
		}
		
		return hWndList;
	}
	
	
	/**
	 * 클립보드에 저장된 내용을 카카오톡 채팅방에 전송해주는 함수
	 * KakaoSendEvent클래스 에서 사용된다
	 * @param hWndList
	 * @throws Exception
	 */
	public void send(List<HWND> hWndList) throws Exception {
		for(HWND hWnd : hWndList) {
			User32.INSTANCE.ShowWindowAsync(hWnd.gethWnd(), User32.SW_SHOWNORMAL);
			Thread.sleep(10);
			User32.INSTANCE.SetForegroundWindow(hWnd.gethWnd());
			Thread.sleep(10);
			
			User32.INSTANCE.SendMessageA(hWnd.getcWnd(), User32.WM_PASTE, 0, 0);
			Thread.sleep(50);
		}
		
		for(HWND hWnd : hWndList) {
			User32.INSTANCE.SendMessageA(hWnd.getcWnd(), User32.WM_IME_KEYDOWN, User32.VK_RETURN, 0);
			Thread.sleep(50);
		}
	}
}
