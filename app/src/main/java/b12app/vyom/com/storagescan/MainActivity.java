package b12app.vyom.com.storagescan;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "total" ;
    Button btnStart, btnStop;
    ListView listView;
    List<String> file_list;
    List<Long> file_size;
    List<String> file_extension;
    long total_size =0 ;
    MyAsyncTask myAsyncTask;
    long average_size = 0;
    TextView tvAvg;
    Set<String> hsVal;
    TextView tvExtension;
    HashMap<String, Integer> hmExtension;
    public static final String DOT_SEPARATOR = ".";

    public static String getFileExtension(File file) {
        if (file == null) {
            return null;
        }

        String name = file.getName();
        int extIndex = name.lastIndexOf(MainActivity.DOT_SEPARATOR);

        if (extIndex == -1) {
            return "";
        } else {
            return name.substring(extIndex + 1);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView  = findViewById(R.id.listView);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        tvAvg = findViewById(R.id.tvAvg);
        tvExtension= findViewById(R.id.tvExtension);
        file_list = new ArrayList<>();
        file_size = new ArrayList<>();
        file_extension = new ArrayList<>();
        hmExtension= new HashMap<>();
        myAsyncTask = new MyAsyncTask();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAsyncTask.execute();
            }
        });


    }

    private void listFolder(File dir){
        File[] subDirs = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File sub = new File(name);
                return sub.isDirectory();
            }
        });

        lisFile(dir);

        for(File folder: subDirs){
            listFolder(folder);
        }
    }

    private void lisFile(File dir) {

        File[] files = dir.listFiles();
        for(File file: files){
            if(file.isDirectory()){
                lisFile(file);
            } else{
            file_list.add(String.valueOf(file));
            file_size.add(file.length());
            total_size = total_size+ file.length();
            file_extension.add(getFileExtension(file));

            }
        }

//        for(int i=0; i<file_list.size()-1;i++){
//            total_size = total_size+file_size.get(i);
//
//
//
//        }



    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            File root  = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsoluteFile()));
            listFolder(root);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            average_size = total_size/file_list.size();
            Log.i(TAG, "total: "+average_size);
            tvAvg.setText("Average Size:"+String.valueOf(average_size));
            Log.i(TAG, "File extensions: "+file_extension);
            ListAdapter adapter = new NameSizeAdapter(file_list,file_size,MainActivity.this);
            listView.setAdapter(adapter);

            for(int x= 0; x<file_extension.size();x++){

               if(hmExtension.containsKey(file_extension.get(x))){
                   hmExtension.put(file_extension.get(x), hmExtension.get(file_extension.get(x))+1);
               } else{
                   hmExtension.put(file_extension.get(x), 1);
               }
            }

            Log.i(TAG, "hashmap:"+hmExtension.entrySet());
            hsVal = hmExtension.keySet();
            for(String extension_key: hsVal){
                if(hmExtension.get(extension_key)>1){
                    tvExtension.append("Extension: "+extension_key +" Count: "+hmExtension.get(extension_key));
                }
            }
        }
    }



    }

