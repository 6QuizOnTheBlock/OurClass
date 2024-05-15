package com.sixkids.teacher.manageclass

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `학급_문자열_정규식_활용_파싱_테스트`(){
        val text = "3 학년, 4학년, 그리고 12 학년이 있습니다."
        val regex = "[\\d+]\\s*학년".toRegex() // 정규 표현식으로 변환


        // 정규식을 사용하여 매치되는 모든 결과를 찾기
        val matches = regex.findAll(text)

        matches.forEach {
            it.groupValues
        }
    }
}