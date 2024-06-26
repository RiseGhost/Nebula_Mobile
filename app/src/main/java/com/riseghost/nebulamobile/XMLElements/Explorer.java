package com.riseghost.nebulamobile.XMLElements;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.riseghost.nebulamobile.NebulaDir;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Handler;

public class Explorer extends LinearLayout {
    private String path;
    private String NebulaURL;
    private String SessionCookies;
    private ExplorerPath explorerPath;
    private ArrayList<String> FilterImageFiles = new ArrayList<>(Arrays.asList(".jpeg",".jpg",".png"));
    private ArrayList<String> FilterAudioFiles = new ArrayList<>(Arrays.asList(".mp3"));
    private String Filter = null;
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

    public void setFilter(String Filter) { this.Filter = Filter; }

    public void setExplorerPath(ExplorerPath explorerPath){
        this.explorerPath = explorerPath;
        this.explorerPath.setExplorer(this);
    }

    public void UpdatePath(String path){
        this.path = path;
        this.explorerPath.UpdatePath();
        removeAllViews();
        new UpdatePathThread(this);
    }

    public String getPath(){ return this.path; }
    public String getNebulaURL(){ return this.NebulaURL; }
    public String getSessionCookies(){ return this.SessionCookies; }

    public String FileExtension(String filename){
        String fileEx = "";
        for (int index = filename.length() - 1; index >= 0 && !fileEx.contains("."); index-- ){
            fileEx = filename.charAt(index) + fileEx;
        }
        return (fileEx.length() == filename.length()) ? "" : fileEx;
    }

    private Boolean isImageFile(String filename){
        return FilterImageFiles.contains(FileExtension(filename));
    }

    private Boolean isAudioFile(String filename){
        return FilterAudioFiles.contains(FileExtension(filename));
    }

    private Boolean isFile(String filename){
        String fileExtension = FileExtension(filename);
        return !FilterImageFiles.contains(fileExtension) && !FilterAudioFiles.contains(fileExtension);
    }

    private class UpdatePathThread extends Thread{
        Explorer explorer;
        public UpdatePathThread(Explorer explorer){
            this.explorer = explorer;
            this.start();
        }

        @Override
        public void run(){
            NebulaDir dir = new NebulaDir(NebulaURL,path,SessionCookies);
            try{
                JSONObject diretory = dir.getResponseJSON();
                if (diretory == null) return;
                for(int index = 0; index < diretory.length() / 2; index++){
                    String NameElement = diretory.getString(String.valueOf(index));
                    String Type = diretory.getString("type_" + index);
                    Log.d("EXPLORERUPDATEPATH", NameElement + "  " + Type);
                    android.os.Handler mainHandler = new android.os.Handler(getContext().getMainLooper());
                    if (this.explorer.Filter == null) CreateItem(NameElement,Type);
                    else{
                        switch (this.explorer.Filter){
                            case "image":
                                if (isImageFile(NameElement)) CreateItem(NameElement,Type);
                                break;
                            case "audio":
                                if (isAudioFile(NameElement)) CreateItem(NameElement,Type);
                                break;
                            case "file":
                                if (isFile(NameElement) && !Type.equals("dir")) CreateItem(NameElement,Type);
                                break;
                            default:
                                CreateItem(NameElement,Type);
                                break;
                        }
                    }
                }
            }   catch (InterruptedException e){
                Log.e("NebulaExplorer", e.getMessage());
            } catch (JSONException e) {
                Log.e("NebulaExplorer", e.getMessage());
            }
        }

        public void CreateItem(String NameElement, String Type){
            android.os.Handler mainHandler = new android.os.Handler(getContext().getMainLooper());
            mainHandler.post(() -> { addView(new ExplorerItem(getContext(),NameElement,Type,this.explorer)); });
        }
    }
}
