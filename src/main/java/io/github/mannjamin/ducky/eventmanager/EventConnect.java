package io.github.mannjamin.ducky.eventmanager;

import io.github.mannjamin.ducky.EventListener;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EventConnect implements Emitter.Listener {
    Socket socket;
    EventListener eventListener;

    public EventConnect(Socket socket, EventListener eventListener) {
        this.socket = socket;
        this.eventListener = eventListener;
    }

    @Override
    public void call(Object... args) {
        socket.emit("consoleLog", "Bot is running");
    }
}
