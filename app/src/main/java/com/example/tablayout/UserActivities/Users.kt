package com.example.tablayout.UserActivities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(

                    val email:String="",
                    val name:String="",
                    val pictureUrl:String=""
                    )


