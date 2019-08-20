package io.github.markspit93.autotechno.channel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "channel")
data class Channel(

        @ColumnInfo(name = "mediaId")
        val mediaId: String,

        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "imageRes")
        val imageRes: Int,

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0

) : Parcelable
