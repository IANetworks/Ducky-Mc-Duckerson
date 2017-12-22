package io.github.mannjamin.ducky.eventmanager;

import io.github.mannjamin.ducky.EventListener;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import net.dv8tion.jda.core.entities.TextChannel;

public class ConsoleMessage implements Emitter.Listener {
    Socket socket;
    EventListener eventListener;
    TextChannel channel;

    public ConsoleMessage(Socket socket, EventListener eventListener, TextChannel channel) {
        this.socket = socket;
        this.eventListener = eventListener;
        this.channel = channel;
    }

    @Override
    public void call(Object... args) {

        eventListener.onConsoleMessage(channel, args);
    }
}
