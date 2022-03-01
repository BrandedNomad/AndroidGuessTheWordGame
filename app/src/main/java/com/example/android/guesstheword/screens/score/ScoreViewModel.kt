package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore:Int): ViewModel() {
    var _finalScore:Int = 0

    init{
        Log.i("ScoreViewModel", "Final score is $finalScore")
        _finalScore = finalScore
    }
}