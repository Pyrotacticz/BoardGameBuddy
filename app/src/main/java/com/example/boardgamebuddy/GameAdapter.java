
/*
 * Created by BoardGameBuddies
 * Copyright (c) 2022. All rights reserved.
 * Last modified 1/2/22, 4:06 PM
 */

package com.example.boardgamebuddy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.app.RemoteInput;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Handles the view of the resources.
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    // view class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText tvName;
        public EditText tvCount;
        public ImageButton bAdd;
        public ImageButton bSubtract;
        public ImageButton bDelete;
        public EditTextWatcher nameWatcher;
        public EditTextWatcher countWatcher;
        public ImageButton icResource;

        // view constructor
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
            icResource.setImageResource(R.drawable.ic_genres);
            icResource.setTag(2);
        }

    }

    Context context;
    private boolean isEditable = false;
    private List<Resource> resourceList;
    private List<ImageButton> icons;
    private View popupView;

    // adapter constructor
    public GameAdapter(Context context, Game game) {
        this.context = context;
        this.resourceList = game.resources;

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.icon_select, null);

        icons = new ArrayList<>();
        icons.add(popupView.findViewById(R.id.imageButton1));
        icons.add(popupView.findViewById(R.id.imageButton2));
        icons.add(popupView.findViewById(R.id.imageButton3));
        icons.add(popupView.findViewById(R.id.imageButton4));
        icons.add(popupView.findViewById(R.id.imageButton5));
        icons.add(popupView.findViewById(R.id.imageButton6));
        icons.add(popupView.findViewById(R.id.imageButton7));
        icons.add(popupView.findViewById(R.id.imageButton8));
        for (int i = 0; i < icons.size(); i++) {
            icons.get(i).setTag(i);
        }
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
        ImageButton addButton = holder.bAdd;
        ImageButton deleteButton = holder.bDelete;
        ImageButton icButton = holder.icResource;

        subtractButton.setEnabled(resource.nonzero() && !isEditable);
        addButton.setEnabled(!isEditable && resource.getCount() < 999);
        deleteButton.setFocusable(isEditable);
        deleteButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);
        icButton.setEnabled(isEditable);
        icButton.setBackgroundColor(Color.parseColor(isEditable ? "#545454" : "#383838"));
        icButton.setImageDrawable(resource.getIcon());


        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resource.subtract();
                holder.tvCount.setText(String.format(Locale.getDefault(), "%d",  resource.getCount()));
                holder.bSubtract.setEnabled(resource.nonzero());
                holder.bAdd.setEnabled(resource.getCount() < 999);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resource.add();
                holder.tvCount.setText(String.format(Locale.getDefault(), "%d",  resource.getCount()));
                holder.bSubtract.setEnabled(resource.nonzero());
                holder.bAdd.setEnabled(resource.getCount() < 999);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
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

        // handles the popup window for icon selection
        icButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // to dim background
                View container = (View) popupWindow.getContentView().getParent();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                // add flag
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f;
                wm.updateViewLayout(container, p);

                for (ImageButton icon : icons) {
                    if ((int) icon.getTag() == resourceList.get(pos).getTag()) {
                        icon.setBackgroundColor(context.getColor(R.color.black_l2));
                    } else {
                        icon.setBackgroundColor(context.getColor(R.color.black_l3));
                    }

                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resourceList.get(pos).setIcon(icon.getDrawable());
                            resourceList.get(pos).setTag((int) icon.getTag());
                            // necessary to set as icon lags behind the resource update
                            icButton.setImageDrawable(icon.getDrawable());
                            icButton.setTag(icon.getTag());
                            popupWindow.dismiss();
                        }
                    });
                }

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

            }
        });
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

    // updates the view with the changes to the resource name and counter.
    private void configureEditText(EditText et, String s) {
        et.setText(s);
        et.setFocusableInTouchMode(isEditable);
        et.setBackgroundResource(isEditable ? R.drawable.textbox : android.R.color.transparent);
    }

    // handles text changes on resource name and counter.
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
