package com.example.utils;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.common.base.Supplier;

public class FragmentUtils {

    private FragmentUtils() {
        throw new UnsupportedOperationException();
    }


    public static void replace(FragmentManager fragmentManager, @IdRes int containerId, Fragment fragment) {
        replace(fragmentManager, containerId, fragment, null);
    }

    public static void replace(FragmentManager fragmentManager, @IdRes int containerId, Fragment fragment, @Nullable String tag) {
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .commitNow();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T setRoot(FragmentManager fragmentManager, @IdRes int containerId, String tag, Supplier<T> fragmentSupplier) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = fragmentSupplier.get();
            replace(fragmentManager, containerId, fragment, tag);
        }
        return (T) fragment;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T find(FragmentManager fragmentManager, @IdRes int fragmentId) {
        return (T) fragmentManager.findFragmentById(fragmentId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T find(FragmentManager fragmentManager, String tag) {
        return (T) fragmentManager.findFragmentByTag(tag);
    }


    public static void remove(FragmentManager fragmentManager, String tag) {
        Fragment fragment = find(fragmentManager, tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow();
        }
    }
}
