package com.partSoftware.heliumplus.features.article.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.partSoftware.heliumplus.features.article.data.db.entity.TagEntity

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagEntity>)

    @Query("SELECT * FROM tag")
    fun getTags(): LiveData<List<TagEntity>?>?

    @Query("SELECT id FROM tag WHERE isSelected=1")
    suspend fun getSelectedTagsIds(): List<Int>

    @Query("SELECT id FROM tag WHERE isChecked=1")
    fun getCheckedTagsIds(): List<Int>

    @Query("SELECT id FROM tag WHERE isChecked=1")
    fun getCheckedTagsIdsLiveData(): LiveData<List<Int>?>?

    @Query("DELETE FROM tag")
    suspend fun deleteTags()

    @Update
    suspend fun updateTags(tags: List<TagEntity>)

    @Update
    suspend fun updateTag(tag: TagEntity)
}