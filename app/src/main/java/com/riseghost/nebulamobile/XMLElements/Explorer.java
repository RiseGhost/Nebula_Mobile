package com.riseghost.nebulamobile.XMLElements;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.riseghost.nebulamobile.NebulaDir;
import com.riseghost.nebulamobile.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Explorer extends LinearLayout {
    private String path;
    private String NebulaURL;
    private String SessionCookies;
    private ExplorerPath explorerPath;
    public Explorer(Context context) {
        super(context);
    }

    public Explorer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Explorer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Explorer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setNebulaURL(String NebulaURL){
        this.NebulaURL = NebulaURL;
    }

    public void setSessionCookies(String SessionCookies){
        this.SessionCookies = SessionCookies;
    }

    public void setExplorerPath(ExplorerPath explorerPath){
        this.explorerPath = explorerPath;
        this.explorerPath.setExplorer(this);
    }

    public void UpdatePath(String path){
        this.path = path;
        this.explorerPath.UpdatePath();
        this.removeAllViews();
        NebulaDir dir = new NebulaDir(this.NebulaURL,this.path,this.SessionCookies);
        try{
            JSONObject diretory = dir.getResponseJSON();
            if (diretory == null) return;
            for(int index = 0; index < diretory.length() / 2; index++){
                String NameElement = diretory.getString(String.valueOf(index));
                String Type = diretory.getString("type_" + index);
                Log.d("EXPLORERUPDATEPATH", NameElement + "  " + Type);
                this.addView(new ExplorerItem(getContext(),NameElement,Type,this));
            }
        }   catch (InterruptedException e){
            Log.e("NebulaExplorer", e.getMessage());
        } catch (JSONException e) {
            Log.e("NebulaExplorer", e.getMessage());
        }
    }

    public String getPath(){ return this.path; }
    public String getNebulaURL(){ return this.NebulaURL; }
    public String getSessionCookies(){ return this.SessionCookies; }
}
