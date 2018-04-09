package b12app.vyom.com.storagescan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NameSizeAdapter extends BaseAdapter {

    private List<String> mFile_list;
    private List<Long> mFile_size;
    Context context;
    LayoutInflater layoutInflater;

    public NameSizeAdapter(List<String> mFile_list, List<Long> mFile_size, Context context) {
        this.mFile_list = mFile_list;
        this.mFile_size = mFile_size;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mFile_list.size();
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
            view = layoutInflater.inflate(R.layout.file_list_format, null);
            myViewHolder.tvName = view.findViewById(R.id.tvName);
            myViewHolder.tvSize = view.findViewById(R.id.tvSize);

            myViewHolder.tvName.setText(mFile_list.get(position));
            myViewHolder.tvSize.setText(mFile_size.get(position).toString());

            view.setTag(myViewHolder);

        } else {

            myViewHolder = (MyViewHolder) view.getTag();

        }

        return view;
    }

   public class MyViewHolder{
       TextView tvName;
       TextView tvSize;
   }
}
