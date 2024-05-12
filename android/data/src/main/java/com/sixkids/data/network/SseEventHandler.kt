package com.sixkids.data.network

import android.util.Log
import com.launchdarkly.eventsource.MessageEvent
import com.launchdarkly.eventsource.background.BackgroundEventHandler
import com.sixkids.domain.sse.SseEventListener
import com.sixkids.model.SseEventType
import javax.inject.Inject

private const val TAG = "D107 sse"

class SseEventHandler @Inject constructor() : BackgroundEventHandler {

    private var eventListener: SseEventListener? = null
    fun setEventListener(listener: SseEventListener) {
        eventListener = listener
    }

    override fun onOpen() {
        eventListener?.onEventReceived(SseEventType.SSE_CONNECT, "")
    }

    override fun onClosed() {
        // SSE 연결 종료시 처리 로직 작성
        Log.d(TAG, "onClosed: SSE 연결 종료")
    }

    override fun onMessage(event: String, messageEvent: MessageEvent) {
        // SSE 이벤트 도착시 처리 로직 작성

        // event: String = 이벤트가 속한 채널 또는 토픽 이름
        // messageEvent.lastEventId: String = 도착한 이벤트 ID
        // messageEvent.data: String = 도착한 이벤트 데이터
        eventListener?.onEventReceived(SseEventType.valueOf(event), messageEvent.data)
    }

    override fun onComment(comment: String) {
        // SSE 주석 도착시 처리 로직 작성

        // comment: String = 도착한 주석
        Log.d(TAG, "onComment: comment=$comment")
    }

    override fun onError(t: Throwable) {
        eventListener?.onError(t)
    }
}
