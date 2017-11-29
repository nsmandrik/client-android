package com.andrewvychev.railwaytickets.ui.findroute

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.andrewvychev.railwaytickets.R
import com.andrewvychev.railwaytickets.RailwayApplication
import com.andrewvychev.railwaytickets.base.Contract
import com.andrewvychev.railwaytickets.base.MvpActivity
import kotlinx.android.synthetic.main.activity_find_route.et_date
import kotlinx.android.synthetic.main.activity_find_route.et_from
import kotlinx.android.synthetic.main.activity_find_route.et_to
import org.threeten.bp.LocalDate
import javax.inject.Inject

class FindRouteActivity : MvpActivity<FindRouteContract.View>(), FindRouteContract.View,
        DatePickerFragment.Listener {

    @Inject lateinit var presenter: FindRouteContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_route)
    }

    override fun injectDependencies() {
        (application as? RailwayApplication)?.appComponent
                ?.activityComponent()
                ?.build()
                ?.inject(this)
    }

    override fun getPresenter(): Contract.Presenter<FindRouteContract.View> = presenter

    fun onNextClicked(view: View) {
        presenter.onNextClicked(et_from.text.toString(),
                et_to.text.toString())
    }

    fun onChooseDateClicked(view: View) {
        val fragment = DatePickerFragment.newInstance(LocalDate.now())
        fragment.setListener(this)
        fragment.show(supportFragmentManager, "")
    }

    override fun onDateSet(date: LocalDate) {
        et_date.setText(date.toString(), TextView.BufferType.EDITABLE)
        presenter.onDateChoosed(date)
    }
}
