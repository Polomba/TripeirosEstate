package com.example.homebalance.Interfaces
import com.example.homebalance.Classes.Auth
import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthI {
    @POST("authLogin")
    fun authLogin(
        @Body userData: User
    ): Call<Any>

    /**
     * problema 1:
     * o que tu retornas de uma call no authLogin é um { string:string }
     * já não é tipo um List<User> ou apenas User portanto "): Call<User>"
     * não pode ser isto a forma que eu resolvi pra não me chatear foi
     * ou por Call<Any> ou a que eu tenho que é quase igual Call<Map<String, String>>
     * porquê?
     * porque se ele defovesse um user tu podias por "): Call<User>" isto como não
     * mesmo que metas não vai fazer nada por que não vai ter acesso a resposta já que
     * no user não tens "token: .... "
     *
     * Eu cheguei a tentar com JSONObject mas depois caguei que tinha outra merdas a fazer
     *
     */

    @POST("authRegister")
    fun authRegister(
        @Body userData: User
    ): Call<Any>
}