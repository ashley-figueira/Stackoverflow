package com.ashleyfigueira.stackoverflow.base

sealed class ScreenAction {
    object PullToRefreshAction : ScreenAction()
}