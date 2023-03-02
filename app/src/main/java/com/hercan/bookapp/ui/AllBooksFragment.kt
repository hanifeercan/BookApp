package com.hercan.bookapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.hercan.bookapp.databinding.FragmentAllBooksBinding
import com.hercan.bookapp.paging.BookPagingAdapter
import com.hercan.bookapp.paging.LoaderAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AllBooksFragment : Fragment() {
    private var _binding: FragmentAllBooksBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: BookPagingAdapter
    lateinit var bookViewModel: AllBooksViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentAllBooksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter=BookPagingAdapter()
        bookViewModel= ViewModelProvider(requireActivity()).get(AllBooksViewModel::class.java)
        binding.bookRv.layoutManager= LinearLayoutManager(activity)
        binding.bookRv.setHasFixedSize(true)
        binding.bookRv.adapter=adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )
        lifecycleScope.launch {
            bookViewModel.list.collectLatest {
                adapter.submitData(lifecycle,it)
            }
        }
        adapter.addLoadStateListener {
            it.refresh.let {
                binding.progressBar.isVisible = it is LoadState.Loading
                binding.bookRv.isVisible= it is LoadState.NotLoading
            }
        }
        adapter.setClickListener {
            val action = AllBooksFragmentDirections.actionAllBooksFragmentToBookInfoFragment(it)
            findNavController(requireView()).navigate(action)
        }
    }
}