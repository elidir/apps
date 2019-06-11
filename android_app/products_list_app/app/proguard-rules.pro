# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\My\Programs\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


#Error:Execution failed for task ':app:transformClassesWithNewClassShrinkerForDebug'.
#> Warnings found during shrinking, please use -dontwarn or -ignorewarnings to suppress them.

#com/squareup/picasso/OkHttpDownloader references unknown class: com/squareup/okhttp/Cache
#...
-ignorewarnings


-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# GreenDao rules
# Source: http://greendao-orm.com/documentation/technical-faq
#
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties