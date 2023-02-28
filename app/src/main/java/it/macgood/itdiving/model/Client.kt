package it.macgood.itdiving.model

data class Client(
    var id: Long,
    var name: String,
    var image: Int,
    var cameraIsEnabled: Boolean = false,
    var micIsEnabled: Boolean = false,
)
