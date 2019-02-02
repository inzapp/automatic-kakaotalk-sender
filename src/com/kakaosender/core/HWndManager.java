package com.kakaosender.core;

import java.util.ArrayList;
import java.util.List;

import com.kakaosender.domain.HWND;
import com.kakaosender.user32.User32;

public class HWndManager {
	private HWndManager() {}
	
	private static class Singleton {
		static final HWndManager INSTANCE = new HWndManager();
	}
	
	public static HWndManager getInstance() {
		return Singleton.INSTANCE;
	}
	
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
