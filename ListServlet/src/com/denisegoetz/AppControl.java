package com.denisegoetz;

import java.util.HashMap;

public class AppControl {

    private static HashMap<String, Handler> handlerMap = new HashMap();

    public static HashMap<String, Object> handleRequest(String command, HashMap<String, Object> responseMap) {
        Handler aCommandHandler = handlerMap.get(command);
        HashMap<String, Object> returnHash = new HashMap<String, Object>();
        if (aCommandHandler != null) {
            returnHash = aCommandHandler.handleIt(responseMap );
        }
        else{
            System.out.println("Command Not Found");
        }
        return returnHash;
        
    }

    public static void mapCommand(String aCommand, Handler acHandler) {
        handlerMap.put(aCommand, acHandler);
    }
}
