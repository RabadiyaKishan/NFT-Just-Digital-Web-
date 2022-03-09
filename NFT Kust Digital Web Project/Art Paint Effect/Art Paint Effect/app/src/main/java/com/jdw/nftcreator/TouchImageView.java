package com.jdw.nftcreator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.widget.ImageView;


@SuppressLint({"AppCompatCustomView"})
public class TouchImageView extends ImageView {
    static final int f14148b = 0;
    static final int f14149c = 1;
    static final int f14150d = 2;
    static final int f14151m = 3;
    Matrix f14152a;
    int f14153e = f14148b;
    PointF f14154f = new PointF();
    PointF f14155g = new PointF();
    float f14156h = 1.0f;
    float f14157i = 3.0f;
    float[] f14158j;
    int f14159k;
    int f14160l;
    float f14161n = 1.0f;
    protected float f14162o;
    protected float f14163p;
    int f14164q;
    int f14165r;
    ScaleGestureDetector f14166s;
    Context f14167t;

    class C29681 implements OnTouchListener {
        final TouchImageView f14146a;

        C29681(TouchImageView touchImageView) {
            this.f14146a = touchImageView;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            this.f14146a.f14166s.onTouchEvent(motionEvent);
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            switch (motionEvent.getAction()) {
                case TouchImageView.f14148b :
                    this.f14146a.f14154f.set(pointF);
                    this.f14146a.f14155g.set(this.f14146a.f14154f);
                    this.f14146a.f14153e = TouchImageView.f14149c;
                    break;
                case TouchImageView.f14149c :
                    this.f14146a.f14153e = TouchImageView.f14148b;
                    int abs2 = (int) Math.abs(pointF.y - this.f14146a.f14155g.y);
                    if (((int) Math.abs(pointF.x - this.f14146a.f14155g.x)) < TouchImageView.f14151m && abs2 < TouchImageView.f14151m) {
                        this.f14146a.performClick();
                        break;
                    }
                case TouchImageView.f14150d :
                    if (this.f14146a.f14153e == TouchImageView.f14149c) {
                        this.f14146a.f14152a.postTranslate(this.f14146a.m17280b(pointF.x - this.f14146a.f14154f.x, (float) this.f14146a.f14159k, this.f14146a.f14162o * this.f14146a.f14161n), this.f14146a.m17280b(pointF.y - this.f14146a.f14154f.y, (float) this.f14146a.f14160l, this.f14146a.f14163p * this.f14146a.f14161n));
                        this.f14146a.m17279a();
                        this.f14146a.f14154f.set(pointF.x, pointF.y);
                        break;
                    }
                    break;
                case R.styleable.FancyButtonsAttrs_fb_textColor :
                    this.f14146a.f14153e = TouchImageView.f14148b;
                    break;
            }
            this.f14146a.setImageMatrix(this.f14146a.f14152a);
            this.f14146a.invalidate();
            return true;
        }
    }

    private class C2969a extends SimpleOnScaleGestureListener {
        final TouchImageView f14147a;

        private C2969a(TouchImageView touchImageView) {
            this.f14147a = touchImageView;
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            float f = this.f14147a.f14161n;
            TouchImageView touchImageView = this.f14147a;
            touchImageView.f14161n *= scaleFactor;
            if (this.f14147a.f14161n > this.f14147a.f14157i) {
                this.f14147a.f14161n = this.f14147a.f14157i;
                scaleFactor = this.f14147a.f14157i / f;
            } else if (this.f14147a.f14161n < this.f14147a.f14156h) {
                this.f14147a.f14161n = this.f14147a.f14156h;
                scaleFactor = this.f14147a.f14156h / f;
            }
            if (this.f14147a.f14162o * this.f14147a.f14161n <= ((float) this.f14147a.f14159k) || this.f14147a.f14163p * this.f14147a.f14161n <= ((float) this.f14147a.f14160l)) {
                this.f14147a.f14152a.postScale(scaleFactor, scaleFactor, (float) (this.f14147a.f14159k / TouchImageView.f14150d), (float) (this.f14147a.f14160l / TouchImageView.f14150d));
            } else {
                this.f14147a.f14152a.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            }
            this.f14147a.m17279a();
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            this.f14147a.f14153e = TouchImageView.f14150d;
            return true;
        }
    }

    public TouchImageView(Context context) {
        super(context);
        m17277a(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m17277a(context);
    }

    private void m17277a(Context context) {
        super.setClickable(true);
        this.f14167t = context;
        this.f14166s = new ScaleGestureDetector(context, new C2969a(this));
        this.f14152a = new Matrix();
        this.f14158j = new float[9];
        setImageMatrix(this.f14152a);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(new C29681(this));
    }

    float m17278a(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = 0.0f;
        } else {
            f5 = f2 - f3;
            f4 = 0.0f;
        }
        if (f < f5) {
            return (-f) + f5;
        }
        return f > f4 ? (-f) + f4 : 0.0f;
    }

    void m17279a() {
        this.f14152a.getValues(this.f14158j);
        float f = this.f14158j[f14150d];
        float f2 = this.f14158j[5];
        f = m17278a(f, (float) this.f14159k, this.f14162o * this.f14161n);
        f2 = m17278a(f2, (float) this.f14160l, this.f14163p * this.f14161n);
        if (f != 0.0f || f2 != 0.0f) {
            this.f14152a.postTranslate(f, f2);
        }
    }

    float m17280b(float f, float f2, float f3) {
        return f3 <= f2 ? 0.0f : f;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.f14159k = MeasureSpec.getSize(i);
        this.f14160l = MeasureSpec.getSize(i2);
        if ((this.f14165r != this.f14159k || this.f14165r != this.f14160l) && this.f14159k != 0 && this.f14160l != 0) {
            this.f14165r = this.f14160l;
            this.f14164q = this.f14159k;
            if (this.f14161n == 1.0f) {
                Drawable drawable = getDrawable();
                if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0) {
                    int intrinsicWidth = drawable.getIntrinsicWidth();
                    int intrinsicHeight = drawable.getIntrinsicHeight();
                    Log.d("bmSize", "bmWidth: " + intrinsicWidth + " bmHeight : " + intrinsicHeight);
                    float min = Math.min(((float) this.f14159k) / ((float) intrinsicWidth), ((float) this.f14160l) / ((float) intrinsicHeight));
                    this.f14152a.setScale(min, min);
                    float f = (((float) this.f14160l) - (((float) intrinsicHeight) * min)) / 2.0f;
                    float f2 = (((float) this.f14159k) - (((float) intrinsicWidth) * min)) / 2.0f;
                    this.f14152a.postTranslate(f2, f);
                    this.f14162o = ((float) this.f14159k) - (f2 * 2.0f);
                    this.f14163p = ((float) this.f14160l) - (f * 2.0f);
                    setImageMatrix(this.f14152a);
                } else {
                    return;
                }
            }
            m17279a();
        }
    }

    public void setMaxZoom(float f) {
        this.f14157i = f;
    }
}
