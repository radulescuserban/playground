package tremend.com.shimmertest.common

import androidx.lifecycle.LiveData
import java.math.BigDecimal

class StockLiveData(symbol: String) : LiveData<BigDecimal>() {

    private val mListener = { price: BigDecimal ->
        value = price
    }
}