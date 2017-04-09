package com.example.lcc.mykitchen.fragment;
		import android.os.Bundle;
		import android.support.v4.app.Fragment;
		import android.text.TextUtils;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.ViewGroup;
		import android.widget.ImageView;
		import android.widget.LinearLayout;
		import android.widget.TextView;

		import com.example.lcc.mykitchen.R;

public abstract class BaseFragment extends Fragment {
	LinearLayout actionBar;
	View contentView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public abstract void initUI();
	public void initActionbar(int leftImg,String title,int rightImg){
		if(actionBar==null){
			return;
		}
		ImageView imgL=(ImageView) actionBar.findViewById(R.id.imgLeftActionbarId);
		ImageView imgR=(ImageView) actionBar.findViewById(R.id.imgRightActionbarId);
		TextView acText=(TextView) actionBar.findViewById(R.id.tvActionbarId);
		if(leftImg==-1){
			imgL.setVisibility(View.INVISIBLE);
		}else{
			imgL.setVisibility(View.VISIBLE);
			imgL.setImageResource(leftImg);
		}
		if(rightImg==-1){
			imgR.setVisibility(View.INVISIBLE);
		}else{
			imgR.setVisibility(View.VISIBLE);
			imgR.setImageResource(rightImg);
		}
		if(TextUtils.isEmpty(title)){
			acText.setVisibility(View.INVISIBLE);
		}else{
			acText.setVisibility(View.VISIBLE);
			acText.setText(title);
		}

	}

}
