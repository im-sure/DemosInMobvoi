package com.example.chentingshuo.myapplist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    private Item[] item;
    private RecyclerView recyclerView;
    private AppListAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = new Item[]{new Item("one"), new Item("two")};

        appListAdapter = new AppListAdapter(this, item);

        recyclerView = (RecyclerView) findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appListAdapter);




    }
    public class AppListAdapter extends RecyclerView.Adapter<ViewHolder> implements OnItemClickListener{


        Context context;

        private final LayoutInflater inflater;

        private Item[] item;

        public AppListAdapter(Context context, Item[] item) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.item = item;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.list_item, parent, false), this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.icon.setImageResource(R.mipmap.ic_launcher);
            holder.title.setText(item[position].title);
            Log.d(TAG, "Current thread is " + Thread.currentThread().getId());
        }

        @Override
        public int getItemCount() {
            return item == null ? 0 : item.length;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public void onItemClick(View view) {
            Toast.makeText(MainActivity.this, "touch me", Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView icon;
        public TextView title;
        public OnItemClickListener listener;

        public ViewHolder(View itemview, OnItemClickListener listener) {
            super(itemview);
            this.icon = (ImageView) itemview.findViewById(R.id.icon);
            this.title = (TextView) itemview.findViewById(R.id.title);
            this.listener = listener;
            itemview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
