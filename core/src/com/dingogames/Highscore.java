package com.dingogames;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Dingo on 09-Jan-17.
 */
public class Highscore {
    Array<String> workArray;

    public Highscore(Array<String> workArray) {
        this.workArray = workArray;
    }

    public void add(String value) {
        this.workArray.add(value);
    }
}
