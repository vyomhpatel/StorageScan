package b12app.vyom.com.storagescan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExtensionAdapter extends BaseAdapter {

    private List<String> mFile_Extension;
    private List<Integer> mCount;
    Context context;
    LayoutInflater layoutInflater;

    public ExtensionAdapter(List<String> mFile_Extension, List<Integer> mCount, Context context) {
        this.mFile_Extension = mFile_Extension;
        this.mCount = mCount;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mFile_Extension.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MyViewHolder myViewHolder;


        if (view == null) {

            myViewHolder = new MyViewHolder();
            view = layoutInflater.inflate(R.layout.ex_list_format, null);
            myViewHolder.tvEx = view.findViewById(R.id.tvEx);
            myViewHolder.tvCount = view.findViewById(R.id.tvCount);

            myViewHolder.tvEx.setText(mFile_Extension.get(position));
            myViewHolder.tvCount.setText(mCount.get(position).toString());

            view.setTag(myViewHolder);

        } else {

            myViewHolder = (MyViewHolder) view.getTag();

        }

        return view;
    }

   public class MyViewHolder{
       TextView tvEx;
       TextView tvCount;
   }
}
