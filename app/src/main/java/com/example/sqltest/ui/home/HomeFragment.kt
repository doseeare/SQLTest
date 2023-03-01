package com.example.sqltest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sqltest.MainActivity
import com.example.sqltest.R
import com.example.sqltest.adapter.LoaderAdapter
import com.example.sqltest.adapter.NameAdapter
import com.example.sqltest.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViews()
        return binding.root
    }

    private fun initViews() {
        val adapter = NameAdapter(true) {
            val bundle = Bundle()
            bundle.putSerializable("bundle_key", it)
            findNavController().navigate(R.id.home_to_detail, bundle)
        }
        binding.rv.adapter = adapter.withLoadStateFooter(LoaderAdapter())
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.data.collectLatest {
                adapter.submitData(it)
            }
        }
    }

}