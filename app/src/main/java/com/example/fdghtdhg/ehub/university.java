package com.example.fdghtdhg.ehub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import me.anwarshahriar.calligrapher.Calligrapher;

public class university extends AppCompatActivity {

    DatabaseReference mDatabase;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = findViewById(R.id.recycler_list);
        if (mRecycler != null) {
            mRecycler.setHasFixedSize(true);
        }

        LinearLayoutManager mManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setLayoutManager(mManager);
        get_list();
        mRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),Integer.toString(v.getId()),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void get_list(){
        Query aQuery = mDatabase.child("universities").child("all-universities");
        FirebaseRecyclerAdapter<Recycler_model, Recycler_ViewHolder> mAdapter = new FirebaseRecyclerAdapter<Recycler_model,
                        Recycler_ViewHolder>(Recycler_model.class,
                R.layout.single_item_view, Recycler_ViewHolder.class, aQuery) {
            @Override
            protected void populateViewHolder(Recycler_ViewHolder v,Recycler_model m,int p) {
                Calligrapher calligrapher = new Calligrapher(getBaseContext());
                calligrapher.setFont(university.this, "Product Sans Regular.ttf", true);
                DatabaseReference post = getRef(p);
                v.data.setText(m.univ_name+"");
                // so as to avoid the error when by chance the value
                // in the database will be int

            }
        };
        mRecycler.setAdapter(mAdapter);
    }

}

/*class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        private String mItem;
        private TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mTextView = (TextView) view;
        }

        public void setItem(String item) {
            mItem = item;
            mTextView.setText(item);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick " + getPosition() + " " + mItem);
        }
    }

    private String[] mDataset;

    public MyAdapter(String[] dataset) {
        mDataset = dataset;
    }



    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.setItem(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
*/