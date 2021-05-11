package com.iuh.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private List<Location> locations;
    private Listener listener;

    public interface Listener {
        void onEdit(int p);
        void onDelete(int p);
    }


    public CustomAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cv, new Listener() {

            @Override
            public void onEdit(int p) {
                Toast toast = Toast.makeText(parent.getContext(), "1", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onDelete(int p) {
                TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(parent.getContext());
                SQLiteDatabase db = travelDatabaseHelper.getWritableDatabase();
                TextView location = cv.findViewById(R.id.tvLocation);
                int result = travelDatabaseHelper.deleteLocation(db, location.getText().toString());
                Toast toast;
                if (result != 0) {
                    toast = Toast.makeText(parent.getContext(), "Deleted", Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(parent.getContext(), "Not deleted", Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView tvID = (TextView) cardView.findViewById(R.id.tvID);
        TextView tvLocation = (TextView) cardView.findViewById(R.id.tvLocation);
        tvID.setText(String.valueOf(locations.get(position).get_id()));
        tvLocation.setText(String.valueOf(locations.get(position).getName()));
    }
    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Listener listener;
        private CardView cardView;
        private ImageButton btnEdit;
        private ImageButton btnDelete;
        private EditText name;

        public ViewHolder(CardView v, Listener listener) {
            super(v);
            cardView = v;

            btnEdit = cardView.findViewById(R.id.ibUpdate);
            btnDelete = cardView.findViewById(R.id.ibDelete);

            this.listener = listener;

            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibUpdate:
                    listener.onEdit(this.getLayoutPosition());
                    break;
                case R.id.ibDelete:
                    listener.onDelete(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }
}
