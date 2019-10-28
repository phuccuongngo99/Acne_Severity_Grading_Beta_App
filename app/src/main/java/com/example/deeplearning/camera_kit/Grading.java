package com.example.deeplearning.camera_kit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimingLogger;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deeplearning.camera_kit.ml.AcneClassifier;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.SizeSelector;
import com.otaliastudios.cameraview.SizeSelectors;
import com.otaliastudios.cameraview.AspectRatio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

public class Grading extends AppCompatActivity {
    private AcneClassifier acneGrading;

    private DrawerLayout mDrawerLayout;
    Intent intent;

    int digit;
    //volatile Bitmap input;
    private Bitmap input;
    Button captureButton;
    CameraView camera;
    TextView predText;
    byte[] byteArray;
    ImageView frameCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading);

        ButterKnife.bind(this);
        acneGrading = new AcneClassifier(this);
        predText = (TextView) findViewById(R.id.text_pred);
        captureButton = (Button) findViewById(R.id.button);
        frameCapture = (ImageView) findViewById(R.id.image);

        camera = findViewById(R.id.camera);
        camera.setLifecycleOwner(this);
        camera.setFacing(Facing.BACK);

        camera.addCameraListener(new CameraListener() {
             @Override
             public void onPictureTaken(byte[] picture) {
                 super.onPictureTaken(picture);
             // Create a bitmap or a file...
             // CameraUtils will read EXIF orientation for you, in a worker thread.
                 CameraUtils.decodeBitmap(picture, new CameraUtils.BitmapCallback() {

                     @Override
                     public synchronized void onBitmapReady(Bitmap bitmap) {
                         //Log.d("Dimension", String.valueOf(bitmap.getHeight()) + 'x' + String.valueOf(bitmap.getWidth()));
                         frameCapture.setImageBitmap(bitmap);

                         findViewById(R.id.image).setVisibility(View.VISIBLE);
                         findViewById(R.id.grey_layout).setVisibility(View.VISIBLE);
                         findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                         getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                 WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                         InferThread thread = new InferThread(bitmap);
                         thread.start();
                     }
                 });
             }
        });

        SizeSelector mwidth = SizeSelectors.minWidth(1000);
        SizeSelector mheight = SizeSelectors.minHeight(1500);
        SizeSelector dimensions = SizeSelectors.and(mwidth, mheight); // Matches sizes bigger than 1000x2000.
        //SizeSelector ratio = SizeSelectors.aspectRatio(AspectRatio.of(3, 2), 0);

        final SizeSelector result = SizeSelectors.or(
                SizeSelectors.and(dimensions),
                SizeSelectors.biggest() // If none is found, take the biggest
        );
        camera.setPictureSize(result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        intent = new Intent(Grading.this, Grading.class);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                intent = new Intent(Grading.this, MainActivity.class);
                                break;
                            case R.id.nav_grading:
                                intent = new Intent(Grading.this, Grading.class);
                                break;
                            case R.id.nav_information:
                                intent = new Intent(Grading.this, InformationActivity.class);
                                break;
                            case R.id.nav_history:
                                intent = new Intent(Grading.this, HistoryActivity.class);
                                break;
                            case R.id.nav_booking:
                                intent = new Intent(Grading.this, BookingActivity.class);
                                break;
                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        //startActivity(intent);
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                startActivity(intent);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    public void startThread(View view){
        camera.capturePicture();
    }

    public class InferThread extends Thread {
        //int digit;
        //Bitmap input;
        Bitmap bitmap;

        public InferThread(Bitmap bitmap){
            this.bitmap = bitmap;
        }
        @Override
        public synchronized void run() {
            //int width = bitmap.getWidth();
            //int height = (int) width*3/2;
            //bitmap = Bitmap.createBitmap(bitmap, 570, 0, 2271, 3350); //for Huawei
            //bitmap = Bitmap.createBitmap(bitmap, 0, 0, 1152, 1728);//for S5
            //saveImage(bitmap);
            input = Bitmap.createScaledBitmap(bitmap, 1000, 1500, false);
            //Log.d("Dimension", String.valueOf(input.getHeight()) + 'x' + String.valueOf(input.getWidth()));
            digit = acneGrading.classify(input);
            runOnUiThread(new Runnable() {
                public void run() {
                    Intent intentResult = new Intent(Grading.this, PopupActivity.class);
                    intentResult.putExtra(PopupActivity.EXTRA_DIGIT, String.valueOf(digit));
                    startActivity(intentResult);
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
            });
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        findViewById(R.id.image).setVisibility(View.GONE);
        findViewById(R.id.grey_layout).setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Shutta_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}