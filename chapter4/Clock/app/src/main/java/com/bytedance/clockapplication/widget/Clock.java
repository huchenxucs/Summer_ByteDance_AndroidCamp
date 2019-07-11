package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private int mWidth, mCenterX, mCenterY, mRadius;

    /**
     * properties
     */
    private int centerInnerColor;
    private int centerOuterColor;

    private int secondsNeedleColor;
    private int hoursNeedleColor;
    private int minutesNeedleColor;

    private int degreesColor;

    private int hoursValuesColor;

    private int numbersColor;
    
    private Paint mpaint;

    private boolean mShowAnalog = true;

    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        size = Math.min(widthWithoutPadding, heightWithoutPadding);
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.centerInnerColor = Color.LTGRAY;
        this.centerOuterColor = DEFAULT_PRIMARY_COLOR;

        this.secondsNeedleColor = DEFAULT_SECONDARY_COLOR;
        this.hoursNeedleColor = DEFAULT_PRIMARY_COLOR;
        this.minutesNeedleColor = DEFAULT_PRIMARY_COLOR;

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        this.hoursValuesColor = DEFAULT_PRIMARY_COLOR;
        this.mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        numbersColor = Color.WHITE;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mWidth = Math.min(getWidth(), getHeight());

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;

        if (mShowAnalog) {
            drawDegrees(canvas);
            drawHoursValues(canvas);
            drawNeedles(canvas);
            drawCenter(canvas);
        } else {
            drawNumbers(canvas);
        }
        postInvalidateDelayed(1000);
    }

    private void drawDegrees(Canvas canvas) {

        //Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
        mpaint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        mpaint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);
        int rEnd = mCenterX - (int) (mWidth * 0.05f);

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0) {
                mpaint.setAlpha(CUSTOM_ALPHA);
                mpaint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
            }
            else {
                mpaint.setAlpha(FULL_ALPHA);
                mpaint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH * 1.5f);
            }

            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, mpaint);

        }
    }

    /**
     * @param canvas
     */
    private void drawNumbers(Canvas canvas) {

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(mWidth * 0.2f);
        textPaint.setColor(numbersColor);
        textPaint.setColor(numbersColor);
        textPaint.setAntiAlias(true);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int amPm = calendar.get(Calendar.AM_PM);

        String time = String.format("%s:%s:%s%s",
                String.format(Locale.getDefault(), "%02d", hour),
                String.format(Locale.getDefault(), "%02d", minute),
                String.format(Locale.getDefault(), "%02d", second),
                amPm == AM ? "AM" : "PM");

        SpannableStringBuilder spannableString = new SpannableStringBuilder(time);
        spannableString.setSpan(new RelativeSizeSpan(0.3f), spannableString.toString().length() - 2, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // se superscript percent

        StaticLayout layout = new StaticLayout(spannableString, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, true);
        canvas.translate(mCenterX - layout.getWidth() / 2f, mCenterY - layout.getHeight() / 2f);
        layout.draw(canvas);
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        // Default Color:
        // - hoursValuesColor
        //TextPaint textPaint = new TextPaint();
        mpaint.setTextSize(mWidth * 0.05f);
        mpaint.setColor(hoursValuesColor);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setTextAlign(Paint.Align.CENTER);
        mpaint.setAntiAlias(true);
        float rEnd = mCenterX - (mWidth * 0.09f);
        for (int i=1;i<=12;i++)
        {
            String text = i + "";
            int  hourAngle = i * FULL_ANGLE / 12;
            hourAngle = FULL_ANGLE - hourAngle + 90;
            hourAngle = hourAngle >= FULL_ANGLE ? hourAngle - FULL_ANGLE : hourAngle;
            float stopX = (float) (mCenterX + rEnd * Math.cos(Math.toRadians(hourAngle)));
            float stopY = (float) (mCenterX - rEnd * Math.sin(Math.toRadians(hourAngle)));
            stopY += mpaint.getTextSize()/2.2;
            canvas.drawText(text,stopX,stopY,mpaint);
        }

    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        // Default Color:
        // - secondsNeedleColor
        // - hoursNeedleColor
        // - minutesNeedleColor
        //Paint needlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mpaint.setStrokeCap(Paint.Cap.ROUND);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        float hourAngle = (hour + (float) minute / 60) * FULL_ANGLE / 12;
        float minuteAngle = (minute + (float) second / 60) * FULL_ANGLE / 60;
        int secondAngle =  second * FULL_ANGLE / 60;

        hourAngle = FULL_ANGLE - hourAngle + 90;
        hourAngle = hourAngle >= FULL_ANGLE ? hourAngle - FULL_ANGLE : hourAngle;

        minuteAngle = FULL_ANGLE - minuteAngle + 90;
        minuteAngle = minuteAngle >= FULL_ANGLE ? minuteAngle - FULL_ANGLE : minuteAngle;

        secondAngle = FULL_ANGLE - secondAngle + 90;
        secondAngle = secondAngle >= FULL_ANGLE ? secondAngle - FULL_ANGLE : secondAngle;

        int rEnd  = mCenterX- (int) (mWidth * 0.25f);
        int startX = mCenterX;
        int startY = mCenterX;
        int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(hourAngle)));
        int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(hourAngle)));
        mpaint.setColor(hoursNeedleColor);
        mpaint.setStrokeWidth(14f);
        canvas.drawLine(startX, startY, stopX, stopY, mpaint);

        rEnd  = mCenterX- (int) (mWidth * 0.11f);
        stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(minuteAngle)));
        stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(minuteAngle)));
        mpaint.setStrokeWidth(8f);
        mpaint.setColor(minutesNeedleColor);
        canvas.drawLine(startX, startY, stopX, stopY, mpaint);

        rEnd  = mCenterX- (int) (mWidth * 0.06f);
        stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(secondAngle)));
        stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(secondAngle)));
        mpaint.setStrokeWidth(5f);
        mpaint.setColor(Color.RED);
        canvas.drawLine(startX, startY, stopX, stopY, mpaint);

    }

    /**
     * Draw Center Dot
     *
     * @param canvas
     */
    private void drawCenter(Canvas canvas) {
        // Default Color:
        // - centerInnerColor
        // - centerOuterColor
        //Paint centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setColor(centerOuterColor);
        canvas.drawCircle(mCenterX, mCenterY,mWidth*0.025f,mpaint);
        mpaint.setColor(centerInnerColor);
        canvas.drawCircle(mCenterX, mCenterY,mWidth*0.0125f,mpaint);
    }

    public void setShowAnalog(boolean showAnalog) {
        mShowAnalog = showAnalog;
        invalidate();
    }

    public boolean isShowAnalog() {
        return mShowAnalog;
    }

}