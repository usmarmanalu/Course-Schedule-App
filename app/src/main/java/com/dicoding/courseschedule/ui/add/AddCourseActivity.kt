package com.dicoding.courseschedule.ui.add

import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.lifecycle.*
import com.dicoding.courseschedule.*
import com.dicoding.courseschedule.util.*
import com.google.android.material.textfield.*
import java.text.*
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private var startTime = ""
    private var endTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.createViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }

            R.id.action_insert -> {
                val courseName =
                    findViewById<TextInputEditText>(R.id.ed_course_name).text.toString()
                val spinnerDay = findViewById<Spinner>(R.id.spinner_day).selectedItem.toString()
                val spinnerDayNumber = getSpinnerDayNumber(spinnerDay)
                val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer).text.toString()
                val note = findViewById<TextInputEditText>(R.id.ed_note).text.toString()

                when {
                    courseName.isEmpty() -> false
                    spinnerDay.isEmpty() -> false
                    lecturer.isEmpty() -> false
                    spinnerDayNumber == 1 -> false
                    note.isEmpty() -> false
                    startTime.isEmpty() -> false
                    endTime.isEmpty() -> false

                    else -> {
                        viewModel.insertCourse(
                            courseName,
                            spinnerDayNumber,
                            startTime,
                            endTime,
                            lecturer, note
                        )
                        finish()
                        true
                    }
                }
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSpinnerDayNumber(day: String): Int {
        val days = resources.getStringArray(R.array.day)
        return days.indexOf(day)

    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            "start_time" -> {
                findViewById<TextView>(R.id.tv_start_time).text = timeFormat.format(calendar.time)
                startTime = timeFormat.format(calendar.time)
            }

            "end_time" -> {
                findViewById<TextView>(R.id.tv_end_time).text = timeFormat.format(calendar.time)
                endTime = timeFormat.format(calendar.time)
            }
        }
    }

    fun showTimePicker(view: View) {
        val tag = when (view.id) {
            R.id.ib_start_time -> "start_time"
            R.id.ib_end_time -> "end_time"
            else -> "default"
        }

        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}