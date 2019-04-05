package com.denisegoetz;

import java.util.HashMap;

public interface Handler {
//FYI this acts like a templet. 
// and then... public HashMap<String, Object> handleIt(HashMap<String, Object> responseMap )....will surround all you handled classes.
// See Login.java, AppControl and Register for example.  Notice also, I am passing the HashMap around with handler.

    public HashMap<String, Object> handleIt( HashMap<String, Object> responseMap );
    
}