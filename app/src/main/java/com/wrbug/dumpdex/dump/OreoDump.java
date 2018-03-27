package com.wrbug.dumpdex.dump;

import android.app.Application;
import android.content.Context;

import com.wrbug.dumpdex.BuildConfig;
import com.wrbug.dumpdex.Native;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * OreoDump
 *
 * @author suanlafen
 * @since 2018/3/23
 */
public class OreoDump {

    public static void log(String txt) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        XposedBridge.log("dumpdex-> " + txt);
    }

    public static void init(final XC_LoadPackage.LoadPackageParam lpparam) {
        Native.dump(lpparam.packageName);
        XposedHelpers.findAndHookMethod("android.app.Instrumentation", lpparam.classLoader, "newApplication", ClassLoader.class, String.class, Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                log("Application=" + param.getResult());
                Native.dump(lpparam.packageName);
            }
        });

    }
}
