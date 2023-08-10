package net.code.free.fast.receivingcodes.util;

import android.content.Context;

public class ResourceManager {
    private static ResourceManager i;

    private ResourceManager() {}

    public static ResourceManager get() {
        if (i == null) {
            synchronized (ResourceManager.class) {
                if (i == null) {
                    i = new ResourceManager();
                }
            }
        }
        return i;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / scale) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }
}
