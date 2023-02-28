package it.macgood.itdiving.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import it.macgood.itdiving.App
import it.macgood.itdiving.R
import it.macgood.itdiving.databinding.FragmentContactsBottomSheetBinding
import it.macgood.itdiving.model.ClientsService

class ContactsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentContactsBottomSheetBinding
    override fun getTheme() = R.style.AppBottomSheetDialogTheme
    private lateinit var adapter: ContactsAdapter
    private val clientsService: ClientsService
        get() = (activity?.applicationContext as App).clientsService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactsBottomSheetBinding.inflate(inflater, container, false)

        adapter = ContactsAdapter(clientsService.getClients())

        binding.recyclerView.adapter = adapter

        return binding.root
    }

}