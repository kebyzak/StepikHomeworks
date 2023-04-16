package com.example.stepik

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ProfileMainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons(view)
    }

    private fun initButtons(view: View) {
        val btnShare: TextView = view.findViewById(R.id.btn_share)
        btnShare.setOnClickListener {
            val send = Intent()
            send.action = Intent.ACTION_SEND
            send.putExtra(Intent.EXTRA_TEXT, "My Profile")
            send.type = "..."
            startActivity(send)
        }

        val btnEmail: TextView = view.findViewById(R.id.btn_email)
        btnEmail.setOnClickListener {
            val email = Intent()
            email.action = Intent.ACTION_SENDTO
            email.data = Uri.parse("mailto:")
            email.putExtra(Intent.EXTRA_TEXT, "My profile!")
            startActivity(email)
        }

        val btnCall: TextView = view.findViewById(R.id.btn_call)
        btnCall.setOnClickListener {
            val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+77777777777"))
            startActivity(call)
        }

        val btnCamera: TextView = view.findViewById(R.id.btn_camera)
        btnCamera.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(camera)
        }

    }
}