package com.dicoding.mygithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: UserAdapter
    private lateinit var followerViewModel: FollowerViewModel

    companion object {
        var EXTRA_USER_NAME = "username"

        fun newInstance(username: String?): FollowerFragment {
            val mfragment = FollowerFragment()
            val mbundle = Bundle()
            mbundle.putString(EXTRA_USER_NAME, username)
            mfragment.arguments = mbundle
            return mfragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)

        showLoading(true)
        showRecyclerView()

        followerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)
        followerViewModel.getListFollower().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
        return binding.root
    }

    private fun showRecyclerView() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFragmentFollower.setHasFixedSize(true)
        binding.rvFragmentFollower.layoutManager = LinearLayoutManager(activity)
        binding.rvFragmentFollower.adapter = adapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(EXTRA_USER_NAME)
        followerViewModel.setListFollower(username)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFollower.visibility = View.VISIBLE
        } else {
            binding.progressBarFollower.visibility = View.GONE
        }
    }
}