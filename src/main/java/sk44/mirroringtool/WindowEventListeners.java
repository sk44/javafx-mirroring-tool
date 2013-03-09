/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sk
 */
enum WindowEventListeners {
	
	// TODO シングルトンじゃない何かにする
	INSTANCE;

	enum Events {
		ON_OPEN_TASK_FORM,
		ON_CLOSE_TASK_FORM;
	}

	private final EnumMap<Events, WindowEventListener> listenerMap = new EnumMap<>(Events.class);

	void addListener(Events events, WindowEventListener listener) {
		listenerMap.put(events, listener);
	}

	void fire(Events event) {
		WindowEventListener listener = listenerMap.get(event);
		if (listener == null) {
			throw new RuntimeException("event " + event + " is not found in listeners.");
		}
		listener.handleEvent();
	}
}
