package com.wts.bharatsamachar.utils;

import android.view.View;

public interface AppCallback {

    interface Callback<T> {
        void onSuccess(T response);
        void onFailure(Exception e);
    }

    interface Status<T> {
        void onSuccess(T response);
    }

    interface NetworkCallback<T> {
        void onCompleted();
        void onDataLoaded();
        void onSuccess(T response);
        void onFailure(Exception e);
    }

    interface OnClickListener<T> {
        void onItemClicked(View view, T item);
    }

    interface OnViewMoreListener {
        void onViewMoreClicked(View view, int pos);
    }

    interface OnListClickListener<T> {
        void onItemClicked(View view, T item);
        void onDeleteClicked(View view, int position, T item);
    }

    interface Progress {
        void onStartProgressBar();
        void onStopProgressBar();
    }
}
