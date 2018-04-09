package b12app.vyom.com.storagescan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExtensionFilter extends AppCompatActivity {

    private static final String TAG ="tg" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_filter);

        ListView listExtension = findViewById(R.id.listExtension);
        Intent intent = getIntent();
        List<String> extension_list = intent.getStringArrayListExtra("extension");
        List<Integer> ex_count = intent.getIntegerArrayListExtra("extension_count");
        Log.i(TAG, "count "+ex_count);
        Log.i(TAG, "count "+extension_list);
        ExtensionAdapter extensionAdapter = new ExtensionAdapter(extension_list,ex_count,ExtensionFilter.this);
        listExtension.setAdapter(extensionAdapter);
    }
}
