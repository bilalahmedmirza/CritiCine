package com.bilalmirza.criticine.activities.mainActivity.fragments.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.changePassword.ChangePasswordActivity
import com.bilalmirza.criticine.activities.logIn.LoginScreenActivity
import com.bilalmirza.criticine.databinding.FragmentProfileBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.user.User
import com.bilalmirza.criticine.utils.CustomDialogs
import com.bumptech.glide.Glide

class ProfileFragment : Fragment(R.layout.fragment_profile), ProfileFragmentView {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var myPresenter: ProfileFragmentPresenter
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.userImage.setImageURI(uri)
                myPresenter.saveProfilePhoto(uri, requireContext())
            } else {
                Toast.makeText(
                    requireContext(), "Please select an image.", Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        //  initialising the presenter
        myPresenter = ProfileFragmentPresenter(this)
        //  setting up clickListeners
        clickListeners()
        //  getting the user details
        myPresenter.getDetails()
    }

    @SuppressLint("InflateParams")
    private fun clickListeners() {
        binding.logOutBtn.setOnClickListener {
            myPresenter.logOut()
        }
        binding.editIcon.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.changeNameBtn.setOnClickListener {
            CustomDialogs.showEditNameDialog(requireContext(),
                binding.nameTV.text.toString(),
                object : ItemClickListener<String> {
                    override fun onItemClick(item: String, pos: Int, type: Int) {
                        myPresenter.updateName(item)
                    }
                })
        }
        binding.changePasswordBtn.setOnClickListener {
            Intent(requireContext(), ChangePasswordActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onLogOutSuccess(msg: String) {
        Toast.makeText(requireContext(), "LogOut: $msg", Toast.LENGTH_SHORT).show()
        Intent(requireContext(), LoginScreenActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onGetUserDetails(user: User) {
        binding.nameTV.text = user.name
        binding.userEmail.text = user.email
        Glide.with(requireContext()).load(user.profilePicture)
            .placeholder(R.drawable.dummy_profile_photo).into(binding.userImage)

        if (user.loginType == "manual") {
            binding.changeNameBtn.visibility = View.VISIBLE
            binding.changePasswordBtn.visibility = View.VISIBLE
            binding.editIcon.visibility = View.VISIBLE
        }
    }

    override fun onStartLoader() {
        binding.progressBar.visibility = View.VISIBLE
        binding.userImage.visibility = View.INVISIBLE
    }

    override fun onStopLoader() {
        binding.progressBar.visibility = View.GONE
        binding.userImage.visibility = View.VISIBLE
    }

    override fun onNameUpdateSuccess(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onNameUpdateFail(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}