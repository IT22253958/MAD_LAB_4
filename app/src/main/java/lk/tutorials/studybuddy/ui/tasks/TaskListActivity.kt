package lk.tutorials.studybuddy.ui.tasks

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lk.tutorials.studybuddy.R
import lk.tutorials.studybuddy.adapter.TaskListDataAdapter
import lk.tutorials.studybuddy.data.db.TaskDatabase

class TaskListActivity : AppCompatActivity() {

    private lateinit var adapter: TaskListDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_list)

        // Initialize RecyclerView
        val taskRvList = findViewById<RecyclerView>(R.id.taskListView)

        // Set up layout manager
        taskRvList.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        adapter = TaskListDataAdapter(emptyList()) // Pass an empty list initially
        taskRvList.adapter = adapter

        // Use lifecycleScope with Coroutines to fetch data in the background
        lifecycleScope.launch {
            // Switch to IO thread to fetch data
            val tasks = withContext(Dispatchers.IO) {
                TaskDatabase.getDatabase(applicationContext).taskDao().getAllTasks().value // Fetch tasks from DB
            }

            // Update the adapter on the main thread
            tasks?.let {
                adapter.updateTaskList(it)
            }
        }
    }
}
