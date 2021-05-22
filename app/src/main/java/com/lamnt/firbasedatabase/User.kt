package com.lamnt.firbasedatabase

import java.io.Serializable

data class User(
    val name: String,
    val phone: String
) : Serializable{
    var key : String = ""
    constructor() : this("", "")
}