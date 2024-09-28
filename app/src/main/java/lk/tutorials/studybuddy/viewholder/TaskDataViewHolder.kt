package lk.tutorials.studybuddy.viewholder

import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lk.tutorials.studybuddy.R

class TaskDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val taskNameTextView: TextView = itemView.findViewById(R.id.task_name)
    val taskDescriptionTextView:TextView = itemView.findViewById(R.id.task_description)
    val taskPriorityTextView:TextView = itemView.findViewById(R.id.task_priority)
    val taskDeadlineTextView:TextView = itemView.findViewById(R.id.task_deadline)
    val taskCategoryTextView:TextView = itemView.findViewById(R.id.task_category)
    val remainderSwitch: Switch = itemView.findViewById(R.id.remainder_switch)
}