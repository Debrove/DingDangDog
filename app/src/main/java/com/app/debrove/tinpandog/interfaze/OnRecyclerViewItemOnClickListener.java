package com.app.debrove.tinpandog.interfaze;

import android.view.View;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog.interfaze
 * <p>
 * OnClickListener for {@link android.support.v7.widget.RecyclerView} news_item.
 */

public interface OnRecyclerViewItemOnClickListener {
    void onItemClick(View v, int position);
}
