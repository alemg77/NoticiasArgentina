package kalitero.software.noticiasargentinas.util

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias
import kalitero.software.noticiasargentinas.Modelo.Voto

interface ResultListener<T> {
    fun onFinish(result: T)
    fun onError(message: String)
}