package com.example.rockerfockers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText tvName;
        public EditText tvCount;
        public ImageButton bAdd;
        public ImageButton bSubtract;
        public ImageButton bDelete;
        public EditTextWatcher nameWatcher;
        public EditTextWatcher countWatcher;
        public ImageView icResource;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.resource_name);
            tvCount = itemView.findViewById(R.id.countId);
            bAdd = itemView.findViewById(R.id.addId);
            bSubtract = itemView.findViewById(R.id.subtractId);
            bDelete = itemView.findViewById(R.id.deleteId);
            nameWatcher = new EditTextWatcher();
            nameWatcher.updateType(0);
            countWatcher = new EditTextWatcher();
            countWatcher.updateType(1);
            tvName.addTextChangedListener(nameWatcher);
            tvCount.addTextChangedListener(countWatcher);
            icResource = itemView.findViewById(R.id.ic_resId);
        }

    }

    private boolean isEditable = false;
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

        holder.nameWatcher.updatePosition(holder.getAdapterPosition());
        holder.countWatcher.updatePosition(holder.getAdapterPosition());

        configureEditText(holder.tvName, resource.getName());
        configureEditText(holder.tvCount, String.format(Locale.getDefault(), "%d",  resource.getCount()));

        ImageButton subtractButton = holder.bSubtract;
        subtractButton.setEnabled(resource.nonzero());

        ImageButton deleteButton = holder.bDelete;
        deleteButton.setFocusable(isEditable);
        deleteButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);

        holder.bSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resource.subtract();
                holder.tvCount.setText(String.format(Locale.getDefault(), "%d",  resource.getCount()));
                holder.bSubtract.setEnabled(resource.nonzero());
                holder.bAdd.setEnabled(resource.getCount() < 999);
            }
        });

        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resource.add();
                holder.tvCount.setText(String.format(Locale.getDefault(), "%d",  resource.getCount()));
                holder.bSubtract.setEnabled(resource.nonzero());
                holder.bAdd.setEnabled(resource.getCount() < 999);
            }
        });

        holder.bDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    resourceList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, resourceList.size());
                }
            }
        });

        holder.icResource.setImageResource(R.drawable.ic_genres);
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public void updateEditable() {
        isEditable = !isEditable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    private void configureEditText(EditText et, String s) {
        et.setText(s);
        et.setFocusableInTouchMode(isEditable);
        et.setBackgroundResource(isEditable ? android.R.drawable.edit_text : android.R.color.transparent);
    }

    private class EditTextWatcher implements TextWatcher {
        private int pos;
        private int type;

        public void updatePosition(int pos) {
            this.pos = pos;
        }

        public void updateType(int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            // this space intentionally left blank
        }

        @Override
        public void onTextChanged(CharSequence c, int start, int before, int counter) {
            switch(type) {
                case 0:
                    resourceList.get(pos).setName(c.toString().trim());
                    break;
                case 1:
                    resourceList.get(pos).setCount(Integer.parseInt(c.toString().trim()));
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable c) {
            // this one too
        }
    }
}
