package com.kassaev.simbirsoft_1_git.repository.category

import com.kassaev.simbirsoft_1_git.util.HelpCategory
import io.reactivex.rxjava3.core.Observable

interface CategoryRepository {
    fun getCategoryListObservable(): Observable<List<HelpCategory>>
}