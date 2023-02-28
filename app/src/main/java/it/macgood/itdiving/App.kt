package it.macgood.itdiving

import android.app.Application
import it.macgood.itdiving.model.ClientsService

class App : Application() {
    val clientsService = ClientsService()

}