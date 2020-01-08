package com.example.tablayout.ui.main.WorkoutContent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablayout.R
import com.example.tablayout.Timer

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.exercise.view.*
import kotlinx.android.synthetic.main.list_layout.view.*

class WorkoutContent : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_content)

        //Log.d("aa", intent.getStringExtra("id"))
        mDatabase = FirebaseDatabase.getInstance().getReference("Workout").child(intent.getStringExtra("id")).child("workoutDetail")

        mRecyclerView = findViewById(R.id.listWorkout)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(GridLayoutManager(this,2))

        logRecyclerView()
    }

    private fun logRecyclerView(){

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Exercise, ExerciseViewHolder>(

            Exercise::class.java,
            R.layout.exercise,
            ExerciseViewHolder::class.java,
            mDatabase

        ){
            override fun populateViewHolder(viewHolder: ExerciseViewHolder?, model: Exercise?, position: Int) {

                viewHolder?.itemView?.exercise_id?.text=model?.eID
                viewHolder?.itemView?.exerciseDesc?.text=model?.exercise
                viewHolder?.itemView?.link?.text=model?.link


            }

        }
        mRecyclerView.adapter = FirebaseRecyclerAdapter

    }

    class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        init {

            itemView!!.setOnClickListener {


                val intent = Intent(itemView.context, Timer::class.java)
                intent.putExtra("link",itemView.link.text)
                itemView.context.startActivity(intent)

            }

        }


        }

}
