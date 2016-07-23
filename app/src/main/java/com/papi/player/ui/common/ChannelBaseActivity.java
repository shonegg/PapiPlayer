package com.papi.player.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Author   Shone
 * Date     02/07/16.
 * Github   https://github.com/shonegg
 */
public abstract class ChannelBaseActivity extends AbsActivity{

    public final String getChannelCode() {
        return this.getExtras().getString("CHANNEL_CODE");
    }

    public final String getChannelId() {
        return this.getExtras().getString("CHANNEL_ID");
    }

    public String getChannelName() {
        return this.getExtras().getString("CHANNEL_NAME");
    }

    private Bundle getExtras() {
        return this.getIntent().getExtras();
    }

    protected static void start(Context ctx, Class clazz, String code, String id, String name) {
        Intent intent = new Intent(ctx, clazz);
        intent.putExtra("CHANNEL_CODE", code);
        intent.putExtra("CHANNEL_ID", id);
        intent.putExtra("CHANNEL_NAME", name);
        ctx.startActivity(intent);
    }

}
