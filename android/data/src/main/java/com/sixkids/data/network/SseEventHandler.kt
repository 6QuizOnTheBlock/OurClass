package com.sixkids.data.network

import android.util.Log
import com.launchdarkly.eventsource.MessageEvent
import com.launchdarkly.eventsource.background.BackgroundEventHandler

private const val TAG = "D107 sse"
class SseEventHandler : BackgroundEventHandler {

    override fun onOpen() {
        // SSE 연결 성공시 처리 로직 작성

        Log.d(TAG, "onOpen: SSE 연결 성공")
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

        Log.d(TAG, "onMessage: event=$event, lastEventId=${messageEvent.lastEventId}, data=${messageEvent.data}")
    }

    override fun onComment(comment: String) {
        // SSE 주석 도착시 처리 로직 작성

        // comment: String = 도착한 주석
        Log.d(TAG, "onComment: comment=$comment")
    }

    override fun onError(t: Throwable) {

        Log.d(TAG, "onError: ${t.message}")
        // SSE 연결 전 또는 후 오류 발생시 처리 로직 작성

        // 서버가 2XX 이외의 오류 응답시 com.launchdarkly.eventsource.StreamHttpErrorException: Server returned HTTP error 401 예외가 발생
        // 클라이언트에서 서버의 연결 유지 시간보다 짧게 설정시 error=com.launchdarkly.eventsource.StreamIOException: java.net.SocketTimeoutException: timeout 예외가 발생
        // 서버가 연결 유지 시간 초과로 종료시 error=com.launchdarkly.eventsource.StreamClosedByServerException: Stream closed by server 예외가 발생
    }
}
