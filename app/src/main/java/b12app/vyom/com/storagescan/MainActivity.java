package b12app.vyom.com.storagescan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "total" ;
    Button btnStart, btnStop, btnExtension;
    ListView listView, listVEx;
    List<String> file_list;
    List<Long> file_size;
    List<String> file_extension;
    List<String> sorted_file;
    List<Long> sorted_file_size;
    List<File> fileList;
    long total_size =0 ;
    MyAsyncTask myAsyncTask;
    long average_size = 0;
    TextView tvAvg;
    Set<String> hsVal;
    List<Map.Entry<String, Integer>> listMap;
    ArrayList<String> extension_repeatedList;
    ArrayList<Integer> extension_repeatedCount;
    TextView tvExtension;
    HashMap<String, Integer> hmExtension;
    public static final String DOT_SEPARATOR = ".";

    public class FileComparator implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            File f1 = (File) o1;
            File f2 = (File)o2;
            return  f1.length()<=f2.length()?1:-1;
        }
    }



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
        btnExtension = findViewById(R.id.btnExtension);
        tvAvg = findViewById(R.id.tvAvg);
        tvExtension = findViewById(R.id.tvExtension);
        file_list = new ArrayList<>();
        fileList = new ArrayList<>();
        file_size = new ArrayList<>();
        sorted_file = new ArrayList<String>();
        sorted_file_size = new ArrayList<Long>();
        file_extension = new ArrayList<>();


        btnExtension.setEnabled(false);

        extension_repeatedList = new ArrayList<>();
        extension_repeatedCount = new ArrayList<>();

        hmExtension= new HashMap<>();
        myAsyncTask = new MyAsyncTask();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAsyncTask.execute();
                btnExtension.setEnabled(true);
            }
        });

        btnExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExtensionFilter.class);
                intent.putStringArrayListExtra("extension",extension_repeatedList);
                intent.putIntegerArrayListExtra("extension_count",extension_repeatedCount);
                startActivity(intent);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAsyncTask.cancel(true);
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
            fileList.add(file);
            file_size.add(file.length());
            total_size = total_size+ file.length();
            file_extension.add(getFileExtension(file));

            }
        }






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



            if(isCancelled()){

            }
             else {

                Collections.sort(fileList,new FileComparator());
                for(int a = 0 ; a<10 && a<fileList.size();a++){

                    sorted_file.add(fileList.get(a).getName());
                    sorted_file_size.add(fileList.get(a).length());

                }


                average_size = total_size / file_list.size();
                Log.i(TAG, "total: " + average_size);
                tvAvg.setText("Average Size:" + String.valueOf(average_size));
                Log.i(TAG, "File extensions: " + file_extension);
                ListAdapter adapter = new NameSizeAdapter(sorted_file, sorted_file_size, MainActivity.this);
                listView.setAdapter(adapter);

                for (int x = 0; x < file_extension.size(); x++) {

                    if (hmExtension.containsKey(file_extension.get(x))) {
                        hmExtension.put(file_extension.get(x), hmExtension.get(file_extension.get(x)) + 1);
                    } else {
                        hmExtension.put(file_extension.get(x), 1);
                    }

                    listMap = new ArrayList<>(hmExtension.entrySet());
                    Collections.sort(listMap, (Comparator<? super Map.Entry<String, Integer>>) new Comparator<Map.Entry<String,Integer>>() {
                        @Override
                        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                            return o1.getValue()>=o2.getValue()?1:-1;
                        }




                    });


                }

                }

                Log.i(TAG, "hashmap:" + hmExtension.entrySet());
                hsVal = hmExtension.keySet();
                for (String extension_key : hsVal) {
                    if (hmExtension.get(extension_key) > 1) {



                        extension_repeatedList.add(extension_key);

                        extension_repeatedCount.add(hmExtension.get(extension_key));

                        tvExtension.append("Extension: " + extension_key + " Count: " + hmExtension.get(extension_key) + "\n");

                    }
                }
                Log.i(TAG, "extension" + extension_repeatedList);

            }
        }
    }





