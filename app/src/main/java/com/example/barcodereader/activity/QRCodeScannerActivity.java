package com.example.barcodereader.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QRCodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    public static final String EXTRA_ADDRESS = "MESSAGE";
    int sourceOfScanFlag = 0;
    String scanSourceText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView );
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(CheckPermission()){

//                Toast.makeText( QRCodeScanner.this,"Permision granted",Toast.LENGTH_LONG ).show();
            }else {

                RequestPermission();
            }

        }

    }
    @Override
    public void handleResult(Result result) {
        final String scanResult=result.getText();


        Intent i = new Intent();
        Bundle extras = new Bundle();
        extras.putString(EXTRA_ADDRESS, scanResult.toString());
        i.putExtras(extras);
        setResult(Activity.RESULT_OK,i);
        finish();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private boolean CheckPermission(){
        boolean res;
        res = (ContextCompat.checkSelfPermission(QRCodeScannerActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
        return  res;
    }
    private void RequestPermission(){

        ActivityCompat.requestPermissions( this,new String[]{CAMERA},REQUEST_CAMERA );
    }
    public void onRequestPermissionsResult(int requestCode, String permission[],int grantResults[]){

        switch (requestCode){

            case REQUEST_CAMERA:
                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){

                        Toast.makeText(QRCodeScannerActivity.this, "Permission granted", Toast.LENGTH_LONG).show();
                    }else {

                        Toast.makeText(QRCodeScannerActivity.this, "Permission denied", Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                            if(shouldShowRequestPermissionRationale( CAMERA )){

                                DisplayAlertMessage( "You need to allow permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                                                    requestPermissions( new String[]{CAMERA},REQUEST_CAMERA );
                                                }
                                            }
                                        } );
                                return;
                            }
                        }
                    }

                }
                break;

        }

    }

    public void DisplayAlertMessage(String message, DialogInterface.OnClickListener listener){

        new AlertDialog.Builder(QRCodeScannerActivity.this)
                .setMessage(message )
                .setPositiveButton( "OK",listener )
                .setNegativeButton( "Cancel",null )
                .create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(CheckPermission()){

                if(mScannerView == null){

                    mScannerView=new ZXingScannerView( this );
                    setContentView( mScannerView );

                }
                mScannerView.setResultHandler( this );
                mScannerView.startCamera();
                mScannerView.resumeCameraPreview(this);

            }else {

                RequestPermission();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
        mScannerView.stopCameraPreview();
    }
}



