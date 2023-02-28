package it.macgood.itdiving.model

import it.macgood.itdiving.R
import java.util.*
import kotlin.collections.ArrayList

typealias ClientsListener = (users: List<Client>) -> Unit

class ClientsService {
    private var clients = mutableListOf<Client>()
    private val listeners = mutableSetOf<ClientsListener>()

    init {
        clients = mutableListOf(
            Client(
                1,
                "You",
                R.drawable.first_caller,
                cameraIsEnabled = false,
                micIsEnabled = false
            ),

            Client(
                2,
                "Venera Phone Long Contact For Test And Test Again And Test Again",
                R.drawable.second_caller,
                cameraIsEnabled = false,
                micIsEnabled = false
            )
        )
    }

    fun getClients(): List<Client> {
        return clients
    }

    fun switchClients() {
        clients = ArrayList(clients)
        Collections.swap(clients, 0, 1)
        notifyChanges()
    }

    fun changeMicState(client: Client, enabled: Boolean) {
        val index = clients.indexOfFirst { it.id == client.id }
        var current = clients[index].copy()
        current.micIsEnabled = enabled
        clients = ArrayList(clients)
        clients[index] = current
        notifyChanges()
    }

    fun changeCameraState(client: Client, enabled: Boolean) {
        val index = clients.indexOfFirst { it.id == client.id }
        var current = clients[index].copy()
        current.cameraIsEnabled = enabled
        clients = ArrayList(clients)
        clients[index] = current
    }

    fun addListener(listener: ClientsListener) {
        listeners.add(listener)
        listener.invoke(clients)
    }

    fun removeListener(listener: ClientsListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(clients) }
    }

}