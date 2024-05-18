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
    fun `학급_문자열__파싱_테스트`(){
        //when
        val text = "구미 초등학교\n2학년 3반"


        val school_name = text.split("\n")[0]
        val grade = text.split("\n")[1].split("학년")[0].toInt()
        val classNumber = text.split("\n")[1].split(" ")[1].split("반")[0].toInt()

        //then
        assertEquals("구미 초등학교", school_name)
        assertEquals(2, grade)
        assertEquals(3, classNumber)

    }
}