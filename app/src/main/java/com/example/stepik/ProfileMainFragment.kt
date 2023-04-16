package com.example.stepik

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class ProfileMainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons(view)
    }

    private fun initButtons(view: View) {
        view.findViewById<TextView>(R.id.btn_share).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND).setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, "My Profile")
            )
        }

        view.findViewById<TextView>(R.id.btn_email).setOnClickListener {
            val email = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).putExtra(
                Intent.EXTRA_TEXT, "My profile!"
            )
            try {
                startActivity(email)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "Приложение эл.почты не найдено.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        view.findViewById<TextView>(R.id.btn_call).setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+77777777777")))
        }

        view.findViewById<TextView>(R.id.btn_camera).setOnClickListener {
            startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }
}