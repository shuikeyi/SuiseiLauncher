package cn.suiseiseki.www.suiseilauncher;

import android.app.Fragment;

/**
 * Created by Administrator on 2016/3/6.
 */
public class SuiseiLauncherActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment()
    {
        return new SuiseiLauncherFragment();
    }
}
