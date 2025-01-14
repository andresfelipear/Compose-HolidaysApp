package com.aarevalo.holidays.domain.model

data class State(
    val name: String,
    val code: String,
){
    companion object{
        fun getCountryStateFromName(name: String?) : String? {
            val words = name?.split(" ")

            return if(words?.size == 1){
                name.take(2).uppercase()
            } else {
                words?.joinToString(""){
                    word -> word.firstOrNull()?.uppercase() ?: ""
                }
            }
        }
    }
}
