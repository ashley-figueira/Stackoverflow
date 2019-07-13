package com.ashleyfigueira.stackoverflow.users

import android.view.View
import androidx.core.content.ContextCompat
import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.stackoverflow.R
import com.ashleyfigueira.stackoverflow.common.load
import com.ashleyfigueira.stackoverflow.common.visibleUnless
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_user.view.*

data class UserItem(val userEntity: UserEntity) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) = viewHolder.itemView.bind()

    override fun getLayout(): Int = R.layout.item_user

    override fun getId(): Long = userEntity.id

    private fun View.bind() {
        userName.text = userEntity.name
        reputation.text = userEntity.reputation.toString()
        heartImage.visibleUnless(userEntity.isFollowing)
        userProfileImage.load(userEntity.profileImageUrl, RequestOptions.circleCropTransform())

        val white = ContextCompat.getColor(context, android.R.color.white)
        val grey = ContextCompat.getColor(context, R.color.gray)

        container.setCardBackgroundColor(if (userEntity.isBlocked) grey else white)
        container.isEnabled = userEntity.isBlocked.not()
    }
}