
package br.ucs.mobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import br.ucs.mobile.camera.GraphicOverlay;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Instância gráfica para renderizar posição, tamanho e ID do código de barras em uma
 * visualização de sobreposição gráfica associada
 */
public class BarcodeGraphic extends GraphicOverlay.Graphic {

    private int mId;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.MAGENTA,
            Color.RED,
            Color.CYAN,
    };

    private static int mCurrentColorIndex = 0;

    private Paint mRectPaint;
    private Paint mTextPaint;
    private volatile Barcode mBarcode;

    BarcodeGraphic(GraphicOverlay overlay) {
        super(overlay);
        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];
        mRectPaint = new Paint();
        mRectPaint.setColor(selectedColor);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(4.0f);
        mTextPaint = new Paint();
        mTextPaint.setColor(selectedColor);
        mTextPaint.setTextSize(36.0f);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Barcode getBarcode() {
        return mBarcode;
    }

    /**
     * Atualiza a instância do código de barras da detecção do quadro mais recente.
     * Inativa as partes relevantes da sobreposição para acionar um redesenho.
     */
    void updateItem(Barcode barcode) {
        mBarcode = barcode;
        postInvalidate();
    }

    /**
     * Desenha as anotações de código de barras para posição, tamanho e valor na tela fornecida.
     */
    @Override
    public void draw(Canvas canvas) {
        Barcode barcode = mBarcode;
        if (barcode == null) {
            return;
        }
        // Desenha a caixa em torno do código de barras.
        RectF rect = new RectF(barcode.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);
        // Desenha um label na parte inferior do código de barras para indicar o valor do código de barras que foi detectado.
        canvas.drawText(barcode.rawValue, rect.left, rect.bottom, mTextPaint);
    }
}
