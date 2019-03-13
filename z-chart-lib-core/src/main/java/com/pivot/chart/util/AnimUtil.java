/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午9:52
 * ********************************************************
 */

package com.pivot.chart.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

import com.pivot.chart.entity.RadarViewEntity;
import com.pivot.chart.view.RadarView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 雷达图动画工具类
 */
public class AnimUtil {
    private WeakReference<RadarView> mWeakRadarView;
    private HashMap<RadarViewEntity, ValueAnimator> mAnim;

    public AnimUtil(RadarView view) {
        mWeakRadarView = new WeakReference<>(view);
        mAnim = new HashMap<>();
    }

    public void animeValue(AnimeType type, int duration, RadarViewEntity data) {
        switch (type) {
            case ZOOM:
                startZoomAnime(duration, data);
                break;
        }
    }

    public boolean isPlaying() {
        boolean isPlaying = false;
        for (ValueAnimator anime : mAnim.values()) {
            isPlaying = anime.isStarted();
            if (isPlaying) {
                break;
            }
        }
        return isPlaying;
    }

    public boolean isPlaying(RadarViewEntity data) {
        ValueAnimator anime = mAnim.get(data);
        return anime != null && anime.isStarted();
    }

    private void startZoomAnime(final int duration, final RadarViewEntity data) {
        final ValueAnimator anime = ValueAnimator.ofFloat(0, 1f);
        final List<Float> values = data.getValue();
        final List<Float> values2 = new ArrayList<>(values);
        anime.addUpdateListener(animation -> {
            RadarView view = mWeakRadarView.get();
            if (view == null) {
                anime.end();
            } else {
                float percent = Float.parseFloat(animation.getAnimatedValue().toString());
                for (int i = 0; i < values.size(); i++) {
                    values.set(i, values2.get(i) * percent);
                }
                view.invalidate();
            }
        });
        anime.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnim.remove(data);
            }
        });
        anime.setDuration(duration).start();
        mAnim.put(data, anime);
    }

    /**
     * 动画类型
     * ZOOM:缩放 ROTATE:旋转
     */
    public enum AnimeType {
        ZOOM, ROTATE
    }
}
