package np.com.bimalkafle.quizonline

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import np.com.bimalkafle.quizonline.databinding.ActivityQuizBinding
import np.com.bimalkafle.quizonline.databinding.ScoreDialogBinding


class QuizActivity : AppCompatActivity(),View.OnClickListener {

    companion object {
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQuizBinding

    private var currentQuestionIndex = 0
    private var selectedAnswer = ""
    private var score = 0
    private var answeredQuestions = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)
            prevBtn.setOnClickListener(this@QuizActivity)
        }

        loadQuestions()
        startTimer()
    }

    private fun startTimer(){
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            @SuppressLint("DefaultLocale")
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)

            }

            override fun onFinish() {
                //Finish the quiz
            }

        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestions(){
        selectedAnswer = ""
        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/ ${questionModelList.size} "
            questionProgressIndicator.progress =
                ( currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100 ).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]
            // Reset button colors
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }
    }

    override fun onClick(view: View?) {
        val clickedBtn = view as Button
        when (clickedBtn.id) {
            R.id.next_btn -> {
                // Next button is clicked
                if (selectedAnswer.isEmpty()) {
                    Toast.makeText(applicationContext, "Please select an answer to continue", Toast.LENGTH_SHORT).show()
                    return
                }
                // Check if the current question was already answered
                val correctAnswer = questionModelList[currentQuestionIndex].correct
                if (!answeredQuestions.containsKey(currentQuestionIndex)) {
                    if (selectedAnswer == correctAnswer) {
                        score++
                    }
                } else {
                    // If changing answer, adjust the score if necessary
                    if (answeredQuestions[currentQuestionIndex] != correctAnswer && selectedAnswer == correctAnswer) {
                        score++
                    } else if (answeredQuestions[currentQuestionIndex] == correctAnswer && selectedAnswer != correctAnswer) {
                        score--
                    }
                }

                // Save the answer
                answeredQuestions[currentQuestionIndex] = selectedAnswer

                currentQuestionIndex++
                loadQuestions()
            }
            R.id.prev_btn -> {
                // Prev button is clicked
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--
                    loadQuestions()
                }
            }
            else -> {
                // Options button is clicked
                selectedAnswer = clickedBtn.text.toString()
                binding.apply {
                    btn0.setBackgroundColor(getColor(R.color.gray))
                    btn1.setBackgroundColor(getColor(R.color.gray))
                    btn2.setBackgroundColor(getColor(R.color.gray))
                    btn3.setBackgroundColor(getColor(R.color.gray))
                }
                clickedBtn.setBackgroundColor(getColor(R.color.orange))
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat() ) *100 ).toInt()

        val dialogBinding  = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if(percentage>60){
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            }else{
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener {
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()

    }
}


















