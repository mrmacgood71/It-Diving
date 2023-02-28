package it.macgood.itdiving.screens

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.macgood.itdiving.R
import it.macgood.itdiving.databinding.ItemClientBinding
import it.macgood.itdiving.model.Client

class ClientsDiffCallback(
    private val oldList: List<Client>,
    private val newList: List<Client>

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldClient = oldList[oldItemPosition]
        val newClient = newList[newItemPosition]

        return oldClient.id == newClient.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldClient = oldList[oldItemPosition]
        val newClient = newList[newItemPosition]
        return oldClient == newClient
    }
}

class ClientsAdapter : RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {

    var clients: List<Client> = emptyList()
        set(newValue) {
            val diffCallback = ClientsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClientBinding.inflate(inflater, parent, false)
        return ClientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val client = clients[position]

        with(holder.binding) {

            setBackgroundColor(position, holder.binding, root.resources.getDrawable(client.image))

            clientImage.setImageResource(client.image)
            nameText.text = client.name
            if (!client.micIsEnabled) {
                nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    root.resources.getDrawable(R.drawable.mic_black_16dp),
                    null
                )
            } else {
                nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    root.resources.getDrawable(R.drawable.mic_off_black_16dp),
                    null
                )
            }
        }
    }

    override fun getItemCount(): Int = clients.size

    class ClientsViewHolder(
        val binding: ItemClientBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private fun setBackgroundColor(
        position: Int,
        binding: ItemClientBinding,
        drawable: Drawable) {
        with(binding) {
            Palette.from(drawable.toBitmap())
                .maximumColorCount(64)
                .generate { palette ->
                    palette?.let {

                    val rightColor = it.lightVibrantSwatch?.rgb ?: it.darkVibrantSwatch?.rgb.let { 0 }
                    val leftColor = it.lightMutedSwatch?.rgb ?: it.vibrantSwatch?.rgb.let { 0 }

                    setColoredText(position, leftColor, rightColor)

                    val gradientDrawable = GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        intArrayOf(leftColor, rightColor)
                    )
                    cardLayout.background = gradientDrawable
                }
        }
    }
}

    private fun ItemClientBinding.setColoredText(
        position: Int,
        leftColor: Int,
        rightColor: Int
    ) {
        if (isColorDark(leftColor) && isColorDark(rightColor)) {
            nameText.setTextColor(Color.WHITE)
            nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                root.resources.getDrawable(R.drawable.mic_black_16dp),
                null
            )
        } else if (isColorDark(leftColor)) {
            nameText.setTextColor(Color.BLACK)

            if (clients[position].micIsEnabled) {
                nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    root.resources.getDrawable(R.drawable.mic_black_16dp),
                    null
                )
            } else {
                nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    root.resources.getDrawable(R.drawable.mic_off_black_16dp),
                    null
                )
            }

        } else if (isColorDark(rightColor)) {
            nameText.setTextColor(Color.BLACK)
            if (clients[position].micIsEnabled) {
                nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    root.resources.getDrawable(R.drawable.mic_black_16dp),
                    null
                )
            } else {
                nameText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    root.resources.getDrawable(R.drawable.mic_off_black_16dp),
                    null
                )
            }

        } else {
            nameText.setTextColor(Color.WHITE)
        }
    }

    fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.3
    }
}
