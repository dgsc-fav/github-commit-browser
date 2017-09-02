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

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import org.codepond.commitbrowser.R;
import org.codepond.commitbrowser.databinding.CommitListBinding;

import dagger.android.AndroidInjection;

public class CommitListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        CommitListBinding binding = DataBindingUtil.setContentView(this, R.layout.commit_list);
        CommitListViewModel viewModel = ViewModelProviders.of(this).get(CommitListViewModel.class);
        binding.setViewModel(viewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.commitList.setLayoutManager(layoutManager);
        binding.commitList.setItemAnimator(new DefaultItemAnimator());
        binding.commitList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.commitList.setAdapter(new CommitAdapter(viewModel.getCommits()));
    }

    class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder> {
        private ObservableList<CommitItemViewModel> commits;

        CommitAdapter(ObservableList<CommitItemViewModel> commits) {
            this.commits = commits;
        }

        @Override
        public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            return new CommitViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false));
        }

        @Override
        public void onBindViewHolder(CommitViewHolder holder, int position) {
            CommitItemViewModel commit = commits.get(position);
            holder.bind(commit);
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.commit_item;
        }

        @Override
        public int getItemCount() {
            return commits.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            commits.addOnListChangedCallback(onListChangedCallback);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            commits.removeOnListChangedCallback(onListChangedCallback);
        }

        private ObservableList.OnListChangedCallback<ObservableList<CommitItemViewModel>> onListChangedCallback =
                new ObservableList.OnListChangedCallback<ObservableList<CommitItemViewModel>>() {
            @Override
            public void onChanged(ObservableList<CommitItemViewModel> commitItemViewModels) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<CommitItemViewModel> commitItemViewModels, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<CommitItemViewModel> commitItemViewModels, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<CommitItemViewModel> commitItemViewModels, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<CommitItemViewModel> commitItemViewModels, int positionStart, int itemCount) {

            }
        };

        class CommitViewHolder extends RecyclerView.ViewHolder {
            private ViewDataBinding binding;

            CommitViewHolder(ViewDataBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bind(CommitItemViewModel viewModel) {
                binding.setVariable(BR.viewModel, viewModel);
                binding.executePendingBindings();
            }
        }
    }
}