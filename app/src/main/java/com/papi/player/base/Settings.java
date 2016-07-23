/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.papi.player.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Settings {
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;

    private static final String KEY_QINXIDU = "key_qinxidu";
    private static final String KEY_PLAY_ACTION = "key_play_action";


    public Settings(Context context) {
        mAppContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }


    public int getQinXiDuSp() {
        return mSharedPreferences.getInt(KEY_QINXIDU, 0);
    }

    public void setQinXiDuSp(int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_QINXIDU, value);
        editor.commit();
    }


    public int getPlayActionSp() {
        return mSharedPreferences.getInt(KEY_PLAY_ACTION, 0);
    }

    public void setPlayActionSp(int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_PLAY_ACTION, value);
        editor.commit();
    }

}
