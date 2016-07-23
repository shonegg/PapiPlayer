package com.papi.player.util;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * author   Shone
 * date     11/06/16.
 * github   https://github.com/shonegg
 */
public class UiHelper {

    public static void intentTo(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }


}
