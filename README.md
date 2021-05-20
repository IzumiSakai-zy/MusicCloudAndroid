# MusicCloudAndroid
一个Android音乐云盘APP



## bug记录

> ### 低版本项目在版本Android  Studio运行
>
> * 报错`Gradle's dependency cache may be corrupt (this sometimes occurs after a network connection timeout.)`
> * 将`gradle/wrapper/gradle-wrapper.properties`文件中的`gradle`版本进行更换就可以了
> * 目前版本一般换为`gradle-6.5-all.zip`就能运行
>
> ### 低版本ConstrainLayout无法运行
>
> * 报错`Error inflating class androidx.constraintlayout.widget.ConstraintLayout`
> * 将`androidx.constraintlayout:constraintlayout`换成最新版本`2.0.4`