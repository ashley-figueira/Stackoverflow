package com.ashleyfigueira.data.users.remote

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("has_more") val has_more: Boolean?,
    @SerializedName("items") val items: List<Item?>?,
    @SerializedName("quota_max") val quota_max: Int?,
    @SerializedName("quota_remaining") val quota_remaining: Int?
) {
    data class Item(
        @SerializedName("accept_rate") val accept_rate: Int?,
        @SerializedName("account_id") val account_id: Int?,
        @SerializedName("badge_counts") val badge_counts: BadgeCounts?,
        @SerializedName("creation_date") val creation_date: Int?,
        @SerializedName("display_name") val display_name: String?,
        @SerializedName("is_employee") val is_employee: Boolean?,
        @SerializedName("last_access_date") val last_access_date: Int?,
        @SerializedName("last_modified_date") val last_modified_date: Int?,
        @SerializedName("link") val link: String?,
        @SerializedName("location") val location: String?,
        @SerializedName("profile_image") val profile_image: String?,
        @SerializedName("reputation") val reputation: Int?,
        @SerializedName("reputation_change_day") val reputation_change_day: Int?,
        @SerializedName("reputation_change_month") val reputation_change_month: Int?,
        @SerializedName("reputation_change_quarter") val reputation_change_quarter: Int?,
        @SerializedName("reputation_change_week") val reputation_change_week: Int?,
        @SerializedName("reputation_change_year") val reputation_change_year: Int?,
        @SerializedName("user_id") val user_id: Int?,
        @SerializedName("user_type") val user_type: String?,
        @SerializedName("website_url") val website_url: String?
    ) {
        data class BadgeCounts(
            @SerializedName("bronze") val bronze: Int?,
            @SerializedName("gold") val gold: Int?,
            @SerializedName("silver") val silver: Int?
        )
    }
}