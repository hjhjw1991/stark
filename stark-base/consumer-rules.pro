# stark plugin
# 保留stark所有类
-keepnames class * extends com.hjhjw1991.stark.plugin.Plugin {*;}
-keepnames class com.hjhjw1991.stark.framework.** { public *;}
-keepnames class com.hjhjw1991.stark.plugin.** { public *;}
-keepnames class com.hjhjw1991.stark.util.** { public *;}