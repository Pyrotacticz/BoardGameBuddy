package com.example.rockerfockers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvCount;
        public ImageButton bAdd;
        public ImageButton bSubtract;
        public ImageButton bDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.resource_name);
            tvCount = itemView.findViewById(R.id.countId);
            bAdd = itemView.findViewById(R.id.addId);
            bSubtract = itemView.findViewById(R.id.subtractId);
            bDelete = itemView.findViewById(R.id.deleteId);
        }

    }

    private List<Resource> resourceList;

    public GameAdapter(Game game) {
        this.resourceList = game.resources;
    }

    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View resView = inflater.inflate(R.layout.resource_list, parent, false);

        return new ViewHolder(resView);
    }

    @Override
    public void onBindViewHolder(GameAdapter.ViewHolder holder, int position) {
        Resource resource = resourceList.get(position);

        TextView textView = holder.tvName;
        textView.setText(resource.getName());

        TextView count = holder.tvCount;
        count.setText(Integer.toString(resource.getCount()));

        ImageButton addButton = holder.bAdd;

        ImageButton subtractButton = holder.bSubtract;
        subtractButton.setEnabled(resource.nonzero());

        ImageButton deleteButton = holder.bDelete;

        holder.bSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resource.subtract();
                holder.tvCount.setText(Integer.toString(resource.getCount()));
                holder.bSubtract.setEnabled(resource.nonzero());
            }
        });

        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resource.add();
                holder.tvCount.setText(Integer.toString(resource.getCount()));
                holder.bSubtract.setEnabled(resource.nonzero());
            }
        });

        holder.bDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                resourceList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), resourceList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }


}
