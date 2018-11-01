package com.drying.HeadImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/10/31 17:00
 * <p/>
 * Description:
 */
public class HeadImageView extends View {
    private int color;
    private int pad = 0;
    private Paint         paint;
    private Paint         paint1;
    private int           width;
    private int           height;
    private Bitmap        bitmap;
    private Bitmap        bitmap1;
    private Bitmap        bitmap2;
    private Bitmap        bitmap3;
    private Bitmap        bitmap4;
    private String        leftTopUrl;
    private String        leftButtonUrl;
    private String        rightTopUrl;
    private String        rightButtonUrl;
    private RefreshHandle refreshHandle;

    public HeadImageView(Context context) {
        this(context, null);
    }

    public HeadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeadImageView);
        leftTopUrl = a.getString(R.styleable.HeadImageView_leftTopUrl);
        leftButtonUrl = a.getString(R.styleable.HeadImageView_leftButtonUrl);
        rightTopUrl = a.getString(R.styleable.HeadImageView_rightTopUrl);
        rightButtonUrl = a.getString(R.styleable.HeadImageView_rightButtonUrl);

        a.recycle();

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.WHITE);
        paint1.setStyle(Paint.Style.FILL);

        paint = new Paint();
        paint.setAntiAlias(true);

        refreshHandle = new RefreshHandle();
        if (leftTopUrl != null && !"".equals(leftTopUrl)) {
            drawLeftTopBitmap();
        }
        if (rightTopUrl != null && !"".equals(rightTopUrl)) {
            drawRightTopBitmap();
        }
        if (leftButtonUrl != null && !"".equals(leftButtonUrl)) {
            drawLeftButtonBitmap();
        }
        if (rightButtonUrl != null && !"".equals(rightButtonUrl)) {
            drawRightButtonBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmap);
            drawLiftUp(canvas2);
            drawLiftDown(canvas2);
            drawRightUp(canvas2);
            drawRightDown(canvas2);
        }

        width = getMeasuredWidth();
        height = getMeasuredHeight();

        Rect mSrcRect   = new Rect(0, 0, (width - 1) / 2, (height - 1) / 2);
        Rect mDestRect1 = new Rect(0, 0, (width - 1) / 2, (height - 1) / 2);
        Rect mDestRect2 = new Rect((width - 1) / 2 + 1, 0, width, (height - 1) / 2);
        Rect mDestRect3 = new Rect(0, (height - 1) / 2 + 1, (width - 1) / 2, height);
        Rect mDestRect4 = new Rect((width - 1) / 2 + 1, (height - 1) / 2 + 1, width, height);

        if (bitmap1 != null) {
            canvas.drawBitmap(bitmap1, mSrcRect, mDestRect1, paint1);
        }
        if (bitmap2 != null) {
            canvas.drawBitmap(bitmap2, mSrcRect, mDestRect2, paint1);
        }
        if (bitmap3 != null) {
            canvas.drawBitmap(bitmap3, mSrcRect, mDestRect3, paint1);
        }
        if (bitmap4 != null) {
            canvas.drawBitmap(bitmap4, mSrcRect, mDestRect4, paint1);
        }
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    private void drawLeftTopBitmap() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL               url               = new URL(leftTopUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream       inputStream       = httpURLConnection.getInputStream();
                    Bitmap            bitmap            = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    if (bitmap == null) {
                        return;
                    }
                    //压缩图片
                    bitmap1 = compressBitmap(bitmap);
                    refreshHandle.sendMessage(new Message());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void drawRightTopBitmap() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL               url               = new URL(rightTopUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream       inputStream       = httpURLConnection.getInputStream();
                    Bitmap            bitmap            = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    if (bitmap == null) {
                        return;
                    }
                    //压缩图片
                    bitmap2 = compressBitmap(bitmap);
                    refreshHandle.sendMessage(new Message());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void drawLeftButtonBitmap() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL               url               = new URL(leftButtonUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream       inputStream       = httpURLConnection.getInputStream();
                    Bitmap            bitmap            = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    if (bitmap == null) {
                        return;
                    }
                    //压缩图片
                    bitmap3 = compressBitmap(bitmap);
                    refreshHandle.sendMessage(new Message());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void drawRightButtonBitmap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL               url               = new URL(rightButtonUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream       inputStream       = httpURLConnection.getInputStream();
                    Bitmap            bitmap            = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    if (bitmap == null) {
                        return;
                    }
                    //压缩图片
                    bitmap4 = compressBitmap(bitmap);
                    refreshHandle.sendMessage(new Message());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() / 2);
        path.lineTo(0, 0);
        path.lineTo(getWidth() / 2, 0);
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), -90, -90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() / 2);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth() / 2, getHeight());
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), 90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), getHeight() / 2);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() / 2, 0);
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), -90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), getHeight() / 2);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth() / 2, getHeight());
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), 90, -90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    /**
     * 图片大小压缩
     *
     * @param bitmap
     *
     * @return
     */
    private synchronized Bitmap compressBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap b;
        Matrix matrix      = new Matrix();
        float  scaleWidth  = ((float) (width - 1) / 2) / bitmap.getWidth();
        float  scaleHeight = ((float) (height - 1) / 2) / bitmap.getHeight();
        matrix.postScale(scaleWidth, scaleHeight);
        b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return b;
    }

    class RefreshHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    }

    public void setLeftTopUrl(String leftTopUrl) {
        this.leftTopUrl = leftTopUrl;
    }

    public void setLeftButtonUrl(String leftButtonUrl) {
        this.leftButtonUrl = leftButtonUrl;
    }

    public void setRightTopUrl(String rightTopUrl) {
        this.rightTopUrl = rightTopUrl;
    }

    public void setRightButtonUrl(String rightButtonUrl) {
        this.rightButtonUrl = rightButtonUrl;
    }
}
