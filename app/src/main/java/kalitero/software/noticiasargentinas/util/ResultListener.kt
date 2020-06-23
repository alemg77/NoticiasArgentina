package kalitero.software.noticiasargentinas.util

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias

interface ResultListener<T> {
    fun onFinish(result: T)
    fun onError(message: String)
}