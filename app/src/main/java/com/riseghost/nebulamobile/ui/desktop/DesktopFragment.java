package com.riseghost.nebulamobile.ui.desktop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
        RadioButton rb_all = binding.rbAll;
        rb_all.setChecked(true);

        Explorer explorer = binding.Explorer;
        explorer.setNebulaURL(NebulaURL);
        explorer.setSessionCookies(SessionCookie);
        explorer.setExplorerPath(binding.ExplorerPath);
        explorer.UpdatePath("/");
        new UpdateUserName();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class UpdateUserName extends Thread{
        public UpdateUserName(){
            this.start();
        }
        @Override
        public void run(){
            TextView UserName = binding.UserName;
            try{
                NebulaUserInfo nebulaUserInfo = new NebulaUserInfo(NebulaURL,SessionCookie);
                String Name = nebulaUserInfo.getUser().getName();
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> {
                    UserName.setText("Hi 👋🏻\n" + Name);
                });
            }   catch (InterruptedException e){
                UserName.setText("Undefined");
            }
        }
    }
}