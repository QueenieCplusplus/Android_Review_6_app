package com.katepatty.katestimerlv

import android.os.CountDownTimer

import android.text.format.DateUtils
import androidx.lifecycle.Transformations

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class KViewModel : ViewModel() {

    private val timer: CountDownTimer

    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 1800000L

    }

    //------------- hide fields ---------

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // current time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime


    //The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }


    // Event which triggers the end of the app
    private val _Finish = MutableLiveData<Boolean>()
    val eventFinish: LiveData<Boolean>
        get() = _Finish


    private val _PlayAgain = MutableLiveData<Boolean>()
    val eventReplay: LiveData<Boolean>
        get() = _PlayAgain

    //----------------Initialize---------------------------

    init {
        _score.value = 0
        Log.i("KViewModel", "KViewModel is now created!")

        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {

                _currentTime.value = millisUntilFinished/ONE_SECOND

            }

            override fun onFinish() {

                _currentTime.value = DONE
                onFinish()
            }
        }

        timer.start()
    }



    fun onGo() {
        _score.value = (_score.value)?.plus(100)
    }


    fun onFinish() {

        timer.cancel()
        _Finish.value = true

    }

    fun onFinishCompleted(){

        _Finish.value = false
    }

    fun onReplay() {

        timer.cancel()
        timer.start()
        _PlayAgain.value = true
    }

    fun onReplayPerformed() {

        _PlayAgain.value = false
    }


}