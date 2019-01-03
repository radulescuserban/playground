package tremend.com.shimmertest.network


class BaseResource<T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    fun <T> success(data: T): BaseResource<T> {
        return BaseResource(Status.SUCCESS, data, null)
    }

    fun <T> error(msg: String, data: T?): BaseResource<T> {
        return BaseResource(Status.ERROR, data, msg)
    }

    fun <T> loading(data: T?): BaseResource<T?> {
        return BaseResource(Status.LOADING, data, null)
    }

}