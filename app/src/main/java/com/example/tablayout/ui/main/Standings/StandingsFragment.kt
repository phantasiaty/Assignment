package com.example.tablayout.ui.main.Standings

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.tablayout.R
import kotlinx.android.synthetic.main.standings_fragment.*


class StandingsFragment : Fragment(),SensorEventListener {

    private lateinit var standingsViewModel: StandingsViewModel

    var running = false
    var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        standingsViewModel=ViewModelProviders.of(this).get(StandingsViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER)?:1)
        }
    }


    private lateinit var viewModel: StandingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.standings_fragment, container, false)
//        val textView: TextView = root.findViewById(R.id.section_label)
//        standingsViewModel.text.observe(this, Observer<String> {
//            textView.text = it
//        })

      // sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager


        return root    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(StandingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume(){
        super.onResume()
        running = true
        var stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(context,"No Step Counter Sensor!",Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause(){
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


    override fun onSensorChanged(event: SensorEvent) {
        if(running){
            stepsValue.setText("" + event.values[0])
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): StandingsFragment {
            return StandingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}
