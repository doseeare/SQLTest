package com.example.sqltest.ui.detail

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
import com.example.sqltest.databinding.FragmentDetailBinding
import com.example.sqltest.model.NameModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private val binding: FragmentDetailBinding by lazy {
        FragmentDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
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
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val nameModel = requireArguments().getSerializable("bundle_key") as NameModel
        binding.itemId.text = "id = ${nameModel.id}"
        binding.itemName.text = "name = ${nameModel.name}"
        binding.itemParentId.text = "parentId = ${nameModel.parentId}"
        binding.itemChildCount.text = "childCount = ${nameModel.childCounts}"

        val adapter = NameAdapter(false) {
            val bundle = Bundle()
            bundle.putSerializable("bundle_key", it)
            findNavController().navigate(R.id.navigation_detail, bundle)
        }
        binding.rv.adapter = adapter.withLoadStateFooter(LoaderAdapter())
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getData(nameModel.id).collectLatest {
                adapter.submitData(it)
            }
        }
    }

}