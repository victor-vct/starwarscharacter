package com.vctapps.starwarscharacters.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.service.CheckUrl;

import java.io.IOException;

public class BarcodeActivity extends AppCompatActivity {

    private static final int ID_PERMISSIONS = 1;
    private static final String TAG = "qrcodeDebug";
    private BarcodeDetector detector;
    private CameraSource camera;
    private SurfaceView surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(check == PackageManager.PERMISSION_GRANTED){
            startCamera();
        }else{
            requestPermissionCamera();
        }
    }

    /**
     * Faz a requisição de permissão da camera
     */
    private void requestPermissionCamera(){
        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, permissions, ID_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != ID_PERMISSIONS){
            Log.d(TAG, "Permissão não esperada, code: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if(grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Temos permissão da camera o/");
            startCamera();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)camera.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(camera != null) camera.stop();
    }

    private void startCamera(){
        surface = (SurfaceView) findViewById(R.id.sv_qr_code_camera);

        detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        camera = new CameraSource.Builder(this, detector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();

        detector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release() {
                this.release();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if(barcodes != null && barcodes.size() > 0 &&
                        CheckUrl.check(barcodes.valueAt(0).displayValue)){
                    Log.d(TAG, "QRCode capturado corretamente");
                    //TODO fazer o serviço de download de informações iniciar aqui
                }else{
                    Log.e(TAG, "Não foi possível identificar esse download");
                    //TODO Exibir AlertDialog com mensagem de erro
                }
            }
        });

        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                int check = ActivityCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA);
                if(check == PackageManager.PERMISSION_GRANTED){
                    try {
                        camera.start(surface.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    requestPermissionCamera();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }
}
