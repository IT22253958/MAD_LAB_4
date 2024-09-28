package lk.tutorials.studybuddy.ui.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lk.tutorials.studybuddy.R
import lk.tutorials.studybuddy.data.db.Task
import lk.tutorials.studybuddy.ui.viewmodel.TaskViewModel
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {

    // Declare ViewModel
    private lateinit var taskViewModel: TaskViewModel

    // Declare Views
    private lateinit var taskName: EditText
    private lateinit var taskDescription: EditText
    private lateinit var taskPriority: String
    private lateinit var taskCategory: String
    private lateinit var taskDeadline: String
    private var remainderEnabled: Boolean = false

    private lateinit var btnSaveTask: Button
    private lateinit var btnCancelTask: Button
    private lateinit var deadlineEditText: EditText
    private lateinit var remainderToggle: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Initialize UI components
        taskName = findViewById(R.id.taskName)
        taskDescription = findViewById(R.id.taskDescription)
        btnSaveTask = findViewById(R.id.btnSaveTask)
        btnCancelTask = findViewById(R.id.btnCancelTask)
        deadlineEditText = findViewById(R.id.taskDeadLine)
        remainderToggle = findViewById(R.id.remainderToggle)

        // Initialize priority spinner
        val prioritySpinner = findViewById<Spinner>(R.id.task_priority_spinner)
        setupSpinner(prioritySpinner, R.array.priority_array) { selectedItem ->
            taskPriority = selectedItem
        }

        // Initialize category spinner
        val categorySpinner = findViewById<Spinner>(R.id.task_category_spinner)
        setupSpinner(categorySpinner, R.array.task_category) { selectedItem ->
            taskCategory = selectedItem
        }

        // Set click listener for deadline selection
        setupDeadlinePicker()

        // Handle switch state changes
        remainderToggle.setOnCheckedChangeListener { _, isChecked ->
            remainderEnabled = isChecked
        }

        // Handle save button click to insert data into database
        btnSaveTask.setOnClickListener {
            saveTaskToDatabase()
        }

        // Handle cancel button click to close the activity
        btnCancelTask.setOnClickListener {
            finish() // Close the activity and return to the previous activity
        }
    }

    // Method to setup spinners
    private fun setupSpinner(spinner: Spinner, arrayResId: Int, onItemSelected: (String) -> Unit) {
        val adapter = ArrayAdapter.createFromResource(
            this, arrayResId, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Handle item selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                onItemSelected(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    // Method to setup DatePicker dialog for deadline input
    private fun setupDeadlinePicker() {
        deadlineEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Show datePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    // Date selected, update EditText with selected date
                    taskDeadline = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                    deadlineEditText.setText(taskDeadline)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

    // Method to save data to the database
    private fun saveTaskToDatabase() {
        // Gather data from UI components
        val name = taskName.text.toString().trim()
        val description = taskDescription.text.toString().trim()
        val priority = taskPriority
        val category = taskCategory
        val deadlineDate = taskDeadline
        val reminder = remainderEnabled

        // Validate inputs (optional, add your validation logic here)
        if (name.isNotEmpty() && description.isNotEmpty()) {
            val task = Task(
                name = name,
                description = description,
                priority = priority,
                category = category,
                deadline = deadlineDate,
                isReminderSet = reminder
            )

            // Use Coroutine to insert task into database on the IO thread
            CoroutineScope(Dispatchers.IO).launch {
                taskViewModel.insert(task)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddTaskActivity,
                        "Task saved successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish() // Close the activity after saving the task
                }
            }
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
