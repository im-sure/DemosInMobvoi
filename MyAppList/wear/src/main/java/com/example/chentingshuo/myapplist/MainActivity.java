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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    private Item[] item;
    private ArrayList<Item> arrayList;
    private RecyclerView recyclerView;
    private AppListAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = new Item[]{new Item("one"), new Item("two"), new Item("three"), new Item("four")
                , new Item("five"), new Item("six"), new Item("seven"), new Item("eight")
                , new Item("nine"), new Item("ten")};
        arrayList = new ArrayList<Item>(Arrays.asList(item));

        appListAdapter = new AppListAdapter(this, arrayList);

        recyclerView = (RecyclerView) findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appListAdapter);




    }
    public class AppListAdapter extends RecyclerView.Adapter<ViewHolder> {


        Context context;

        private final LayoutInflater inflater;

        private ArrayList<Item> arrayList;

        public AppListAdapter(Context context, ArrayList<Item> arrayList) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.arrayList = arrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.content.setX(0);
            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            holder.icon.setImageResource(R.mipmap.ic_launcher);
            holder.title.setText(arrayList.get(position).title);
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, holder.title.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, holder.title.getText() + " deleted", Toast.LENGTH_SHORT).show();
                    arrayList.remove(position);
                    Log.d(TAG, "arrayList size is " + arrayList.size());
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrayList.size());
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList == null ? 0 : arrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeLayout swipeLayout;
        private ImageView icon;
        private TextView title;
        private RelativeLayout content;
        private RelativeLayout delete;

        public ViewHolder(View itemview) {
            super(itemview);
            this.swipeLayout = (SwipeLayout) itemview.findViewById(R.id.swipe_layout);
            this.icon = (ImageView) itemview.findViewById(R.id.icon);
            this.title = (TextView) itemview.findViewById(R.id.title);
            this.content = (RelativeLayout) itemview.findViewById(R.id.content);
            this.delete = (RelativeLayout) itemview.findViewById(R.id.delete);


        }
    }

}
