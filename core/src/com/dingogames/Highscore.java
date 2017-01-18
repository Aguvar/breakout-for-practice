package com.dingogames;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class Highscore {
    OrderedMap<String,String> scoreMap;
    Array<Integer> keys;

    public Highscore(boolean diff) {
        scoreMap = new OrderedMap<String, String>(20);
        scoreMap.put(Integer.toString(1300), "Dingo");
        keys = new Array<Integer>(true,20);
        keys.add(1300);
    }

    public Highscore() {
    }

    public void add(int score ,String name) {
        scoreMap.put(Integer.toString(score),name);
        keys.add(score);
        keys.sort();
    }

    public Array<Integer> getKeys(){
        return keys;
    }

    public String getScore(Integer key){
        return scoreMap.get(Integer.toString(key));
    }

    public int quantity(){
        return scoreMap.size;
    }
}
