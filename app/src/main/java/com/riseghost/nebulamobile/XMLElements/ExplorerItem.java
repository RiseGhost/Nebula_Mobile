package com.riseghost.nebulamobile.XMLElements;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.riseghost.nebulamobile.Desktop;
import com.riseghost.nebulamobile.NebulaRequestFile;
import com.riseghost.nebulamobile.R;
import com.riseghost.nebulamobile.display_images;
import com.riseghost.nebulamobile.display_text;


public class ExplorerItem extends LinearLayout {
    private final String ElementName;
    private final String Type;
    private final Explorer explorer;
    private String path;


    public ExplorerItem(Context context, String ElementName, String Type, Explorer explorer){
        super(context);
        this.ElementName = ElementName;
        this.Type = Type;
        this.explorer = explorer;
        init();
    }

    private void init(){
        this.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.container));
        this.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10,15,10,15);
        this.setPadding(0,10,0,10);
        this.setLayoutParams(params);
        if (this.Type.equals("dir")){
            this.setOnClickListener((event) -> {
                this.explorer.UpdatePath(this.explorer.getPath() + this.ElementName + "/");
            });
        }   else{
            String NebulaURL = this.explorer.getNebulaURL();
            String SessionCookie = this.explorer.getSessionCookies();
            path = this.explorer.getPath() + this.ElementName;
            this.setOnClickListener((event) -> {
                NebulaRequestFile nebulaRequestFile = new NebulaRequestFile(NebulaURL,SessionCookie,path);
                try {
                    nebulaRequestFile.join();
                    switch (nebulaRequestFile.FileType()){
                        case ("jpg/jpeg"):
                        case("png"):
                            LaunchActivity(display_images.class);
                            break;
                        case ("File"):
                            LaunchActivity(display_text.class);
                            break;
                        default:
                            break;
                    }
                } catch (InterruptedException e) {
                    Log.e("ERROSTARTASDAS", e.getMessage());
                }

            });
        }
        this.addView(creatIcon());
        this.addView(createLabel());
    }

    private void LaunchActivity(Class ActivityClass){
        Intent intent = new Intent(getContext(),ActivityClass);
        intent.putExtra("FileName",this.ElementName);
        intent.putExtra("path",path);
        intent.putExtra("NebulaURL",this.explorer.getNebulaURL());
        intent.putExtra("SessionCookie",this.explorer.getSessionCookies());
        getContext().startActivity(intent);
    }

    private TextView createLabel(){
        TextView label = new TextView(getContext());
        label.setText(this.ElementName);
        LayoutParams label_params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        label.setGravity(Gravity.CENTER);
        label.setLayoutParams(label_params);
        return label;
    }

    private ImageView creatIcon(){
        ImageView icon = new ImageView(getContext());
        if (this.Type.equals("dir"))        icon.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.dir));
        else if (this.Type.equals("file")){
            if (this.ElementName.contains(".jpeg") || this.ElementName.contains(".jpg") || this.ElementName.contains(".png"))
                icon.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.image));
            else if (this.ElementName.contains(".mp3"))
                icon.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.music));
            else
                icon.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.file));
        }
        else                                icon.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.undefined));
        LayoutParams icon_params = new LayoutParams(110,110);
        icon_params.setMargins(15,0,0,0);
        icon.setLayoutParams(icon_params);
        return icon;
    }
}