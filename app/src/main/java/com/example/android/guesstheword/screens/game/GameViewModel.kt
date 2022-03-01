package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer

    // The current word
    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    //Game finished event
    private var _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private var _timeString = MutableLiveData<String>()
    val timeString: LiveData<String>
        get() = _timeString

    init{
        Log.i("GameViewModel","GameViewModel created")
        resetList()
        nextWord()
        _score.value = 0
        _word.value = wordList.removeAt(0)
        _eventGameFinished.value = false

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // TODO implement what should happen each tick of the timer
                _timeString.value = DateUtils.formatElapsedTime(millisUntilFinished)
                Log.i("timer",_timeString.value!!)

            }

            override fun onFinish() {
                // TODO implement what should happen when the timer finishes
                _eventGameFinished.value = true
            }
        }



        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel","GameViewModel Destroyed")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = score.value!! - 1
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value!! + 1
        nextWord()
    }

    fun onGameFinishedComplete(){
        _eventGameFinished.value = false
    }


}