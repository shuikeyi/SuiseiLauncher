package cn.suiseiseki.www.suiseilauncher;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public class SuiseiLauncherFragment extends ListFragment {
    private static final String TAG = "SuiseiLauncherFragment";

    /* get Activity packages */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startupIntent,0);
        Log.i(TAG, "i find " + activities.size() + " activities.");
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(lhs.loadLabel(pm).toString(), rhs.loadLabel(pm).toString());
            }
        });
        ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(),android.R.layout.simple_list_item_1,activities){
            public View getView(int pos,View convertView,ViewGroup parent)
            {
                PackageManager pm = getActivity().getPackageManager();
                View v = super.getView(pos, convertView, parent);
                TextView textView = (TextView)v;
                ResolveInfo ri = getItem(pos);
                Drawable drawable = ri.loadIcon(pm);
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                textView.setText(ri.loadLabel(pm));
                return  v;
            }
        };
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView listView,View v,int position,long id)
    {
        ResolveInfo resolveInfo = (ResolveInfo)listView.getAdapter().getItem(position);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        if(activityInfo == null)
            return;
        Intent i =new Intent(Intent.ACTION_MAIN);
        i.setClassName(activityInfo.applicationInfo.packageName,activityInfo.name);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



}
