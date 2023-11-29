package com.dicoding.courseschedule.paging

import android.view.*
import androidx.paging.*
import androidx.recyclerview.widget.*
import com.dicoding.courseschedule.*
import com.dicoding.courseschedule.data.*

//TODO 6 : Implement Method for PagedListAdapter
class CourseAdapter(private val clickListener: (Course) -> Unit) :
    PagedListAdapter<Course, CourseViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(item)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position) as Course
        holder.bind(course, clickListener)
    }
}