package com.dingogames;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Dingo on 28-Jan-17.
 */
public class LevelLayout {
    boolean[][] layout;
    int levelY;
    int levelX;

    public LevelLayout() {
        levelX = 6;
        levelY = 6;
        layout = new boolean[6][6];
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[0].length; x++) {
                layout[y][x] = true;
            }
        }
    }
}
