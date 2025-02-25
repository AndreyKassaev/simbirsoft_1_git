package com.kassaev.simbirsoft_1_git.repository.category

import com.kassaev.simbirsoft_1_git.util.Category
import io.reactivex.rxjava3.core.Observable

interface CategoryRepository {
    fun getCategoryMapObservable(): Observable<Map<String, Category>>
}