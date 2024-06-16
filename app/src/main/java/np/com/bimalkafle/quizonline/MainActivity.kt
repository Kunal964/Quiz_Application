package np.com.bimalkafle.quizonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import np.com.bimalkafle.quizonline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var quizModelList: MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()


    }

    private fun setupRecyclerView() {
        // binding.progressBar.visibility = View.GONE
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        val listQuestionModel = mutableListOf<QuestionModel>()
        listQuestionModel.add(
            QuestionModel(
                "What is android?",
                mutableListOf("Language", "OS", "Product", "Framework"),
                "OS"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "Who owns Android?",
                mutableListOf("Apple", "Microsoft", "Samsung", "Google"),
                "Google"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "What is the primary programming language used for Android app development?",
                mutableListOf("Swift", "Kotlin/Java", "C#", "Python"),
                "Kotlin/Java"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "Which Android version introduced Material Design?",
                mutableListOf("KitKat", "Lollipop", "Marshmallow", "Nougat"),
                "Lollipop"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "What is the primary IDE used for Android development?",
                mutableListOf("Visual Studio", "Xcode", "Eclipse", "Android Studio"),
                "Android Studio"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "What does the acronym AOSP stand for?",
                mutableListOf(
                    "Android Open Source Project",
                    "Android Operating System Project",
                    "Android Official Software Program",
                    "Android Open System Platform"
                ),
                "Android Open Source Project"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "Which company did Google acquire to develop Android?",
                mutableListOf("Nokia", "HTC", "Motorola", "Android Inc."),
                "Android Inc."
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "What year was the first Android device released?",
                mutableListOf("2005", "2007", "2008", "2010"),
                "2008"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "What is the name of the Android application package file format?",
                mutableListOf("IPA", "APK", "EXE", "JAR"),
                "APK"
            )
        )
        listQuestionModel.add(
            QuestionModel(
                "Which Android version is known as 'Oreo'?",
                mutableListOf("7.0", "8.0", "9.0", "10.0"),
                "8.0"
            )
        )

        quizModelList.add(
            QuizModel(
                "1",
                "programming",
                "All the basic programming",
                "10",
                listQuestionModel
            )
        )
        setupRecyclerView()
    }
}

//      binding.progressBar.visibility = View.VISIBLE88
//        FirebaseDatabase.getInstance().reference
//            .get()
//            .addOnSuccessListener { dataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    for (snapshot in dataSnapshot.children) {
//                        val quizModel = snapshot.getValue(QuizModel::class.java)
//                        if (quizModel != null) {
//                            quizModelList.add(quizModel)
//                        }
//                    }
//                }
//                setupRecyclerView()
//            }

//    }
//}



















