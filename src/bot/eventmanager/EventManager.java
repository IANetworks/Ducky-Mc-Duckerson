package bot.eventmanager;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The type Event manager.
 */
public class EventManager {

    /**
     * On jda event. Parse JDA Events and print a JSON format to stdout
     *
     * @param event the event
     */
    public void onJDAEvent(Event event) {
        List<String> trackMethodLevel0 = new ArrayList<>();
        List<String> trackMethodLevel1 = new ArrayList<>();
        List<String> trackMethodLevel2 = new ArrayList<>();
        List<String> basicClass = new ArrayList<>();

        //first level methods
        trackMethodLevel0.add("getDisconnectedTime");
        trackMethodLevel0.add("isClosedByServer");
        trackMethodLevel0.add("getClosedCode");
        trackMethodLevel0.add("getCause");
        trackMethodLevel0.add("isLogged");
        trackMethodLevel0.add("getShutdownTime");
        trackMethodLevel0.add("getCode");
        trackMethodLevel0.add("getOldStatus");
        trackMethodLevel0.add("getStatus");
        trackMethodLevel0.add("getCategory");
        trackMethodLevel0.add("getIdLong");
        trackMethodLevel0.add("getGuild");
        trackMethodLevel0.add("getResponseNumber");
        trackMethodLevel0.add("isManaged");
        trackMethodLevel0.add("getEmote");
        trackMethodLevel0.add("getNewName");
        trackMethodLevel0.add("getOldName");
        trackMethodLevel0.add("getMember");
        trackMethodLevel0.add("getUser");
        trackMethodLevel0.add("getNewNick");
        trackMethodLevel0.add("getPrevNick");
        trackMethodLevel0.add("getVoiceState");
        trackMethodLevel0.add("getChannel");
        trackMethodLevel0.add("getChannelType");
        trackMethodLevel0.add("getMessageIdLong");
        //trackMethodLevel0.add("getMessageEmbeds"); -- Embeds contains a lot of varible infomation
        trackMethodLevel0.add("getAuthor");
        trackMethodLevel0.add("getMessage");
        trackMethodLevel0.add("getReaction");
        trackMethodLevel0.add("getPrivateChannel");
        trackMethodLevel0.add("getTextChannel");
        trackMethodLevel0.add("getRole");
        trackMethodLevel0.add("getOldDiscriminator");
        trackMethodLevel0.add("getOldName");
        trackMethodLevel0.add("getPreviousStatus");

        //second level, from those that doesn't return strings/boolean/int/long
        trackMethodLevel1.add("getCode");
        trackMethodLevel1.add("getMeaning");
        trackMethodLevel1.add("isReconnected");
        trackMethodLevel1.add("toString");
        trackMethodLevel1.add("getName");
        trackMethodLevel1.add("getIdLong");
        trackMethodLevel1.add("getAsMention");
        trackMethodLevel1.add("getEffectiveName");
        trackMethodLevel1.add("isBot");
        trackMethodLevel1.add("getTitle");
        trackMethodLevel1.add("getRawContent");
        trackMethodLevel1.add("isEdited");
        trackMethodLevel1.add("getCount");
        trackMethodLevel1.add("isEmote");

        trackMethodLevel2.add("toString");
        trackMethodLevel2.add("getIdLong");
        trackMethodLevel2.add("getName");

        basicClass.add("String");
        basicClass.add("long");
        basicClass.add("Long");
        basicClass.add("int");
        basicClass.add("Integer");
        basicClass.add("Boolean");
        basicClass.add("boolean");

        Map<String, Object> dataMap = new HashMap<>();
        Class c = event.getClass();

        Method[] methods = c.getMethods();
        System.out.println("-------------------------------------");
        System.out.println(c.getSimpleName());
        for (Method method : methods) {
            String name = method.getName();
            if (trackMethodLevel0.contains(method.getName())) {
                if (basicClass.contains(method.getReturnType().getSimpleName())) {
                    try {
                        dataMap.put(method.getName(), method.invoke(event));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    Object returnedObject = null;
                    try {
                        returnedObject = method.invoke(event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (returnedObject != null) {
                        Method[] secondLevel = returnedObject.getClass().getMethods();
                        Map<String, Object> newLevel = new HashMap<>();
                        for (Method thisMethod : secondLevel) {
                            if (trackMethodLevel1.contains(thisMethod.getName())) {
                                if (basicClass.contains(thisMethod.getReturnType().getSimpleName())) {
                                    try {
                                        newLevel.put(thisMethod.getName(), thisMethod.invoke(returnedObject));
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        System.err.println("*************");
                                        System.err.println(c.getSimpleName());
                                        System.err.println(method.getName() + ": " + method.getReturnType().getSimpleName());
                                        System.err.println(thisMethod.getName() + ": " + thisMethod.getReturnType().getSimpleName());
                                        e.printStackTrace();
                                        System.err.println("*************");
                                    }
                                } else {
                                    Object returnedObject2 = null;
                                    try {
                                        returnedObject2 = thisMethod.invoke(event);
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    if (returnedObject2 != null) {
                                        Map<String, Object> newLevel2 = new HashMap<>();
                                        Method[] thirdLevel = returnedObject2.getClass().getMethods();
                                        for (Method thisLastMethod : thirdLevel) {
                                            if (trackMethodLevel2.contains(thisLastMethod.getName()))
                                                try {
                                                    newLevel2.put(thisLastMethod.getName(), thisLastMethod.invoke(returnedObject2));
                                                } catch (IllegalAccessException | InvocationTargetException e) {
                                                    e.printStackTrace();
                                                }
                                        }
                                        newLevel.put(thisMethod.getName(), newLevel2);
                                    } else {
                                        newLevel.put(thisMethod.getName(), null);
                                    }
                                }
                            }
                        }
                        dataMap.put(method.getName(), newLevel);
                    } else {
                        dataMap.put(method.getName(), null);
                    }
                }

            }
            //if(isSetter(method)) System.out.println("setter: " + method);
        }

        System.out.println(dataMap);
    }

    /**
     * Is getter boolean.
     *
     * @param method the method
     * @return the boolean
     */
    public static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterTypes().length == 0 && !void.class.equals(method.getReturnType());
    }

//    public static boolean isSetter(Method method) {
//        return method.getName().startsWith("set") && method.getParameterTypes().length == 1;
//    }
}
