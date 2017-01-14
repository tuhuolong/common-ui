
package app.lib.commonui.tabfragment;

import android.support.v4.app.Fragment;

/**
 * Created by chenhao on 17/1/14.
 */

public abstract class TabFragmentAdapter {
    public abstract int getCount();

    public abstract int getTabIcon(int position);

    public abstract CharSequence getTabTitle(int position);

    public abstract int getTabTitleColor(int position);

    public abstract Fragment getFragment(int position);
}
