package open.your.eyes.miichibaseapp.ui.about

import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.about_fragment.*
import open.your.eyes.miichibaseapp.R
import open.your.eyes.miichibaseapp.data.model.RepoModel
import open.your.eyes.miichibaseapp.databinding.AboutFragmentBinding
import open.your.eyes.miichibaseapp.ui.base.BaseFragment


class AboutFragment : BaseFragment<AboutViewModel, AboutFragmentBinding>() {
    companion object {
        const val ArgRepoModel = "repo_model"
        const val ArgResult = "arg_result"
    }

    override fun createViewModel(): Class<AboutViewModel> {
        return AboutViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.about_fragment
    }

    override fun bindViewModel() {

    }

    override fun initView() {
//        val repoModel = arguments?.get("RepoModel") as RepoModel
//        val id = repoModel.id.toString()
//        val url = repoModel.owner.avatar_url
//        item_avatar.transitionName = id
//        Glide.with(context).load(url).into(item_avatar)
    }

}
