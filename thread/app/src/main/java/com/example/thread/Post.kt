package com.example.thread

import android.icu.text.CaseMap.Title
import org.json.JSONArray
import org.json.JSONObject

class Post (val id: Int, val userId: Int, val title: String, body:String) {
    companion object {
        fun importFromJSONObject(obj: JSONObject): Post {
            return Post(
                obj.getInt("Id"),
                obj.getInt("userId"),
                obj.getString("title"),
                obj.getString("body")
            )
        }

        fun importFromJSONArray(array: JSONArray): List<Post> {
            var posts = mutableListOf<Post>()
            for (i in 0..array.length()) {
                posts.add(Post.importFromJSONObject((array.getJSONObject())))
            }
            return posts
        }
    }
}