package com.android4dev.roomjetpack.appinterface

interface AsyncResponseCallback {
    fun onResponse(isSuccess: Boolean, call: String)
}