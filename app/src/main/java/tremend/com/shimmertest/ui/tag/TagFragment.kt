package tremend.com.shimmertest.ui.tag

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tag_fragment.*

import tremend.com.shimmertest.R
import tremend.com.shimmertest.common.Constants

class TagFragment : Fragment() {

    companion object {
        fun newInstance() = TagFragment()
    }

    private lateinit var viewModel: TagViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tag_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TagViewModel::class.java)
        val tagName = arguments?.getString(Constants.KEY_IMGUR_TAG_NAME)
        val tagFollowers = arguments?.getInt(Constants.KEY_IMGUR_TAG_FOLLOWRS)
        val tagTotalItems = arguments?.getInt(Constants.KEY_IMGUR_TAG_TOTAL_ITEMS)
        setTexts(tagName, tagFollowers, tagTotalItems)

    }

    private fun setTexts(tagName: String?, tagFollowers: Int?, tagTotalItems: Int?) {
        tagNameTv.text = "Tag: $tagName"
        followersValueTv.text = tagFollowers.toString()
        itemsValueTv.text = tagTotalItems.toString()
    }
}
