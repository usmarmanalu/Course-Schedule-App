package com.dicoding.courseschedule.data

import android.content.*
import androidx.lifecycle.*
import androidx.paging.*
import com.dicoding.courseschedule.util.*
import com.dicoding.courseschedule.util.QueryUtil.nearestQuery
import com.dicoding.courseschedule.util.QueryUtil.sortedQuery
import java.util.*

//TODO 4 : Implement repository with appropriate dao
class DataRepository(private val dao: CourseDao) {

    fun getNearestSchedule(queryType: QueryType): LiveData<Course?> =
        dao.getNearestSchedule(nearestQuery(queryType))

    fun getAllCourse(sortType: SortType): LiveData<PagedList<Course>> =
        LivePagedListBuilder(dao.getAll(sortedQuery(sortType)), PAGE_SIZE).build()

    fun getCourse(id: Int): LiveData<Course> =
        dao.getCourse(id)

    fun getTodaySchedule(): List<Course> {
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return dao.getTodaySchedule(dayOfWeek)
    }

    fun insert(course: Course) = executeThread {
        dao.insert(course)

    }

    fun delete(course: Course) = executeThread {
        dao.delete(course)

    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        private const val PAGE_SIZE = 10

        fun getInstance(context: Context): DataRepository? {
            return instance ?: synchronized(DataRepository::class.java) {
                if (instance == null) {
                    val database = CourseDatabase.getInstance(context)
                    instance = DataRepository(database.courseDao())
                }
                return instance
            }
        }
    }
}