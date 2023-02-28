package it.macgood.itdiving

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.macgood.itdiving.databinding.ActivityMainBinding
import it.macgood.itdiving.model.ClientsListener
import it.macgood.itdiving.model.ClientsService
import it.macgood.itdiving.screens.ClientsAdapter
import it.macgood.itdiving.screens.ContactsBottomSheetFragment

//todo
// 4) arrow
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ClientsAdapter

    private val clientsService: ClientsService
        get() = (applicationContext as App).clientsService

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        adapter = ClientsAdapter()

        with(binding) {
            clientsRecyclerView.adapter = adapter

            tilesButton.setOnClickListener{
                clientsService.switchClients()
            }

            val clients = clientsService.getClients()

            membersCounterText.text = clients.size.toString()

            val helloAlert = MaterialAlertDialogBuilder(this@MainActivity)
                .setMessage("привет")
                .create()

            helloButton.setOnClickListener { helloAlert.show() }

            membersButton.setOnClickListener {
                val contacts = ContactsBottomSheetFragment()
                contacts.show(supportFragmentManager, "Contacts")
            }

            var micIsEnabled = clients[0].micIsEnabled

            var currentClient = clients.first { it.name == "You" }


            micButton.setOnClickListener {

                if (micIsEnabled) {
                    fadeInAnimation(it)
                    switchingButtons(
                        it as ImageButton,
                        resources.getDrawable(R.drawable.mic_off_black),
                        resources.getDrawable(R.drawable.button_background)
                    )
                    micIsEnabled = false
                    clientsService.changeMicState(currentClient, micIsEnabled)

                } else {
                    fadeInAnimation(it)
                    switchingButtons(
                        it as ImageButton,
                        resources.getDrawable(R.drawable.mic_white),
                        resources.getDrawable(R.drawable.enable_button_circle_background)
                    )
                    micIsEnabled = true
                    clientsService.changeMicState(currentClient, micIsEnabled)
                }
            }

            var cameraIsEnabled = clients[0].cameraIsEnabled

            cameraButton.setOnClickListener {
                if (cameraIsEnabled) {
                    fadeInAnimation(it)
                    switchingButtons(
                        it as ImageButton,
                        resources.getDrawable(R.drawable.camera_outlined_off_black),
                        resources.getDrawable(R.drawable.button_background)
                    )
                    cameraIsEnabled = false
                    clientsService.changeCameraState(currentClient, cameraIsEnabled)
                } else {
                    fadeInAnimation(it)
                    switchingButtons(
                        it as ImageButton,
                        resources.getDrawable(R.drawable.camera_outlined_96dp),
                        resources.getDrawable(R.drawable.enable_button_circle_background)
                    )
                    cameraIsEnabled = true
                    clientsService.changeCameraState(currentClient, cameraIsEnabled)
                }
            }

            messagesButton.setOnClickListener{
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
                startActivity(intent)

            }

            val itemAnimator = binding.clientsRecyclerView.itemAnimator

            if (itemAnimator is DefaultItemAnimator) {
                itemAnimator.supportsChangeAnimations = false
            }

            endCallButton.setOnClickListener {finishAndRemoveTask();}

            clientsService.addListener(clientsListener)
        }

    }

    private fun switchingButtons(
        button: ImageButton,
        icon: Drawable,
        background: Drawable
    ) {
        button.setImageDrawable(icon)
        button.background = background
    }

    private fun fadeInAnimation(view: View) {
        val fadeOut: Animation =
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
        view.startAnimation(fadeOut)
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        clientsService.removeListener(clientsListener)
    }

    private val clientsListener: ClientsListener = {
        adapter.clients = it
    }
}