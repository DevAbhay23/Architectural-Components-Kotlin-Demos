package com.learn.kotlin.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.learn.kotlin.BuildConfig.VERSION_CODE
import com.learn.kotlin.BuildConfig.VERSION_NAME
import com.learn.kotlin.R
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtAppVersion.text = String.format("App Version $VERSION_NAME ($VERSION_CODE)")
    }
}