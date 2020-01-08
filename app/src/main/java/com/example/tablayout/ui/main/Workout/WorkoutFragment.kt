package com.example.tablayout.ui.main.Workout

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tablayout.R
import com.example.tablayout.ui.main.WorkoutContent.WorkoutContent
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout.view.*


class WorkoutFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference
    private var iv:ImageView? =null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        workoutViewModel=ViewModelProviders.of(this).get(WorkoutViewModel::class.java).apply {
            //setIndex(arguments?.getInt(ARG_SECTION_NUMBER)?:1)


        }
    }


    private lateinit var viewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.workout_fragment, container, false)

        mDatabase = FirebaseDatabase.getInstance().getReference("Workout")
        mRecyclerView = root.findViewById(R.id.listView)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(context))
        //mButton = root.findViewById(R.id.exerciseItem)
        iv = root.findViewById(R.id.workoutImg)
        logRecyclerView()

        return root    }



//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(WorkoutViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    private fun logRecyclerView(){

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Workout, WorkoutViewHolder>(

            Workout::class.java,
            R.layout.list_layout,
            WorkoutViewHolder::class.java,
            mDatabase

        ){
            override fun populateViewHolder(viewHolder: WorkoutViewHolder?, model: Workout?, position: Int) {

                viewHolder?.itemView?.workoutDesc?.text=model?.desc
                viewHolder?.itemView?.section_id?.text=model?.id
                Picasso.get().load(model?.image).into(viewHolder?.itemView?.workoutImg)

            }
        }
        mRecyclerView.adapter = FirebaseRecyclerAdapter

    }


    class WorkoutViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!){

        init {

            itemView!!.setOnClickListener {


                val intent = Intent(itemView.context, WorkoutContent::class.java)
                intent.putExtra("desc",itemView.workoutDesc.text)
                intent.putExtra("id",itemView.section_id.text)
                itemView.context.startActivity(intent)

            }


        }
    }


//        companion object {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private const val ARG_SECTION_NUMBER = "section_number"
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//
//
//        @JvmStatic
//        fun newInstance(sectionNumber: Int): WorkoutFragment {
//            return WorkoutFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                }
//            }
//        }
//    }

}








