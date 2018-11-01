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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/10/31 17:00
 * <p/>
 * Description:群头像控件
 * 这是一个群头像控件，目前有两种形式，第一种是圆形展示，另一种是矩形展示
 * 可以设置背景色，切背景色等于线的颜色（自定义背景色）
 * 可以设置线的宽度
 * 注意：控件的宽和高要相等 ，如不想等则按照短的计算
 * 一定要有网络权限
 */
public class GroupHeadImageView extends View {
    //控件背景色
    private int color;
    //0:圆形；1:矩形
    private int type;
    //控件边距
    private int pad         = 0;
    //当整体图形为圆形，切一排有三列或两排三类。。水平居中展示
    private int padTop      = 0;
    //矩形圆角长度
    private int roundWidth  = 20;
    //矩形圆角长度
    private int roundHeight = 20;
    //画图像画笔
    private Paint paint;
    //画圆外围画笔
    private Paint paint1;
    //控件宽度
    private int   width;
    //控件高度
    private int   height;
    //每行数量
    private int   xSize;
    //每列数量
    private int   ySize;
    //展示图片数量
    private int   size;
    //每张图片的宽度
    private int   itemWidth;
    //没张图片的高度
    private int   itemHeight;
    //每张图片中间的线宽
    private int line = 1;
    //圆圈外围图像
    private Bitmap        bitmap;
    //图片列表
    private List<Bitmap>  bitmapList;
    //图片地址列表
    private List<String>  headList;
    //刷新handle
    private RefreshHandle refreshHandle;

    public GroupHeadImageView(Context context) {
        this(context, null);
    }

    public GroupHeadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupHeadImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GroupHeadImageView);
        color = a.getColor(R.styleable.GroupHeadImageView_BackgroundColor, Color.WHITE);
        line = a.getInt(R.styleable.GroupHeadImageView_line, 1);
        type = a.getInteger(R.styleable.GroupHeadImageView_type, 0);
        a.recycle();
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(color);
        setBackgroundColor(color);

        paint = new Paint();
        paint.setAntiAlias(true);

        refreshHandle = new RefreshHandle();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmapList == null && headList != null && headList.size() > 0) {
            bitmapList = new ArrayList<>();
            upData();
            refreshBitmap();
            return;
        }

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmap);
            if (type == 0) {
                paint1.setStyle(Paint.Style.FILL);
                drawLiftUp(canvas2);
                drawLiftDown(canvas2);
                drawRightUp(canvas2);
                drawRightDown(canvas2);
            } else {
                paint1.setStyle(Paint.Style.STROKE);
                paint1.setStrokeWidth(roundWidth);
                RectF oval3 = new RectF(0, 0, width, height);
                canvas2.drawRoundRect(oval3, roundWidth, roundHeight, paint1);
            }
        }

        if (bitmapList.size() == 0) {
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return;
        }

        Rect mSrcRect = new Rect(0, 0, itemWidth, itemHeight);

        if (xSize == ySize) {
            padTop = 0;
        } else if (xSize > ySize) {
            padTop = (xSize - ySize) * itemWidth / 2;
        }

        for (int i = 1; i <= ySize; i++) {
            for (int j = 1; j <= xSize; j++) {
                if (i * j <= size) {
                    int left   = (j - 1) * itemWidth + (j - 1) * line;
                    int top    = (i - 1) * itemHeight + padTop + (i - 1) * line;
                    int right  = j * itemWidth + (j - 1) * line;
                    int bottom = i * itemHeight + padTop + (i - 1) * line;
                    int index  = (i - 1) * xSize + j;

                    Rect mDestRect1 = new Rect(left, top, right, bottom);
                    if (index <= bitmapList.size()) {
                        canvas.drawBitmap(bitmapList.get(index - 1), mSrcRect, mDestRect1, paint1);
                    }
                }
            }
        }

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    //绘制圆形左上角路径
    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() / 2);
        path.lineTo(0, 0);
        path.lineTo(getWidth() / 2, 0);
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), -90, -90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    //绘制圆形左下角路径
    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() / 2);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth() / 2, getHeight());
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), 90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    //绘制圆形右上角路径
    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), getHeight() / 2);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() / 2, 0);
        path.arcTo(new RectF(pad, pad, getWidth() - pad, getHeight() - pad), -90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    //绘制圆形右下角路径
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
        if (bitmap == null || itemHeight <= 0 || itemWidth <= 0) {
            return null;
        }
        Bitmap b;
        Matrix matrix      = new Matrix();
        float  scaleWidth  = ((float) itemWidth) / bitmap.getWidth();
        float  scaleHeight = ((float) itemHeight) / bitmap.getHeight();
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

    public void setHeadList(List<String> headList) {
        this.headList = headList;
        if (headList != null && headList.size() > 0) {
            bitmapList = null;
            invalidate();
        }
    }

    private void upData() {

        int x = (int) Math.sqrt(headList.size());
        size = headList.size();
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        if (x * x == size) {
            xSize = x;
        } else {
            xSize = x + 1;
        }
        if (size <= x * xSize) {
            ySize = x;
        } else {
            ySize = x + 1;
        }
        itemWidth = (width - xSize + 1) / xSize;
        itemHeight = itemWidth;
    }

    private synchronized Bitmap urlToBitMap(String imgUrl) {
        try {
            boolean           useHttps          = imgUrl.startsWith("https");
            URL               url               = new URL(imgUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (useHttps) {

                HttpsURLConnection https               = (HttpsURLConnection) httpURLConnection;
                SSLSocketFactory   oldSocketFactory    = SSLUtils.trustAllHosts(https);
                HostnameVerifier   oldHostnameVerifier = https.getHostnameVerifier();
                https.setHostnameVerifier(SSLUtils.DO_NOT_VERIFY);
            }

            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap      bitmap      = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            if (bitmap == null) {
                return null;
            }
            //返回压缩图片
            return compressBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void refreshBitmap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String url : headList) {
                    Bitmap bitmap = urlToBitMap(url);
                    if (bitmap != null) {
                        bitmapList.add(bitmap);
                        refreshHandle.sendMessage(new Message());
                    }
                }
            }
        }).start();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
