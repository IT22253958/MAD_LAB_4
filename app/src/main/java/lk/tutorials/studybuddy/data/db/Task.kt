package lk.tutorials.studybuddy.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,                  // Unique ID for the task (auto-generated)
    val name: String,                 // Task Name
    val description: String,          // Task Description
    val priority: String,             // Task Priority (e.g., Low, Medium, High)
    val deadline: String,               // Task Deadline (Date object for better handling)
    val category: String,             // Task Category (e.g., Work, Personal)
    var isReminderSet: Boolean        // Whether the reminder switch is on or off
)
