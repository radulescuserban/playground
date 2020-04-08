package tremend.com.shimmertest.ui.tag

import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
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
        val tagName = arguments?.let { TagFragmentArgs.fromBundle(it).tagName }
        val tagFollowers = arguments?.let { TagFragmentArgs.fromBundle(it).followers }
        val tagTotalItems = arguments?.let { TagFragmentArgs.fromBundle(it).totalItems }

        setTexts(tagName, tagFollowers, tagTotalItems)
        animateValue(tagFollowers, followersValueTv)
        animateValue(tagTotalItems, itemsValueTv)
        animateIndicator(tagFollowers, followersIndicator)

        nextBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_tagFragment_to_secondActivity)
        }
    }

    private fun setTexts(tagName: String?, tagFollowers: Int?, tagTotalItems: Int?) {
        tagNameTv.text = "Tag: $tagName"
        followersValueTv.text = "0"
        itemsValueTv.text = "0"

        followersLabelTv.text = "followers"
        itemsLabelTv.text = "total items"
    }

    private fun animateValue(value: Int?, textView: TextView) {
        var floatValue = 500f
        value?.let { floatValue = it.toFloat() }
        ValueAnimator.ofFloat(0f, floatValue).apply {
            duration = 1000
            addUpdateListener {
                textView.text = it.animatedValue.toString()
            }
            start()
        }
    }

    private fun animateIndicator(value: Int?, view: View) {
        var floatValue = 500f
        value?.let { floatValue = it.toFloat() }
        ValueAnimator.ofFloat(0f, floatValue).apply {
            duration = 1000
            addUpdateListener {
                view.x = it.animatedValue as Float
            }
            start()
        }
    }
}
