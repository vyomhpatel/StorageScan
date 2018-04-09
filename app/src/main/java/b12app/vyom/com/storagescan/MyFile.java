package b12app.vyom.com.storagescan;

import java.util.ArrayList;

public class MyFile {

   ArrayList<String> file_names;
   long[] file_size;
   double total_size;

    public MyFile(ArrayList<String> file_names, long[] file_size) {
        this.file_names = file_names;
        this.file_size = file_size;
    }

    public ArrayList<String> getFile_names() {
        return file_names;
    }

    public long[] getFile_size() {
        return file_size;
    }
}
