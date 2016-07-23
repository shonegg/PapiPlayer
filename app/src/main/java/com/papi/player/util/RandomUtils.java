package com.papi.player.util;

import java.util.Random;

/**
 * Author   Shone
 * Date     04/07/16.
 * Github   https://github.com/shonegg
 */
public class RandomUtils {

    public static int delay() {
        int max = 2000;
        int min = 800;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }


}
