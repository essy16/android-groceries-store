package com.hieuwu.groceriesstore.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hieuwu.groceriesstore.data.utils.OrderStatus
import com.hieuwu.groceriesstore.domain.entities.Order
import com.hieuwu.groceriesstore.domain.entities.ProductAndLineItem
import com.hieuwu.groceriesstore.domain.repository.OrderRepository
import com.hieuwu.groceriesstore.domain.repository.ProductRepository
import com.hieuwu.groceriesstore.presentation.utils.ObservableViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ObservableViewModel() {

    private var orderId = orderRepository.getCurrentCartId(OrderStatus.IN_CART)

    private var _lineItemList = MutableLiveData<List<ProductAndLineItem>>()
    val lineItemList: LiveData<List<ProductAndLineItem>>
        get() = _lineItemList

    private var _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double>
        get() = _totalPrice

    init {
        getLineItemFromDatabase()
        sumPrice()
    }

    private fun getLineItemFromDatabase() {
        viewModelScope.launch {
            getLineItemFromLocal()
        }
    }

    private suspend fun getLineItemFromLocal() {
        return withContext(Dispatchers.IO) {
            _lineItemList =
                productRepository.getLineItemInOrder("da0bce0d-628e-4b77-86db-a70d6ddf7050")
                    .asLiveData() as MutableLiveData<List<ProductAndLineItem>>
        }
    }

    fun sumPrice() {
        var sum = 0.0
        if (_lineItemList.value != null) {
            for (item in _lineItemList.value!!) {
                val sub = item.lineItem?.subtotal ?: 0.0
                sum = sum.plus(sub)
            }
        }
        _totalPrice.value = sum
    }


    fun decreaseQty(lineItemModel: ProductAndLineItem) {
        Timber.d("Minus Clicked")
        val qty = lineItemModel.lineItem?.quantity?.minus(1)
        if (qty != null) {
            lineItemModel.lineItem.quantity = qty
            lineItemModel.lineItem.subtotal = qty * (lineItemModel.product?.price ?: 1.0)
        }
        viewModelScope.launch {
            updateCurrentLineItem(lineItemModel)
        }
    }

    fun increaseQty(lineItemModel: ProductAndLineItem) {
        Timber.d("Plus Clicked")
        val qty = lineItemModel.lineItem?.quantity?.plus(1)
        if (qty != null) {
            lineItemModel.lineItem.quantity = qty
            lineItemModel.lineItem.subtotal = qty * (lineItemModel.product?.price ?: 1.0)
        }
        viewModelScope.launch {
            updateCurrentLineItem(lineItemModel)
        }
    }

    private suspend fun updateCurrentLineItem(lineItemModel: ProductAndLineItem) {
        withContext(Dispatchers.IO) {
            productRepository.updateProductAndLineItem(lineItemModel)
        }
    }


}