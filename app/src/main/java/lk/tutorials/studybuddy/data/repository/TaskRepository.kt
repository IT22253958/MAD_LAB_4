package lk.tutorials.studybuddy.data.repository

import androidx.lifecycle.LiveData
import lk.tutorials.studybuddy.data.db.Task
import lk.tutorials.studybuddy.data.db.TaskDao

class TaskRepository(
    private val taskDao: TaskDao
) {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task:Task){
        taskDao.insert(task)
    }

    suspend fun update(task:Task){
        taskDao.update(task)
    }

    suspend fun delete(task:Task){
        taskDao.delete(task)
    }

}