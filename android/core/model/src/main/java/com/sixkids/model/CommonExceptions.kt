package com.sixkids.model

class BadRequestException(
    override val message: String = "잘못된 요청입니다..",
) : RuntimeException()

class ForbiddenException(
    override val message: String = "제한된 요청입니다.",
) : RuntimeException()

class NotFoundException(
    override val message: String = "정보를 찾을 수 없습니다.",
) : RuntimeException()

class NetworkException(
    override val message: String = "네트워크 연결 상태에 문제가 있습니다.",
) : RuntimeException()

class UnknownException(
    override val message: String = "알 수 없는 에러가 발생습니다.",
) : RuntimeException()
