package lk.tutorials.studybuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import lk.tutorials.studybuddy.ui.tasks.AddTaskActivity
import lk.tutorials.studybuddy.ui.tasks.TaskListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var addTask:Button
        lateinit var btnTaskList:Button

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        addTask = findViewById(R.id.addTask)
        btnTaskList = findViewById(R.id.btnTaskList)

        addTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        btnTaskList.setOnClickListener {
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
        }
    }
}