package com.kassaev.simbirsoft_1_git.core.util

import com.kassaev.simbirsoft_1_git.core.util.Event as UiEvent
import com.kassaev.simbirsoft_1_git.core.api.model.Event as ApiEvent
import com.kassaev.simbirsoft_1_git.core.database.entity.Event as DbEvent

object EventMapper {

    fun apiToUi(apiEvent: ApiEvent): UiEvent =
        with(apiEvent) {
            UiEvent(
                id = id,
                imageUrlList = photos,
                title = name,
                description = description,
                timestamp = startDate,
                organization = organization,
                people = List(42) { Friend.default },
                category = category,
                isWatched = false,
            )
        }

    fun assetToUi(assetEvent: EventAsset): UiEvent =
        with(assetEvent){
            UiEvent(
                id = id,
                imageUrlList = photos,
                title = name,
                description = description,
                timestamp = startDate,
                organization = organization,
                people = List(42) { Friend.default },
                category = category,
                isWatched = false,
            )
        }

    fun dbToUi(dbEvent: DbEvent): UiEvent =
        with(dbEvent) {
            UiEvent(
                id = id,
                description = description,
                category = category,
                imageUrlList = photos,
                title = name,
                timestamp = startDate,
                organization = organization,
                people = List(42) { Friend.default },
                isWatched = isWatched
            )
        }

    fun apiToDb(apiEvent: ApiEvent): DbEvent =
        with(apiEvent) {
            DbEvent(
                id = id,
                name = name,
                startDate = startDate,
                endDate = endDate,
                description = description,
                status = status,
                photos = photos,
                category = category,
                createdAt = createdAt,
                phone = phone,
                address = address,
                organization = organization,
                isWatched = false,
            )
        }

    fun assetToDb(assetEvent: EventAsset): DbEvent =
        with(assetEvent) {
            DbEvent(
                id = id,
                name = name,
                startDate = startDate,
                endDate = endDate,
                description = description,
                status = status,
                photos = photos,
                category = category,
                createdAt = createdAt,
                phone = phone,
                address = address,
                organization = organization,
                isWatched = false
            )
        }

    fun apiListToUiList(apiEventList: List<ApiEvent>): List<UiEvent> =
        apiEventList.map { apiToUi(it) }

    fun assetListToUiList(assetEventList: List<EventAsset>): List<UiEvent> =
        assetEventList.map { assetToUi(it) }

    fun dbListToUiList(dbEventList: List<DbEvent>): List<UiEvent> =
        dbEventList.map { dbToUi(it) }

    fun apiListToDbList(apiEventList: List<ApiEvent>): List<DbEvent> =
        apiEventList.map { apiToDb(it) }

    fun assetListToDbList(assetEventList: List<EventAsset>): List<DbEvent> =
        assetEventList.map { assetToDb(it) }
}