package com.greedy.toyproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper (context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version){

    /* 데이터베이스가 생성될 때 동작하는 메소드 */
    override fun onCreate(db: SQLiteDatabase?) {

        /* 데이터베이스가 생성될 때 테이블을 생성한다. */
        val create = "create table post (" +
                "no integer primary key, " +
                "content text, " +
                "datetime integer " +
                ")"

        db?.execSQL(create)
    }

    /* 데이터베이스가 업그레이드 될 때 동작하는 메소드 */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        /* 내용 없음 */
    }

    /* 1. insert */
    fun insertPost(post: Post) {

        val values = ContentValues()
        values.put("content", post.content)
        values.put("datetime", post.datetime)

        val wd = writableDatabase
        wd.insert("post", null, values)
        wd.close()

    }

    /* 2. select */

    @SuppressLint("Range")
    fun selectPost(): MutableList<Post>{


        if(readableDatabase == null){
            onCreate(readableDatabase)
        }

        val rd = readableDatabase

        val select = "select * from post"
        val list = mutableListOf<Post>()

        var cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()){
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val dateTime = cursor.getLong(cursor.getColumnIndex("datetime"))
            list.add(Post(no, content, dateTime))
        }

        cursor.close()
        rd.close()
        return  list

    }

    /* 3. delete */
    fun  deletePost(post: Post){
        val delete = "delete from post where no = ${post.no}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }

}