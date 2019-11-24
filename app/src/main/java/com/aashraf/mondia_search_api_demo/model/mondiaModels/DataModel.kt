package com.aashraf.mondia_search_api_demo.model.mondiaModels

data class DataModel(
//    val additionalArtists: List<Any>,
//    val adfunded: Boolean=true,
//    val bundleOnly: Boolean= true,
//    val cover: Cover,
//    val duration: Int=1,
//    val genres: List<String>,
//    val id: Int,
//    val idBag: IdBag,
    val label: String,
//    val mainArtist: MainArtist,
//    val mainRelease: Boolean,
//    val numberOfTracks: Int,
//    val partialStreamable: Boolean,
//    val publishingDate: String,
//    val release: Release,
//    val statistics: Statistics,
//    val streamable: Boolean,
//    val streamableTracks: Int,
    val title: String
//    val trackNumber: Int,
//    val type: String,
//    val variousArtists: Boolean,
//    val volumeNumber: Int

)
{
    companion object {
        fun getList(): List<DataModel> {
            return listOf( DataModel(title = "",label = ""))
        }
    }
}