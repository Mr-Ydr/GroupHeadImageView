package com.drying.HeadImageView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GroupHeadImageView head1, head2, head3, head4, head5, head6, head7, head8, head9, head10;
    private List<String> urlList1;
    private List<String> urlList2;
    private List<String> urlList3;
    private List<String> urlList4;
    private List<String> urlList5;
    private List<String> urlList6;
    private List<String> urlList7;
    private List<String> urlList8;
    private List<String> urlList9;
    private List<String> urlList10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        head1 = findViewById(R.id.head1);
        head2 = findViewById(R.id.head2);
        head3 = findViewById(R.id.head3);
        head4 = findViewById(R.id.head4);
        head5 = findViewById(R.id.head5);
        head6 = findViewById(R.id.head6);
        head7 = findViewById(R.id.head7);
        head8 = findViewById(R.id.head8);
        head9 = findViewById(R.id.head9);
        head10 = findViewById(R.id.head10);

        urlList1 = new ArrayList<>();
        urlList1.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3460857493,3352743706&fm=26&gp=0.jpg");
        head1.setHeadList(urlList1);

        urlList2 = new ArrayList<>();
        urlList2.addAll(urlList1);
        urlList2.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2859971253,1279006959&fm=26&gp=0.jpg");
        head2.setHeadList(urlList2);

        urlList3 = new ArrayList<>();
        urlList3.addAll(urlList2);
        urlList3.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541044626081&di=02dad8c32ed2033602bb853a8cae58fd&imgtype=0&src" +
                "=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201512%2F13%2F20151213071550_vNJTm.jpeg");
        head3.setHeadList(urlList3);

        urlList4 = new ArrayList<>();
        urlList4.addAll(urlList3);
        urlList4.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2190038428,764807561&fm=26&gp=0.jpg");
        head4.setHeadList(urlList4);

        urlList5 = new ArrayList<>();
        urlList5.addAll(urlList4);
        urlList5.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541044654698&di=4515fc206904b019109197e067bc829d&imgtype=0&src" +
                "=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201609%2F28%2F20160928173134_T5Kvj.thumb.700_0.jpeg");
        head5.setHeadList(urlList5);

        urlList6 = new ArrayList<>();
        urlList6.addAll(urlList5);
        urlList6.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1314135381,2778610172&fm=26&gp=0.jpg");
        head6.setHeadList(urlList6);

        urlList7 = new ArrayList<>();
        urlList7.addAll(urlList6);
        urlList7.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3703741891,2145845339&fm=26&gp=0.jpg");
        head7.setHeadList(urlList7);

        urlList8 = new ArrayList<>();
        urlList8.addAll(urlList7);
        urlList8.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3878176532,1414630775&fm=26&gp=0.jpg");
        head8.setHeadList(urlList8);

        urlList9 = new ArrayList<>();
        urlList9.addAll(urlList8);
        urlList9.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541044735567&di=50e45466c34749588ac77a8490aa7b4c&imgtype=0&src" +
                "=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170711%2F0a63c8c13888466db7ef31177dc4cd3d.png");
        head9.setHeadList(urlList9);

        urlList10 = new ArrayList<>();
        urlList10.addAll(urlList9);
        head10.setHeadList(urlList10);
    }
}
