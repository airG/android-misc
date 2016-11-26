/*
 * ****************************************************************************
 *   Copyright  2016 airG Inc.                                                 *
 *                                                                             *
 *   Licensed under the Apache License, Version 2.0 (the "License");           *
 *   you may not use this file except in compliance with the License.          *
 *   You may obtain a copy of the License at                                   *
 *                                                                             *
 *       http://www.apache.org/licenses/LICENSE-2.0                            *
 *                                                                             *
 *   Unless required by applicable law or agreed to in writing, software       *
 *   distributed under the License is distributed on an "AS IS" BASIS,         *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 *   See the License for the specific language governing permissions and       *
 *   limitations under the License.                                            *
 * ***************************************************************************
 */

package com.airg.android.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Don't you hate it when you make a {@link Toast} and forget to call {@link Toast#show()}? That's all this class does.
 * Also, it makes sure your toast is run on the main thread. Toast without worry because it's always shown and it's
 * always on the main thread.
 */

@SuppressWarnings ( {"UnusedDeclaration", "WeakerAccess"})
public final class Toaster {

    private static final Handler uiHandler = new Handler(Looper.getMainLooper());
    private final Context context;

    /**
     * Gets you a multi-use Toaster
     * @param context Context to toast with
     * @return A nice shiny Toaster.
     */
    public Toaster with (@NonNull final Context context) {
        return new Toaster(context);
    }

    /**
     * If you toast with a duration of {@link Toast#LENGTH_SHORT} you end up with a light toast.
     * @param context Context to toast with
     * @param txtRes Format string resource to use
     * @param args Format string arguments
     */
    public static void light (@NonNull final Context context, @StringRes final int txtRes, final Object... args) {
        toast(context, context.getString(txtRes, args), LENGTH_SHORT);
    }

    /**
     * If you toast with a duration of {@link Toast#LENGTH_SHORT} you end up with a light toast.
     * @param context Context to toast with
     * @param txtRes Toast message string resource
     */
    public static void light (@NonNull final Context context, @StringRes final int txtRes) {
        toast(context, context.getString(txtRes), LENGTH_SHORT);
    }

    /**
     * If you toast with a duration of {@link Toast#LENGTH_SHORT} you end up with a light toast.
     * @param context Context to toast with
     * @param txt Toast message string
     */
    public static void light (@NonNull final Context context, final CharSequence txt) {
        toast(context, txt, LENGTH_SHORT);
    }

    /**
     * If you toast with a duration of {@link Toast#LENGTH_LONG} you end up with a dark toast.
     * @param context Context to toast with
     * @param txtRes Format string resource to use
     * @param args Format string arguments
     */
    public static void dark (@NonNull final Context context, @StringRes final int txtRes, final Object... args) {
        toast(context, context.getString(txtRes, args), LENGTH_LONG);
    }

    /**
     * If you toast with a duration of {@link Toast#LENGTH_LONG} you end up with a dark toast.
     * @param context Context to toast with
     * @param txtRes Toast message string resource
     */
    public static void dark (@NonNull final Context context, @StringRes final int txtRes) {
        toast(context, context.getString(txtRes), LENGTH_LONG);
    }

    /**
     * If you toast with a duration of {@link Toast#LENGTH_LONG} you end up with a dark toast.
     * @param context Context to toast with
     * @param txt Toast message string
     */
    public static void dark (@NonNull final Context context, final CharSequence txt) {
        toast(context, txt, LENGTH_LONG);
    }

    /**
     * Multi-use version of {@link #light(Context, int, Object...)}
     */
    public void light (@StringRes final int txtRes, final Object... args) {
        light(context, txtRes, args);
    }

    /**
     * Multi-use version of {@link #light(Context, int)}
     */
    public void light (@StringRes final int txtRes) {
        light(context, txtRes);
    }

    /**
     * Multi-use version of {@link #light(Context, CharSequence)}
     */
    public void light (final CharSequence txt) {
        light(context, txt);
    }

    /**
     * Multi-use version of {@link #dark(Context, CharSequence)}
     */
    public void dark (@StringRes final int txtRes, final Object... args) {
        dark (context, txtRes, args);
    }

    /**
     * Multi-use version of {@link #dark(Context, int)}
     */
    public void dark (@StringRes final int txtRes) {
        dark (context, txtRes);
    }

    /**
     * Multi-use version of {@link #dark(Context, CharSequence)}
     */
    public void dark (final CharSequence txt) {
        dark(context, txt);
    }

    private static void toast (@NonNull final Context context, @NonNull final CharSequence text, final int duration) {
        if (isMainThread())
            Toast.makeText(context, text, duration).show();
        else uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static boolean isMainThread () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? Looper.getMainLooper ().isCurrentThread ()
                : Looper.myLooper () == Looper.getMainLooper ();
    }

    private Toaster(@NonNull final  Context c) {
        context = c;
    }
}
