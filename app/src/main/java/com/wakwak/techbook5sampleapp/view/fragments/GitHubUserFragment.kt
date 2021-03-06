package com.wakwak.techbook5sampleapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wakwak.techbook5sampleapp.R
import com.wakwak.techbook5sampleapp.databinding.DialogUserSearchBinding
import com.wakwak.techbook5sampleapp.databinding.FragmentGitHubUserBinding
import com.wakwak.techbook5sampleapp.model.data.GitHubUser
import com.wakwak.techbook5sampleapp.presentation.binding_data.GitHubUserBindableData
import com.wakwak.techbook5sampleapp.presentation.presenters.GitHubUserPresenter
import com.wakwak.techbook5sampleapp.view.epoxy_controllers.GitHubUserController
import com.wakwak.techbook5sampleapp.view.view.IGitHubUserView
import org.koin.android.ext.android.inject

class GitHubUserFragment : Fragment(), IGitHubUserView {

    companion object {
        fun newInstance(): GitHubUserFragment = GitHubUserFragment()
    }

    private val presenter: GitHubUserPresenter by inject()
    private lateinit var binding: FragmentGitHubUserBinding
    private lateinit var fetchedUserController: GitHubUserController

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter.attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentGitHubUserBinding.inflate(inflater, container, false)
        binding.toolbar.inflateMenu(R.menu.menu_git_hub_user)
        binding.toolbar.setOnMenuItemClickListener { presenter.onClickMenuItem(it.itemId) }
        val toggle = ActionBarDrawerToggle(
                requireActivity(),
                binding.drawerLayout,
                binding.toolbar,
                R.string.drawer_open,
                R.string.drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.drawerItems.layoutManager = LinearLayoutManager(requireContext())
        fetchedUserController = GitHubUserController(presenter)
        binding.drawerItems.adapter = fetchedUserController.adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setUpDrawerItems()
        presenter.showGitHubUserByUserName("wakwak3125")
    }

    override fun bind(bindable: GitHubUserBindableData) {
        binding.bindable = bindable
    }

    override fun setUpDrawerItems(users: List<GitHubUser>) {
        fetchedUserController.setData(users)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    override fun showSearchDialog() {
        val dialogBinding = DialogUserSearchBinding.inflate(LayoutInflater.from(requireContext()))
        AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_title_search_git_hub_user))
                .setView(dialogBinding.root)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    presenter.showGitHubUserByUserName(dialogBinding.editText.text?.toString())
                    dialog.dismiss()
                }
                .show()
    }
}
