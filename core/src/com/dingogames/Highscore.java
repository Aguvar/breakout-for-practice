package com.dingogames;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class Highscore {
    Array<String> workArray;
    OrderedMap<Integer,String> scoreMap;

    public Highscore(Array<String> workArray) {
        this.workArray = workArray;
    }

    public Highscore() {
    }

    public void add(String value) {
        this.workArray.add(value);
        workArray.sort();
    }

    public int quantity(){
        return workArray.size;
    }
}
