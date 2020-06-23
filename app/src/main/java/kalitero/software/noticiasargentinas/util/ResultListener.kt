package kalitero.software.noticiasargentinas.util

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias

interface ResultListener<T> {
    fun onFinish(result: ListaNoticias)

    fun onError(message: String)
}