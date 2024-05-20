# 울반 - NFC기반 학급 소통 앱
![](https://blog.kakaocdn.net/dn/kTWTG/btsHtPbRbMO/ApDuCfEaKyqA57ZlnGnkU0/img.png)

<div>
<h4>📱 NFC, 블루투스 기반으로 소통해요<h4> 

<h4>🎯 친구들과 채팅하고 글을 쓰며 일상을 공유해요<h4>

<h4>👥 친구들과의 친밀도를 확인해요<h4>

<h4>🥇 선생님은 학생들의 소통 통계를 확인해요<h4>
</div>
<br/>

## 개요

- **한 줄 요약** : *울반* 프로젝트는 NFC와 BLE 통신을 기반으로 한 학급 소통 앱입니다.

- **기획의도** : 코로나 이후 악화된 교우관개를 개선을 위해 제작되었습니다.

- **개발 인원 및 기간**
  
  - **개발 인원** : Android 3명, BackEnd 3명
  
  - **프로젝트 기간** : 2024.04.08 ~ 2024.05.19

- **주요 기능**
  
  - NFC 기반의 태깅인사, 이어달리기
  
  - 블루투스 기반의 함께달리기
  
  - 학급별 채팅, 게시판, 알림장
  
  - 학급별 소통 통계

<br/><br/><br/><br/>


# 프로젝트 구조
![](https://blog.kakaocdn.net/dn/17Gn2/btsHtQhx6Ti/nfrxsULAZmdlsdLl3SJZxK/img.png)

### 기술
- Android: <span style="color:yellowgreen"> Hilt, Jetpack AAC(ViewModel, Room), Jetpack Compose, Paging</span>
- Kotlin : <span style="color:blueviolet"> Coroutine, Flow, KotlinSerialization</span>
- Library : <span style="color:orange"> Retrofit, Coil, FCM, KakaoSocialAuth, KrossBow(Stomp)</span>
- Architecture : <span style="color:gray"> MVI, MultiModule, CleanArchitecture</span>
- Connection : NFC, BlueTooth
<br/><br/><br/><br/>


# 동작 화면

**주요 동작화면은 추후 추가 예정입니다.**

### [피그마](https://www.figma.com/design/yfm5gTmRJED2uAdm7H70YC/6-kids-on-the-block?node-id=0%3A1&t=5blyLSniokJVPpQR-1)


<br/><br/><br/>
## 개발 환경

- Android Studio : Iguana | 2023.2.1 Patch 2
- Gradle JDK : jbr-17(JetBrains Runtime version 17.0.6)
- Android Gradle Plugin Version : 8.1.3
- Gradle Version : 8.1
- Kotlin version : 1.8.0

## 역할
