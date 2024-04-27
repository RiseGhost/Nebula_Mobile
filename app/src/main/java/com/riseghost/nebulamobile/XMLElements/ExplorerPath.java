package com.riseghost.nebulamobile.XMLElements;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.riseghost.nebulamobile.R;

public class ExplorerPath extends LinearLayout {
    private Explorer explorer;
    private TextView Labelpath;
    public ExplorerPath(Context context) {
        super(context);
        init();
    }

    public ExplorerPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExplorerPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ExplorerPath(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        this.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.container_transparent_top));
        this.removeAllViews();
        this.addView(this.BackButton());
        this.addView(this.LabelPath());
    }

    public TextView BackButton(){
        TextView back = new TextView(getContext());
        back.setText("<");
        back.setTypeface(null,Typeface.BOLD);
        back.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.container));
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10,15,10,15);
        back.setPadding(24,6,24,6);
        back.setLayoutParams(params);
        back.setOnClickListener((event) -> {
            StringBuilder NewPath = new StringBuilder(this.Labelpath.getText().toString());
            String[] SplitPath = NewPath.toString().split("/");
            NewPath = new StringBuilder("/");
            for(int index = 1; index < SplitPath.length - 1; index++){
                NewPath.append(SplitPath[index] + "/");
            }
            this.Labelpath.setText(NewPath);
            this.explorer.UpdatePath(NewPath.toString());
        });
        return back;
    }

    public TextView LabelPath(){
        this.Labelpath = new TextView(getContext());
        if (this.explorer != null)  this.Labelpath.setText(this.explorer.getPath());
        else                        this.Labelpath.setText("Undefined");
        this.Labelpath.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(5,5,5,5);
        this.Labelpath.setLayoutParams(params);
        this.Labelpath.setPadding(5,5,5,5);
        this.Labelpath.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.container));
        return this.Labelpath;
    }

    public void setExplorer(Explorer explorer){
        this.explorer = explorer;
        init();
    }

    public void UpdatePath(){
        this.Labelpath.setText(this.explorer.getPath());
    }
}
