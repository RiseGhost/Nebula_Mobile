package com.riseghost.nebulamobile.ui.desktop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.riseghost.nebulamobile.NebulaRequestFile;
import com.riseghost.nebulamobile.R;
import com.riseghost.nebulamobile.XMLElements.Explorer;
import com.riseghost.nebulamobile.XMLElements.NebulaUserInfo;
import com.riseghost.nebulamobile.databinding.FragmentDesktopBinding;

public class DesktopFragment extends Fragment {

    private FragmentDesktopBinding binding;
    private String NebulaURL;
    private String SessionCookie;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDesktopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.NebulaURL = requireActivity().getIntent().getStringExtra("NebulaURL");
        this.SessionCookie =  requireActivity().getIntent().getStringExtra("SessionCookie");


        Explorer explorer = binding.Explorer;
        explorer.setNebulaURL(NebulaURL);
        explorer.setSessionCookies(SessionCookie);
        explorer.setExplorerPath(binding.ExplorerPath);
        explorer.UpdatePath("/");
        UpdateUserName();


        //NebulaRequestFile nebulaRequestFile = new NebulaRequestFile(NebulaURL,SessionCookie,"Desktop/eu.jpg");
        //try {
        //    nebulaRequestFile.join();
        //    String Filetype = nebulaRequestFile.FileType();
        //    switch (Filetype){
        //        case "jpg/jpeg":
        //            startActivity(new Intent(getActivity(),ImageView.class));
        //            break;
        //        default:
        //            break;
        //    }
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void UpdateUserName() {
        TextView UserName = binding.UserName;
        try{
            NebulaUserInfo nebulaUserInfo = new NebulaUserInfo(NebulaURL,SessionCookie);
            UserName.setText("Hi 👋🏻\n" + nebulaUserInfo.getUser().getName());
        }   catch (InterruptedException e){
            UserName.setText("Undefined");
        }
    }
}