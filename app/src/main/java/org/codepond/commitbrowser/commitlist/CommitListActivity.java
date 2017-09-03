/*
 * Copyright 2017 Nimrod Dayan CodePond.org
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

package org.codepond.commitbrowser.commitlist;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import org.codepond.commitbrowser.R;
import org.codepond.commitbrowser.common.recyclerview.ItemAdapter;
import org.codepond.commitbrowser.common.recyclerview.OnLoadMoreScrollListener;
import org.codepond.commitbrowser.databinding.CommitListActivityBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class CommitListActivity extends AppCompatActivity implements LifecycleRegistryOwner {
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private CommitListViewModel viewModel;
    @Inject CommitListViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CommitListViewModel.class);
        CommitListActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.commit_list_activity);
        lifecycleRegistry.addObserver(viewModel);
        binding.setViewModel(viewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.commitList.setLayoutManager(layoutManager);
        binding.commitList.setItemAnimator(new DefaultItemAnimator());
        binding.commitList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.commitList.setAdapter(new ItemAdapter(viewModel.getCommits()));
        binding.commitList.addOnScrollListener(new OnLoadMoreScrollListener(getResources().getInteger(R.integer.load_threshold)) {
            @Override
            protected void onLoadMore() {
                viewModel.load();
            }
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
