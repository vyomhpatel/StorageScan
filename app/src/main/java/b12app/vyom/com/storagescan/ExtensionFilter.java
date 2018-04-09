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

//        Log.i(TAG, "count "+ex_count_5);
//        Log.i(TAG, "count "+first_5);

        if(extension_list.size()>=5) {
            List<String> first_5 = extension_list.subList(0, 4);
            List<Integer> ex_count_5 = ex_count.subList(0, 4);
            ExtensionAdapter extensionAdapter = new ExtensionAdapter(first_5, ex_count_5, ExtensionFilter.this);
            listExtension.setAdapter(extensionAdapter);
        } else{
            ExtensionAdapter extensionAdapter = new ExtensionAdapter(extension_list, ex_count, ExtensionFilter.this);
            listExtension.setAdapter(extensionAdapter);
        }
    }
}
