package kalitero.software.noticiasargentinas.util

interface ResultListener<T> {
    fun onFinish(result: T)

    fun onError(message: String)
}