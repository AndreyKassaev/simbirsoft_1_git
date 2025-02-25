package com.kassaev.simbirsoft_1_git.util

import com.kassaev.simbirsoft_1_git.util.Event as UiEvent
import com.kassaev.simbirsoft_1_git.api.model.Event as ApiEvent

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
                people = List(42) { getMockFriend() },
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
                people = List(42) { getMockFriend() },
                category = category,
                isWatched = false,
            )
        }

    fun apiListToUiList(apiList: List<ApiEvent>): List<UiEvent> =
        apiList.map { apiToUi(it) }

    fun assetListToUiList(assetList: List<EventAsset>): List<UiEvent> =
        assetList.map { assetToUi(it) }
}