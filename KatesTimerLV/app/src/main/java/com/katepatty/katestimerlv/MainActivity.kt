package com.katepatty.katestimerlv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import android.util.Log
import android.view.View
import android.widget.Toast

import com.katepatty.katestimerlv.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

// import com.katepatty.katestimerlv.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var kViewModel: KViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // View Model Provider plays a role hereby
        kViewModel = ViewModelProvider(this).get(KViewModel::class.java)
        Log.i("MainActivity is called", " KViewModelProvider.get is called")

        //binding.lifecycleOwner = this.viewLifecycleOwner

        binding.kViewModel = kViewModel

        // This is used so that the binding can observe LiveData updates
        //binding.lifecycleOwner = viewLifecycleOwner

        /*binding.apply{

            lifecycleOwner = viewLifecycleOwner
        }*/
        /*binding.apply {

            binding.timerText.visibility = View.VISIBLE
            binding.scoreText.visibility = View.VISIBLE
            // z;
            //binding.goButton.setOnClickListener {  }
            //binding.endButton.setOnClickListener { appFinished() }
            //binding.replayButton.setOnClickListener { appReplayed() }
        }*/

        // This is used to observe LiveData updates
        // viewLifecycleOwner

        // Observer for the user finished event
        //
        kViewModel.eventFinish.observe(this, Observer<Boolean> { hasFinished ->
            if (hasFinished) appFinished()
        })

        kViewModel.eventReplay.observe(this, Observer<Boolean> { chooseReplay ->
            if (chooseReplay) appReplayed()
        })

        // Create the observer which updates the UI.
        val timeWatcher = Observer<String> { time ->
            // Update the UI, in this case, a TextView.
            timer_text.text = time
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        kViewModel.currentTimeString.observe(this, timeWatcher)


        val scoreCounter = Observer<Int> { number ->

            score_text.text = number.toString()

        }

        kViewModel.score.observe(this, scoreCounter)


        // could be called to update score for a variety of reasons,
        // including in response to a network request or a database load completing;
        // in all cases, the call to setValue() or postValue() triggers observers and updates the UI.

        /*go_button.setOnClickListener {
            val score = kViewModel.score
            score.value?.plus(1)
        }*/

    }

    /**
     * Called when the app is finished
     */
    private fun appFinished() {
        // Toast.makeText(it, "Game has just finished", Toast.LENGTH_SHORT).show()
        kViewModel.onFinishCompleted()
    }

    private fun appReplayed() {
        // Toast.makeText(it, "Game has just finished", Toast.LENGTH_SHORT).show()
        kViewModel.onReplayPerformed()
    }


}