package com.softwarengineering.bloodconnect.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.softwarengineering.bloodconnect.data.datasource.DonorDatasource
import com.softwarengineering.bloodconnect.data.datasource.HospitalDataSource
import com.softwarengineering.bloodconnect.data.repo.DonorRepostory
import com.softwarengineering.bloodconnect.data.repo.HospitalRepostory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Collection Providers with @Named
    @Provides
    @Singleton
    @Named("donorCollection")
    fun provideDonorCollection(): CollectionReference {
        return Firebase.firestore.collection("donor")
    }

    @Provides
    @Singleton
    @Named("hospitalCollection")
    fun provideHospitalCollection(): CollectionReference {
        return Firebase.firestore.collection("hospital")
    }

    // Donor DataSource & Repository
    @Provides
    @Singleton
    fun provideDonorDatasource(@Named("donorCollection") collection: CollectionReference): DonorDatasource {
        return DonorDatasource(collection)
    }

    @Provides
    @Singleton
    fun provideDonorRepository(donorDatasource: DonorDatasource): DonorRepostory {
        return DonorRepostory(donorDatasource)
    }

    // Hospital DataSource & Repository
    @Provides
    @Singleton
    fun provideHospitalDatasource(@Named("hospitalCollection") collection: CollectionReference): HospitalDataSource {
        return HospitalDataSource(collection)
    }

    @Provides
    @Singleton
    fun provideHospitalRepository(hospitalDataSource: HospitalDataSource): HospitalRepostory {
        return HospitalRepostory(hospitalDataSource)
    }
}