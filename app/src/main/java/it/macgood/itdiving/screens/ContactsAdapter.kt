package it.macgood.itdiving.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.macgood.itdiving.R
import it.macgood.itdiving.databinding.ItemClientDescriptionBinding
import it.macgood.itdiving.model.Client

class ContactsAdapter(
    var clients: List<Client>
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClientDescriptionBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val client = clients[position]

        with(holder.binding) {
            photoImageView.setImageResource(client.image)
            usernameText.text = client.name

            if (clients[position].micIsEnabled) {
                micIcon.setImageResource(R.drawable.mic_white)
            } else {
                micIcon.setImageResource(R.drawable.mic_off_white)
            }

            if (clients[position].cameraIsEnabled) {
                cameraIcon.setImageResource(R.drawable.camera_outlined_96dp)
            } else {
                cameraIcon.setImageResource(R.drawable.camera_outlined_off_white)
            }
        }


    }

    override fun getItemCount(): Int = clients.size

    class ContactsViewHolder(
        val binding: ItemClientDescriptionBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    )
}