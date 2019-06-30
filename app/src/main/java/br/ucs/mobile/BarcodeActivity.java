
package br.ucs.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeActivity extends Activity implements View.OnClickListener {

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;

    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        statusMessage = (TextView) findViewById(R.id.status_message);
        barcodeValue = (TextView) findViewById(R.id.barcode_value);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        findViewById(R.id.read_barcode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFoco, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UsarFlash, useFlash.isChecked());
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.ObjectBarcode);
                    statusMessage.setText(R.string.barcode_sucesso);
                    barcodeValue.setText(barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_falha);
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_erro), CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
