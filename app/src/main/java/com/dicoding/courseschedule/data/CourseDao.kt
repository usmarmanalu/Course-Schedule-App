package com.dicoding.courseschedule.data

import androidx.lifecycle.*
import androidx.paging.*
import androidx.room.*
import androidx.sqlite.db.*

//TODO 2 : Define data access object (DAO)
@Dao
interface CourseDao {

    @RawQuery(observedEntities = [Course::class])
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>

    @RawQuery(observedEntities = [Course::class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>

    @Query("SELECT * FROM course WHERE id = :id")
    fun getCourse(id: Int): LiveData<Course>

    @Query("SELECT * FROM course WHERE day = :day")
    fun getTodaySchedule(day: Int): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(course: Course)

    @Delete
    fun delete(course: Course)
}