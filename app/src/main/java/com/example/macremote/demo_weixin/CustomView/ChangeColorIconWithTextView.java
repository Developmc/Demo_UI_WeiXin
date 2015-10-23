package com.example.macremote.demo_weixin.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.macremote.demo_weixin.R;

/**
 * Created by macremote on 2015/10/21.
 */
public class ChangeColorIconWithTextView extends View {
    private Bitmap mBitmap ;
    private Canvas mCanvas;
    private Paint mPaint ;
    //颜色（选中时的文本和字体的颜色）
    private int mColor ;
    //未选中时的文本和字体颜色
    private int mColorUnchecked ;
    //透明度
    private float mAlpha = 0f;
    //图标
    private Bitmap mIconBitmap;
    //限制绘制icon的范围
    private Rect mIconRect ;
    //icon底部的文字
    private String mText="微信" ;
    //默认值为10
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            10,getResources().getDisplayMetrics()) ;
    private Paint mTextPaint ;
    private Rect mTextBound = new Rect();
    private Context context ;

    public ChangeColorIconWithTextView(Context context) {
        super(context);
        this.context=context;
    }

    //初始化自定义的属性
    public ChangeColorIconWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        //获取设置的图标
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconView) ;
        int n=a.getIndexCount() ;
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i) ;
            switch (attr){
                case R.styleable.ChangeColorIconView_icon_custom:
                    BitmapDrawable drawable = (BitmapDrawable)a.getDrawable(attr);
                    //得到图标对象
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconView_color_custom:
                    //默认是蓝色，但好像不起效果
                    mColor=a.getColor(attr,context.getResources().getColor(R.color.blue)) ;
                    break;
                case R.styleable.ChangeColorIconView_color_unchecked_custom:
                    mColorUnchecked=a.getColor(attr,context.getResources().getColor(R.color.blue)) ;
                    break;
                case R.styleable.ChangeColorIconView_text_custom:
                    mText=a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconView_text_size_custom:
                    //默认字体大小是10
                    mTextSize=(int)a.getDimension(attr,TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP,10,getResources().getDisplayMetrics())) ;
                    break;
            }
        }
        //Give back a previously retrieved array, for later re-use.
        a.recycle();
        mTextPaint = new Paint() ;
        mTextPaint.setTextSize(mTextSize);
        //画笔默认颜色
        mTextPaint.setColor(context.getResources().getColor(R.color.black));
        //得到text的绘制范围(text的长度)
        mTextPaint.getTextBounds(mText,0,mText.length(),mTextBound);
    }

    public ChangeColorIconWithTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    /**计算图标的绘制范围
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到绘制icon的边长(可分情况判断)，icon为正方形图标
        int bitmapWidth = Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
                getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-mTextBound.height());
        int left = getMeasuredWidth()/2-bitmapWidth/2 ;
        int top = (getMeasuredHeight()-mTextBound.height())/2-bitmapWidth/2;
        //设置icon的绘制范围
        mIconRect = new Rect(left,top,left+bitmapWidth,top+bitmapWidth) ;
    }

    /**绘制图标和文字
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算alpha
        int alpha = (int)Math.ceil(255*mAlpha) ;
        //绘制原图
        canvas.drawBitmap(mIconBitmap,null,mIconRect,null);
        setupTargetBitmap(alpha);
        drawSourceText(canvas,alpha);
        drawTargetText(canvas,alpha);
        canvas.drawBitmap(mBitmap,0,0,null);
    }

    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888) ;
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        //绘制纯色块
        mCanvas.drawRect(mIconRect, mPaint);
        //设置mode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    /**绘制源文本
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas,int alpha){
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColorUnchecked);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    /**绘制目标文本
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas,int alpha){
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2,
                mIconRect.bottom + mTextBound.height(), mTextPaint);
    }
    public void setIconAlpha(float alpha){
        this.mAlpha = alpha ;
        invalidateView() ;
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper())
        {
            invalidate();
        } else
        {
            postInvalidate();
        }
    }
}
