package cn.lygtc.coolweather;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.os.Message;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.baidu.location.BDLocation;
        import com.baidu.location.BDLocationListener;
        import com.baidu.location.LocationClient;
        import com.baidu.location.LocationClientOption;

        import org.w3c.dom.Text;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.logging.Handler;
        import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements BDLocationListener {

    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(this);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {

            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();

        }
    }

    private void requestLocation() {
        locationClient.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须同意所有权限才能使用本APP", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            }

        }
    }
    @Override
    public void  onReceiveLocation (BDLocation bdLocation) {
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            String address = bdLocation.getCountry() + bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() + bdLocation.getStreetNumber();
            Log.i("定位结果:", address);
        }

    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
    }
}

