/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.util.EnumMap;

/**
 *
 * @author sk
 */
enum WindowEventListeners {
	
	// TODO シングルトンじゃない何かにする？
	INSTANCE;

	private final EnumMap<WindowEvents, WindowEventListener> listenerMap = new EnumMap<>(WindowEvents.class);

	void addListener(WindowEvents events, WindowEventListener listener) {
		listenerMap.put(events, listener);
	}

	void notify(WindowEvents event) {
		WindowEventListener listener = listenerMap.get(event);
		if (listener == null) {
			throw new RuntimeException("event " + event + " is not found in listeners.");
		}
		listener.handleEvent();
	}
}
