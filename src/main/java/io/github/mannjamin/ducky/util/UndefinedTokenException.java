package io.github.mannjamin.ducky.util;

/**
 * An exception indicating the token for the bot is undefined or null
 *
 * @author Matthew Struble
 */
public class UndefinedTokenException extends Exception {
    UndefinedTokenException() {
        super("Bot token was not specified and must be given for the bot to start");
    }
}
