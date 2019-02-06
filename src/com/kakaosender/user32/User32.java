package com.kakaosender.user32;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Win32Api와 Mapping을 하기 위한 클레스
 * @author root
 *
 */
public interface User32 extends Library {
    public User32 INSTANCE = Native.load("user32", User32.class);
    
    static long GW_HWNDNEXT = 2;
    
    static long WM_IME_KEYDOWN = 0x0290;
    
    static long VK_RETURN = 0x0D;
    
    static long SW_SHOWNORMAL = 1;
    
    static long WM_PASTE = 0x0302;
    
    long FindWindowA(int lpClassName, int lpWindowName);
    
    long FindWindowExA(long hwndParent, long hwndChildAfter, String lpszClass, long lpszWindow);
    
    long GetWindow(long hWnd, long gwHwndnext);
    
    void ShowWindowAsync(long hWnd, long swShownormal);
    
    void SetForegroundWindow(long hWnd);
    
    void SendMessageA(long hWnd, long msg, long wParam, long lParam);
}