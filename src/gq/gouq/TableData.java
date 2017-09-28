package gq.gouq;

import java.io.Serializable;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class TableData implements Serializable {

    Object key, data;

    public TableData(Object key, Object data){
        this.key = key;
        this.data = data;
    }

    public boolean isQualified(Object key){
        return key == this.key || key.equals(this.key);
    }

    public Object getKey() {
        return key;
    }

    public Object getData() {
        return data;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
