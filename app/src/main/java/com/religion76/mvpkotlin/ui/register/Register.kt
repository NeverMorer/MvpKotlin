package com.religion76.mvpkotlin.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.base.LazyFragment
import com.religion76.mvpkotlin.base.showMessage
import com.religion76.mvpkotlin.utils.StringUtils
import com.religion76.mvpkotlin.utils.hideKeyBoard
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by SunChao on 2019-08-19.
 */
class Register : LazyFragment() {

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onLazyInit() {

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }

        btnCreate.setOnClickListener {
            val enteredName = etName.text?.trim()?.toString()
            if (enteredName.isNullOrBlank()) return@setOnClickListener

            etName.hideKeyBoard()

            userViewModel.createUser(enteredName).observe(this, Observer { result ->

                Timber.d(result.toString())

                if (result.isLoading) {
                    pbLoading.show()
                } else {
                    pbLoading.hide()
                }

                if (result.isSuccessful) {
                    findNavController().popBackStack()
                    showMessage(String.format(StringUtils.getString(R.string.welcome_format), enteredName))
                } else if (result.isErroneous) {
                    showMessage(result.exception?.message ?: "unknown error")
                }
            })
        }
    }

}