package com.vctapps.starwarscharacters.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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
import com.vctapps.starwarscharacters.util.CheckUrl;

import java.io.IOException;

public class BarcodeActivity extends AppCompatActivity {

    private static final int ID_PERMISSIONS = 1;
    public static final String RESULT_REQUEST_QR_CODE = "qr_code";
    private static final String TAG = "qrcodeDebug";
    private BarcodeDetector detector;
    private CameraSource camera;
    private SurfaceView surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        //Verifica se existe permissão para utilizar camera
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

    /**
     * Inicia a camera
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    /**
     * Para a camera quando sair da tela
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)camera.stop();
    }

    /**
     * Para a camera quando destroi a activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(camera != null) camera.stop();
    }

    /**
     * Inicia a camera
     */
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

                //Caso o QRCode for válido, retorna a string dele para quem solicitou
                //Caso erro exibe um Toast alertando o usuário que o QRCode é invalido
                if(barcodes != null && barcodes.size() > 0 && !barcodes.valueAt(0).equals("")){
                    if(CheckUrl.check(barcodes.valueAt(0).displayValue)) { //verifica se a url é valida
                        Log.d(TAG, "QRCode capturado corretamente");
                        Intent result = new Intent();
                        result.putExtra(RESULT_REQUEST_QR_CODE, barcodes.valueAt(0).displayValue);
                        setResult(Activity.RESULT_OK, result);
                        finish();
                    }else {
                        //TODO fazer um aviso amigável avisando que a URL não é valida
                    }
                }
            }
        });

        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                setSurfacesHolderOnCamera(camera, surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                setSurfacesHolderOnCamera(camera, surfaceHolder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                camera.stop();
            }
        });
    }

    /**
     * Configura SurfaceHolder na camera
     * @param camera CameraSource
     * @param holder SurfaceHolder
     */
    private void setSurfacesHolderOnCamera(CameraSource camera, SurfaceHolder holder){
        int check = ActivityCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA);
        if(check == PackageManager.PERMISSION_GRANTED){
            try {
                camera.start(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            requestPermissionCamera();
        }
    }
}
