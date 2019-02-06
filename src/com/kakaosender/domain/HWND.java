package com.kakaosender.domain;

/**
 * Win32Api의 HWND를 관리하기 위한 ValueObject
 * 현재 핸들과 자손 핸들을 가지고 있다
 * @author root
 *
 */
public class HWND {
	private long hWnd;
	private long cWnd;
	
	public HWND(long hWnd2, long cWnd2) {
		this.hWnd = hWnd2;
		this.cWnd = cWnd2;
	}

	public long gethWnd() {
		return hWnd;
	}

	public long getcWnd() {
		return cWnd;
	}
}
