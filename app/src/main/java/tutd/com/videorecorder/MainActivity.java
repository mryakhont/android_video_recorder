package tutd.com.videorecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.start_record_video_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startToRecordCamera();
            }
        });
    }

    @Override
    protected  void onStart() {
        super.onStart();
        PackageManager packageManager = this.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "Your device does not have camera.", Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            Toast.makeText(this, "Your device's camera is ready.", Toast.LENGTH_LONG).show();
        }
    }

    private void startToRecordCamera() {
        dispatchTakeVideoIntent();
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
//            videoView.setVideoURI(videoUri);
            Toast.makeText(this, "Your video has been stored at " + videoUri.getPath(), Toast.LENGTH_LONG).show();
        }
    }
}
