package gq.gouq;

import java.io.Serializable;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class Password implements Serializable {

    private String usage, password;

    public Password(String usage, String password){
        this.usage = usage;
        this.password = password;
    }

    public String getUsage(){
        return usage;
    }

    public String getPassword() {
        return password;
    }
}
