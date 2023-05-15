package com.example.data.source

import com.example.data.model.auth.AuthLocalData

interface AuthLocalDataSource {
    fun hasData(): Boolean
    fun getData(): AuthLocalData?
    fun setData(authLocalData: AuthLocalData)
    fun clear()
}
