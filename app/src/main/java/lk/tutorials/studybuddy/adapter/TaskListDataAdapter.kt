package lk.tutorials.studybuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lk.tutorials.studybuddy.R
import lk.tutorials.studybuddy.data.db.Task
import lk.tutorials.studybuddy.viewholder.TaskDataViewHolder

class TaskListDataAdapter(private var taskList:List<Task>) : RecyclerView.Adapter<TaskDataViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDataViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(R.layout.task_list_layout,parent,false)
        return TaskDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskDataViewHolder, position: Int) {

        val task = taskList[position] //Get the task for the current position

        //Bind the data to the views in the viewHolder
        holder.taskNameTextView.text = task.name
        holder.taskDescriptionTextView.text = task.description
        holder.taskPriorityTextView.text = "Priority: ${task.priority}"
        holder.taskDeadlineTextView.text = "Deadline: ${task.deadline}"  // Convert date to string as needed
        holder.taskCategoryTextView.text = "Category: ${task.category}"

        //set the reminder switch based on the data
        holder.remainderSwitch.isChecked = task.isReminderSet

        // Optionally: set a listener to handle reminder switch toggling
        holder.remainderSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Update the task's reminder status or trigger further actions
            task.isReminderSet = isChecked
        }
    }

    fun updateTaskList(newTaskList: List<Task>) {
        val oldTaskList = taskList
        taskList = newTaskList

        // If you're sure of the changes, you can do a more efficient granular update
        when {
            // If items were added
            taskList.size > oldTaskList.size -> notifyItemRangeInserted(oldTaskList.size, taskList.size - oldTaskList.size)

            // If items were removed
            taskList.size < oldTaskList.size -> notifyItemRangeRemoved(taskList.size, oldTaskList.size - taskList.size)

            // Otherwise, just notify item changes (if needed, a more advanced diff check can be used)
            else -> notifyDataSetChanged()  // Use diff calculations for more granular updates
        }
    }

}