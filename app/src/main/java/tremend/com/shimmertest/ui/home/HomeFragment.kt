package tremend.com.shimmertest.ui.home


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_home.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import tremend.com.shimmertest.R


class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshFab.setOnClickListener { makeRxCalls() }

        listenRx1textChanged()
        testRx1ZipFunction()

//        listenRx2textChanged()
//        testRx2ZipFunction()
    }

    private fun makeRxCalls() {
        viewModel.getAllTags()
    }

    private fun listenRx1textChanged() {
        inputRx1Et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onRx1TextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun onRx1TextChanged(newRx1Input: String) {
        getRx1Observable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    toChangeTv.visibility = View.VISIBLE
                    toChangeTv.text = newRx1Input + " has been added!"
                },
                {
                    Log.e(TAG, "error in rx1", it)
                })
    }

    private fun testRx1ZipFunction() {
        startRx1ZipBtn.setOnClickListener {
            Log.d(TAG, "in rx1 on click listener")

            Observable.zip(getRx1Observable(), getSecondRx1Observable()) { _, _ ->
                Log.d(TAG, "in rx1 zip")
                toChangeTv.visibility = View.INVISIBLE
                toChangeTv.visibility = View.VISIBLE
                toChangeTv.text = "i was changed from a Rx1 zip function!"

                true
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it) {
                            Toast.makeText(context, "Rx1 onNext()", Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Log.e(TAG, "error in rx1 zip", it)
                    })
        }
    }

    private fun getRx1Observable(): Observable<String> {
        return Observable.defer {
            Observable.just("de ce nu mergi cu rx1")
        }
    }

    private fun getSecondRx1Observable(): Observable<String> {
        return Observable.defer {
            Observable.just("test")
        }
    }

    private fun listenRx2textChanged() {
        inputRx2Et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onRx2TextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun onRx2TextChanged(newRx2Input: String) {
        val disposable = getRx2Observable()
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe(
                {
                    toChangeTv.visibility = View.VISIBLE
                    toChangeTv.text = newRx2Input + " has been added!"
                },
                {
                    Log.e(TAG, "error in rx2", it)
                })
    }

    private fun testRx2ZipFunction() {
        startRx1ZipBtn.setOnClickListener {
            Log.d(TAG, "in rx2 on click listener")

            io.reactivex.Observable.zip(getRx2Observable(), getSecondRx2Observable(),
                BiFunction<String, String, Boolean> { _: String, _: String ->
                    Log.d(TAG, "in rx2 zip")
                    toChangeTv.visibility = View.VISIBLE
                    toChangeTv.text = "i was changed from a Rx2 zip function!"

                    true
                }
            )
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it) {
                            Toast.makeText(context, "Rx2 onNext()", Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Log.e(TAG, "error in rx2 zio", it)
                    })
        }
    }

    private fun getRx2Observable(): io.reactivex.Observable<String> {
        return io.reactivex.Observable.defer {
            io.reactivex.Observable.just("de ce mergi cu rx2")
        }
    }

    private fun getSecondRx2Observable(): io.reactivex.Observable<String> {
        return io.reactivex.Observable.defer {
            io.reactivex.Observable.just("test 2")
        }
    }
}
