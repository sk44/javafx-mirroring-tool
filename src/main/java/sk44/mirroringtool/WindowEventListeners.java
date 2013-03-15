/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 *
 * @author sk
 */
enum WindowEventListeners {

    // TODO シングルトンじゃない何かにする？
    INSTANCE;
    private final EnumMap<WindowEvents, List<WindowEventListener>> listenerMap = new EnumMap<>(WindowEvents.class);

    void addListener(WindowEvents events, WindowEventListener listener) {
        getEventListenersOf(events).add(listener);
    }

    void notify(WindowEvents event) {
        for (WindowEventListener listener : getEventListenersOf(event)) {
            listener.handleEvent();
        }
    }

    List<WindowEventListener> getEventListenersOf(WindowEvents events) {
        List<WindowEventListener> values = listenerMap.get(events);
        if (values == null) {
            values = new ArrayList<>();
        }
        listenerMap.put(events, values);
        return values;
    }
}
