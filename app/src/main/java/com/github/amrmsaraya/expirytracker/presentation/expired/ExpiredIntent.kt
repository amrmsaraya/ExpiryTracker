package com.github.amrmsaraya.expirytracker.presentation.expired

sealed class ExpiredIntent {
    object GetProducts : ExpiredIntent()
}