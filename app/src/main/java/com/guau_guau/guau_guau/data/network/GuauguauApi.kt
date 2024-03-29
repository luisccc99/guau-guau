package com.guau_guau.guau_guau.data.network


import com.guau_guau.guau_guau.data.responses.GuauguauComment
import com.guau_guau.guau_guau.data.responses.GuauguauPost
import com.guau_guau.guau_guau.data.responses.*
import retrofit2.http.*

interface GuauguauApi {

    companion object {
        const val BASE_URL = "https://pupperinossearch.herokuapp.com/api/v1/"
    }

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field(value = "email") email: String,
        @Field(value = "password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("users")
    suspend fun signUp(
        @Field("name") name: String,
        @Field("lastname") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): UserResponse

    @GET("user")
    suspend fun getUser(
        @Query("id") userId: String
    ): UserResponse

    @FormUrlEncoded
    @PATCH("user")
    suspend fun patchUser(
        @Field("id") id: String,
        @Field("name") name: String?,
        @Field("lastname") lastName: String?,
        @Field("aboutme") about: String?
    ): UserResponse

    @FormUrlEncoded
    @POST("posts")
    suspend fun createPost(
        @Field("user_id") id: String,
        @Field("title") title: String,
        @Field("body") body: String,
        @Field("longitude") longitude: Double?,
        @Field("latitude") latitude: Double?
    ): PostResponse

    @FormUrlEncoded
    @POST("comments")
    suspend fun createComment(
        @Field("user_id") userId: String,
        @Field("post_id") postId: String,
        @Field("body") body: String,
        @Header("Authorization") token: String
    ): CommentResponse

    @GET("comment")
    suspend fun searchComments(
        @Query("post_id") post_id: String,
        @Query("page") page: Int?,
    ): List<GuauguauComment>

    @FormUrlEncoded
    @PATCH("post")
    suspend fun patchPost(
        @Field("id") postId: String,
        @Field("resolved") resolved: Boolean,
        @Field("resolved_reason") resolvedReason: String
    ): PostResponse

    @DELETE("post")
    suspend fun deletePost(
        @Query("id") postId: String
    ): PostResponse

    @GET("posts")
    suspend fun searchPosts(
        @Query("resolved") page: Boolean,
        @Query("page") per_page: Int?,
    ):List<GuauguauPost>?

    @GET("post")
    suspend fun getPost (
        @Query("id") postId: String
    ): PostResponse
}
