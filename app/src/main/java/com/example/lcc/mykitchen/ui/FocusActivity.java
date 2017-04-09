package com.example.lcc.mykitchen.ui;
        import android.os.Bundle;


        import com.example.lcc.mykitchen.MyApp;
        import com.example.lcc.mykitchen.adapter.ForceManAdapter;
        import com.example.lcc.mykitchen.entity.CollectKiter;
        import com.example.lcc.mykitchen.entity.RelatedPartner;
        import com.example.lcc.mykitchen.entity.UserInfo;
        import com.example.lcc.mykitchen.utils.DBUtils;

        import android.util.Log;
        import android.widget.LinearLayout;
        import android.widget.ListView;

        import com.example.lcc.mykitchen.R;

        import java.util.ArrayList;
        import java.util.List;

        import cn.bmob.v3.BmobQuery;
        import cn.bmob.v3.listener.FindListener;

public class FocusActivity extends MyBaseActivity {
private ForceManAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        //初始化界面
        initialUI();

    }
    @Override
    public void initialUI() {
        //初始化ActionBar
        actionBar = (LinearLayout)findViewById(R.id.llActionbarId);
        initActionbar(R.drawable.go_back_normal, "关注的人", R.drawable.collect_delete);
        //初始化ListView
        ListView collectListview=(ListView) findViewById(R.id.listview_myf_focusId);
        adapter=new ForceManAdapter(this);
        collectListview.setAdapter(adapter);

        final List<UserInfo> focusList= new ArrayList<>();
        BmobQuery<RelatedPartner> query = new BmobQuery<>();
        query.include("relatedName");
        query.addWhereEqualTo("userName", MyApp.bmobUser.getObjectId());
        query.findObjects(this, new FindListener<RelatedPartner>() {
            @Override
            public void onSuccess(List<RelatedPartner> list) {
                for (RelatedPartner item:list){
                    focusList.add(item.getRelatedName());
                }
                adapter.addDate(focusList,true);
            }

            @Override
            public void onError(int i, String s) {
                //TODO
                Log.i("TAG", "错误代码" + i + "," + s);
            }
        });
    }




   private void getTestData(ForceManAdapter adapter) {
       /*DBUtils dbUtils=new DBUtils(this,2);
       List<CollectKiter> list= dbUtils.queryKiter();
        adapter.addDate(list,true);*/

    }



}

