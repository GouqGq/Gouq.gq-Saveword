package gq.gouq;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mega
 * Intellij IDEA
 */
public class KeyedTable implements Serializable {

    ArrayList<TableData> dataTable;

    public KeyedTable(){
        dataTable = new ArrayList<TableData>();
    }


    public void setData(Object key, Object data){
        for (TableData tbldata:dataTable)
            if (tbldata.isQualified(key)){
                tbldata.setData(data);
                return;
            }
        dataTable.add(new TableData(key, data));
    }

    public Object getData(Object key){
        for (TableData tbldata:dataTable)
            if (tbldata.isQualified(key)){
                return tbldata.getData();
            }
        return null;
    }

    public void setDataAt(int index, Object data) {
        if(dataTable.get(index) != null){
            dataTable.get(index).setData(data);
        }
    }

    public Object getDataAt(int index) {
        if(dataTable.get(index) != null){
            return dataTable.get(index).getData();
        }
        return null;
    }

    public Object getKeyAt(int index) {
        if(dataTable.get(index) != null){
            return dataTable.get(index).getKey();
        }
        return null;
    }

    public int getSize(){
        return dataTable.size();
    }

    public void removeData(Object key){
        for (int i = 0; i < getSize(); i++) {
            if (dataTable.get(i).isQualified(key)){
                dataTable.remove(i);
            }
        }
    }

    public void removeDataAt(int index){
        dataTable.remove(index);
    }

    public boolean hasData(Object key){
        for (TableData data:dataTable)
            if (data.isQualified(key))
                return true;
        return false;
    }

}
