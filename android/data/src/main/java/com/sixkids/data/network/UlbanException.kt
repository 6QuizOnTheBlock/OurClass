package com.sixkids.data.network

class UlbanException (
    val code: Int,
    override val message: String
): Exception(message)