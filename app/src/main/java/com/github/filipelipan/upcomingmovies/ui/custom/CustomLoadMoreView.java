package com.github.filipelipan.upcomingmovies.ui.custom;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.github.filipelipan.upcomingmovies.R;

/**
 * Created by filipejonson on 10/08/17.
 */

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.load_more_recycler_footer;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
