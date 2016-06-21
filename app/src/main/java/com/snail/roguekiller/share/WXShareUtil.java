package com.snail.roguekiller.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class WXShareUtil {

    public static final int TO_WEIXIN = SendMessageToWX.Req.WXSceneSession;
    public static final int TO_WEIXIN_FRIEND = SendMessageToWX.Req.WXSceneTimeline;
    public static final int TO_WEIXIN_COLLECT = SendMessageToWX.Req.WXSceneFavorite;

    private static final int MAX_THUMB_LENGTH = 32768;

    private static final int MAX_LENGTH = 512;

    private static final int THUMB_SIZE = 150;

    private static final int RESET_THUMB_SIZE = 100;//当图片太大时，缩小图片的尺寸到指定长度

    /**
     * 创建微信API连接
     *
     * @param context
     * @return
     */
    public static IWXAPI createWXAPI(Context context, String appId) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId, true);
        try {
            api.registerApp(appId);
        } catch (Exception e) {
        }
        return api;
    }

    /**
     * 分享文本到微信
     *
     * @param api
     * @param content
     * @param type
     * @return
     */
    public static boolean sendTextContent(IWXAPI api, String title, String content, int type) {

        if (title == null) {
            title = "";
        }

        if (content == null) {
            content = "";
        } else if (content.length() > MAX_LENGTH) {
            content = content.substring(0, MAX_LENGTH);
        }

        WXTextObject obj = new WXTextObject();
        obj.text = content;

        WXMediaMessage msg = new WXMediaMessage();
        // 发送文本类型的消息时，title字段不起作用
        msg.title = title;
        msg.mediaObject = obj;
        msg.description = content;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = type;

        // 调用api接口发送数据到微信
        return api.sendReq(req);
    }

    public static boolean sendLocalBitmap(IWXAPI api, Bitmap bmp, String title, String content, int type) {
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.description = content;
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        if (thumbBmp != null) {
            msg.thumbData = bmpToByteArray(thumbBmp, false);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = type;
        return api.sendReq(req);
    }

    public static boolean sendLocalImage(IWXAPI api, String path, String title, String content, int type) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = title;
        msg.description = content;
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, false);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = type;
        return api.sendReq(req);
    }

    /**
     * 分享网页
     *
     * @param api
     * @param title
     * @param description
     * @param thumb
     * @param weburl
     * @param type
     */
    public static boolean sendActionUrl(IWXAPI api, String title, String description, Bitmap thumb, String weburl, int type) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = weburl;

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        if (thumb != null) {
            msg.thumbData = bmpToByteArray(thumb, false);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("source");
        req.message = msg;
        req.scene = type;

        return api.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(Bitmap bmp, final boolean needRecycle) {
        if (null == bmp) {
            return new byte[0];
        }

        //如果图片的大小超出限制，则将其修改为100px * 100px
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Bitmap resetBmp = null;
        if (bmp.getHeight() > RESET_THUMB_SIZE || bmp.getWidth() > RESET_THUMB_SIZE) {
            int height = bmp.getHeight() > RESET_THUMB_SIZE ? RESET_THUMB_SIZE : bmp.getHeight();
            int width = bmp.getWidth() > RESET_THUMB_SIZE ? RESET_THUMB_SIZE : bmp.getWidth();
            resetBmp = zoomImage(bmp,width,height);
            resetBmp.compress(CompressFormat.PNG, 100, output);
        } else {
            bmp.compress(CompressFormat.PNG, 100, output);
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }
}
