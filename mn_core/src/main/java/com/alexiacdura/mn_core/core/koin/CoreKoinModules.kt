package com.alexiacdura.mn_core.core.koin

import com.alexiacdura.mn_core.core.factory.DefaultServiceFactory
import com.alexiacdura.mn_core.core.factory.ServiceFactory
import com.alexiacdura.mn_core.data.feed.FeedInteractor
import com.alexiacdura.mn_core.data.feed.FeedInteractorImpl
import com.alexiacdura.mn_core.data.network.star.StarApi
import com.alexiacdura.mn_core.data.network.star.StarApiImpl
import com.alexiacdura.mn_core.data.network.star.StarApiService
import com.alexiacdura.mn_core.data.network.user.UserApi
import com.alexiacdura.mn_core.data.network.user.UserApiImpl
import com.alexiacdura.mn_core.data.network.user.UserApiService
import com.alexiacdura.mn_core.data.network.vote.VoteApi
import com.alexiacdura.mn_core.data.network.vote.VoteApiImpl
import com.alexiacdura.mn_core.data.network.vote.VoteApiService
import com.alexiacdura.mn_core.data.user.*
import org.koin.dsl.module

val coreKoinModules = module {
    single(KoinNames.USER_API_SERVICE) {
        DefaultServiceFactory(
            schedulersProvider = get(),
            clientBuilderFactory = get(),
            baseUrl = get(KoinNames.MUSICNERDS_BASE_URL),
            serviceClass = UserApiService::class.java
        ) as ServiceFactory<UserApiService>
    }

    single(KoinNames.STAR_API_SERVICE) {
        DefaultServiceFactory(
            schedulersProvider = get(),
            clientBuilderFactory = get(),
            baseUrl = get(KoinNames.MUSICNERDS_BASE_URL),
            serviceClass = StarApiService::class.java
        ) as ServiceFactory<StarApiService>
    }

    single(KoinNames.VOTE_API_SERVICE) {
        DefaultServiceFactory(
            schedulersProvider = get(),
            clientBuilderFactory = get(),
            baseUrl = get(KoinNames.MUSICNERDS_BASE_URL),
            serviceClass = VoteApiService::class.java
        ) as ServiceFactory<VoteApiService>
    }

    single(KoinNames.USER_API) { UserApiImpl(serviceFactory = get(KoinNames.USER_API_SERVICE)) as UserApi }
    single { StarApiImpl(serviceFactory = get(KoinNames.STAR_API_SERVICE)) as StarApi }
    single { VoteApiImpl(serviceFactory = get(KoinNames.VOTE_API_SERVICE)) as VoteApi }

    single { UserInteractorImpl(userApi = get(KoinNames.USER_API)) as UserInteractor }
    single (KoinNames.USER_FEED) { UserFeedInteractorImpl(userApi = get(KoinNames.USER_API)) as UserFeedInteractor }
    single (KoinNames.USER_DATA) { UserDataInteractorImpl(userApi = get(KoinNames.USER_API)) as UserDataInteractor }

    single { CreateStarInteractorImpl(starApi = get()) as CreateStarInteractor }
    single { DeleteStarInteractorImpl(starApi = get()) as DeleteStarInteractor }

    single { CreateVoteInteractorImpl(voteApi = get()) as CreateVoteInteractor }
    single { DeleteVoteInteractorImpl(voteApi = get()) as DeleteVoteInteractor }

    single(KoinNames.FEED) {
        FeedInteractorImpl(
            userFeedInteractor = get(KoinNames.USER_FEED),
            userDataInteractor = get(KoinNames.USER_DATA)
        ) as FeedInteractor
    }
}