package com.kakaosender.domain;

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
