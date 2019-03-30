package org.codepond.commitbrowser.home.commitlist

import com.airbnb.epoxy.TypedEpoxyController
import org.codepond.commitbrowser.commitInfo
import org.codepond.commitbrowser.loading
import javax.inject.Inject

class CommitListController @Inject constructor(
) : TypedEpoxyController<CommitListInfo>() {
    override fun buildModels(data: CommitListInfo) {
        data.list.forEach {
            commitInfo {
                id(it.sha)
                commitInfo(it)
                clickListener { model, _, _, _ ->
                    data.onClick?.invoke(model.commitInfo().sha)
                }
            }
        }
        if (data.loading) {
            loading {
                id("loading")
                matchParent(data.list.isEmpty())
            }
        }
    }
}