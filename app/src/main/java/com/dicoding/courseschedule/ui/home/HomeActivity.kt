package com.dicoding.courseschedule.ui.home

import android.content.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.lifecycle.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.*
import com.dicoding.courseschedule.ui.add.*
import com.dicoding.courseschedule.ui.list.*
import com.dicoding.courseschedule.ui.setting.*
import com.dicoding.courseschedule.util.*

//TODO 15 : Write UI test to validate when user tap Add Course (+) Menu, the AddCourseActivity is displayed
class HomeActivity : AppCompatActivity() {

    private lateinit var  viewModel : HomeViewModel
    private var queryType = QueryType.CURRENT_DAY

    //TODO 5 : Show nearest schedule in CardHomeView and implement menu action
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = resources.getString(R.string.today_schedule)

        val factory = ViewModelFactory.createViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        viewModel.getNearestSchedule().observe(this) {
            showNearestSchedule(it)
        }
    }

    private fun showNearestSchedule(course: Course?) {
        checkQueryType(course)
        course?.apply {
            val dayName = DayName.getByNumber(day)
            val time = String.format(getString(R.string.time_format), dayName, startTime, endTime)
            val remainingTime = timeDifference(day, startTime)

            val cardHome = findViewById<CardHomeView>(R.id.view_home)
            cardHome.apply {
                setCourseName(courseName)
                setTime(time)
                setRemainingTime(remainingTime)
                setLecturer(lecturer)
                setNote(note)
            }
        }

        findViewById<TextView>(R.id.tv_empty_home).visibility =
            if (course == null) View.VISIBLE else View.GONE
    }

    private fun checkQueryType(course: Course?) {
        if (course == null) {
            val newQueryType: QueryType = when (queryType) {
                QueryType.CURRENT_DAY -> QueryType.NEXT_DAY
                QueryType.NEXT_DAY -> QueryType.PAST_DAY
                else -> QueryType.CURRENT_DAY
            }
            viewModel.setQueryType(newQueryType)
            queryType = newQueryType
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.action_add -> Intent(this, AddCourseActivity::class.java)
            R.id.action_list -> Intent(this, ListActivity::class.java)
            R.id.action_settings -> Intent(this, SettingsActivity::class.java)
            else -> null
        } ?: return super.onOptionsItemSelected(item)

        startActivity(intent)
        return true
    }
}