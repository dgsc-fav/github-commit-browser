/*
 * Copyright 2020 Nimrod Dayan nimroddayan.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nimroddayan.commitbrowser.home

import com.nimroddayan.commitbrowser.di.ActivityScope
import com.nimroddayan.commitbrowser.home.commitdetail.CommitDetailModule
import com.nimroddayan.commitbrowser.home.commitlist.CommitListModule
import com.nimroddayan.commitbrowser.home.commitlist.CommitListNavigation
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @Module(includes = [CommitListModule::class, CommitDetailModule::class])
    internal abstract class HomeModule {
        @Binds
        abstract fun bindHomeNavigator(navigator: HomeNavigator): CommitListNavigation
    }
}
