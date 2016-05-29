package lsj.kobe.lsjchat.client.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.view.fragment.FriendListsFragment;

public class MainActivity extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private MyViewPagerAdapter pagerAdapter;
    private List<Fragment> datas = new ArrayList<>();

    protected int currentId = R.id.rb_main_conversation; //当前点击的radiobutton的id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_main);
        mRadioGroup.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        mRadioGroup.check(currentId);
    }

    private class myOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_conversation:

                    mViewPager.setCurrentItem(0, true);
                    break;
                case R.id.rb_main_friends:
                    mViewPager.setCurrentItem(1, true);
                    break;
            }
            currentId = checkedId;
        }
    }
    private void initData() {

        FriendListsFragment mView = new FriendListsFragment();
        FriendListsFragment mView2 = new FriendListsFragment();
        datas.add(mView);
        datas.add(mView2);
        pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),datas);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter{
        List<Fragment> datas;
        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> datas) {
            super(fm);
            this.datas = datas;
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }
        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }
    }
}
