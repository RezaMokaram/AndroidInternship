package com.partSoftware.heliumplus.core.dp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.partSoftware.heliumplus.features.article.data.db.dao.ArticleDao
import com.partSoftware.heliumplus.features.article.data.db.dao.TagDao
import com.partSoftware.heliumplus.features.article.data.db.entity.ArticleEntity
import com.partSoftware.heliumplus.features.article.data.db.entity.TagEntity
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkDao
import com.partSoftware.heliumplus.features.profile.data.db.BookmarkEntity
import com.partSoftware.heliumplus.features.profile.data.db.ProfileDao
import com.partSoftware.heliumplus.features.search.data.db.dao.SearchDao

@Database(
    entities = [
        TagEntity::class,
        ArticleEntity::class,
        BookmarkEntity::class
    ], version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun tagDao(): TagDao

    abstract fun articleDao(): ArticleDao

    abstract fun profileDao(): ProfileDao

    abstract fun searchDao(): SearchDao

    abstract fun bookmark(): BookmarkDao
}