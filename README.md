# Android_Review_6_app
LiveData usage in Activity 

LiveData 則屬於泛型資料類別：https://ithelp.ithome.com.tw/articles/10239879 免除為龍蛇雜燴的集合工具做轉型運算的煩惱。


1. timer 的使用方式


         // 設定如下常數為數字型態，且設定初始值
         COUNTDOWN_TIME = 60000L
         ONE_SECOND = 1000L


         //  timer 是物件類似類別 繼承 如下的類別和覆寫其方法
         timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {

                 // 將此回呼填入等號左邊的變數容器
                _currentTime.value = millisUntilFinished/ONE_SECOND

            }



        timer.start()
        timer.cancel()

2. 效果

![](https://raw.githubusercontent.com/QueenieCplusplus/Android_Review_6_app/main/output1.png)

![](https://raw.githubusercontent.com/QueenieCplusplus/Android_Review_6_app/main/output2.png)
