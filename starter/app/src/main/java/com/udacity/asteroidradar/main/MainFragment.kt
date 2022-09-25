package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.asteroidradar.AsteroidFilter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {



    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, Factory(activity.application)).get(MainViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        //////////////////// set recycler view adapter
        val adapter=AsteroidsAdapter(AsteroidsListener {
            asteroid->view?.findNavController()?.navigate(MainFragmentDirections.actionShowDetail(asteroid))

        })
        binding.asteroidRecycler.adapter=adapter


//////////////////////////////////////////////////////
viewModel.connection.observe(viewLifecycleOwner, Observer {
    check-> if (check)
    {
    Toast.makeText(context,"Connection error connect to network and try again ",Toast.LENGTH_SHORT).show()
    viewModel.connecctivity()
    }

})
        ////////////////////////////


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selectedDateItem= when (item.itemId)
        {
            R.id.show_week_menu->AsteroidFilter.SHOW_WEEK
            R.id.show_saved_menu->AsteroidFilter.SHOW_SAVED
            else->AsteroidFilter.SHOW_TODAY
        }
        viewModel.applyFilter(selectedDateItem)
        return true
    }
}
