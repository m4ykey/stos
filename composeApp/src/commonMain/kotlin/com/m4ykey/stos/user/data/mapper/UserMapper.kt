package com.m4ykey.stos.user.data.mapper

import com.m4ykey.stos.user.data.network.model.BadgeCountsDto
import com.m4ykey.stos.user.data.network.model.UserDto
import com.m4ykey.stos.user.domain.model.BadgeCounts
import com.m4ykey.stos.user.domain.model.User

fun BadgeCountsDto.toBadgeCounts() : BadgeCounts {
    return BadgeCounts(
        silver = silver ?: -1,
        gold = gold ?: -1,
        bronze = bronze ?: -1
    )
}

fun UserDto.toUser() : User {
    return User(
        displayName = displayName.orEmpty(),
        reputation = reputation ?: -1,
        profileImage = profileImage.orEmpty(),
        userId = userId ?: -1,
        badgeCounts = badgeCounts?.toBadgeCounts() ?: BadgeCounts(0,0,0)
    )
}