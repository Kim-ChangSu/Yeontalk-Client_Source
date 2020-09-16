연톡
======================

# 1. 연톡에 관하여
## 1.1. 연톡이란?
익명의 상대와 1대1 실시간 채팅, 사진 전송, 동영상 전송, 영상통화가 가능한 어플이다.


## 1.2. 연톡 시연 영상
[**연톡 시연 영상 링크**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4)

## 1.3. 추가 소스
[**연톡 코틀린, MVVM, RxJava 리팩토링 소스 링크**](https://github.com/Kim-ChangSu/Yeontalk-Client_Kotlin_Source)

[**연톡 서버 소스 링크**](https://github.com/Kim-ChangSu/Yeontalk-Server_Source)

[**연톡 챗봇 소스 링크**](https://github.com/Kim-ChangSu/Yeontalk-Chatbot_Source)

****
# 2. 주요 기능

## 2.1 실시간 1대1 채팅

* 채팅방 나누기 

[**채팅방 나누기 영상 링크 (2:40)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=160)

	- 해당 채팅방에서 사용자가 읽지 않은 채팅의 개수가 표시된다. 


* 실시간 채팅

[**실시간 채팅 영상 링크 (3:10)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=190)
    
	- 실시간으로 채팅이 읽혔는지, 안 읽혔는지 확인 가능하다. 


* 네트워크 연결 끊김에 따른 채팅 에러 처리

[**채팅 에러 처리 영상 링크 (3:25)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=205)

	- 네트워크 연결이 끊긴 상태에서 채팅을 전송하게 되면 채팅에 에러 표시가 뜬다.

	- 전송 에러가 뜬 채팅은 재전송과 삭제가 가능하다. 

	   + 이유 : 에러가 뜬 채팅을 재전송함으로써 사용자가 다시 채팅 내용을 적는 수고로움을 덜어준다. 

	- 해당 채팅방에서 전송 에러가 뜬 채팅들은 계속해서 채팅방에 남아있다. 

    - 네트워크 연결이 끊긴 상태에서 상대로부터 전송된 채팅은 네트워크 연결이 다시 되었을 때, 화면에 뜨게 된다. 

	   + 이유 : 네트워크 연결 이후, 계속해서 대화를 이어나갈 수 있다. 


* 채팅방 나가기

[**채팅방 나가기 영상 링크 (5:16)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=316)

	- 사용자가 채팅방을 나가게 되면, 상대방에게 해당 사용자가 채팅방을 나갔다는 메세지가 전송된다. 

	- 사용자가 채팅방을 나가게 되면, 해당 채팅방에서 사용자의 정보는 보이지 않게 된다.


## 2.2. 동영상 압축 전송 

[**동영상 압축 영상 링크 (4:20)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=260)

	- 동영상이 압축되어 상대방에서 전송된다.

	   + 이유 : 동영상 파일의 크기가 작아지면 상대방이 동영상을 볼 때, 로딩 시간이 줄어든다. 

	   + 이유 : 전송할 동영상 파일의 크기가 줄어 들게 되면 전송한 동영상을 저장하는 서버의 용량을 절약할 수 있다. 

	- 동영상 압축은 해당 기기에 이루어진다. 

	   + 이유 : 동영상 압축 과정이 서버 컴퓨터에서 이루어 진다면, 서버 컴퓨터의 과부하를 초래할 수 있다. 

	- 동영상 압축이 진행되는 동안 채팅이 가능하다.
	

## 2.3. 영상 통화

* 영상 통화

[**영상 통화 영상 링크 (4:58)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=298)

## 2.4. 챗봇

* 챗봇

[**챗봇 영상 링크 (5:30)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=330)

## 2.5. 친구 리스트

* 친구 목록

[**친구 목록 영상 링크 (00:00)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4)

    - 친구들을 최근 어플 접속 시간 순으로 정렬한다.

       + 이유 : 사용자가 최근 접속한 사람에게 대화를 걸 경우, 대화가 지속적으로 이어질 가능성이 높다.  

	- 화면을 위로 스크롤 하거나 목록 버튼을 클릭함으로써 친구 목록이 갱신된다.

	   + 이유 : 실시간으로 친구 목록이 계속해서 갱신된다면, 사용자가 리스트의 잦은 변화로 인해 불편함을 느낄 것이다.
    
    - 화면을 아래로 스크롤함에 따라 목록에 친구들이 30명씩 추가된다.

       + 이유 : 서버로부터 한번에 너무 많은 친구들의 정보들을 가져온다면, 사용자가 오랜 시간동안 화면이 로딩되는 것을 기다려야 한다.


* 프로필 사진 목록

[**프로필 사진 목록 영상 링크 (00:52)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=52)

    - 프로필 사진을 등록한 사람들만 모아서 보여준다.

       + 이유 : 친구들의 프로필 사진을 더 자세히 보기 위해 추가로 화면을 클릭해야 하는 번거러움을 덜어준다. 

    
* 즐겨찾기

[**즐겨찾기 영상 링크 (01:05)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=65)

	- 관심이 있는 친구들을 따로 즐겨찾기 목록에 추가할 수 있다. 

       + 이유 : 친구목록이 지속적으로 업데이트 되더라도 관심 있는 친구들을 따로 모아 놓을 수 있다.

	- 즐겨찾기 목록에 있는 친구들은 친구 조건 설정에 따라서 친구 목록이 수정 될 때 해당 조건들에 영항을 받지 않는다. 


* 친구 조건 설정의 따른 친구 리스트 수정

[**친구 리스트 수정 영상 링크 (01:18)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=78)

	- 친구 목록을 사용자가 원하는 조건에 맞추어 보여준다. 

       + 이유 : 사용자가 본인이 원하는 대화 상대를 더욱 쉽게 찾을 수 있다. 
  		

## 2.6. 프로필 관리

[**프로필 관리 영상 링크 (01:48)**](http://15.165.69.4/portfolio/videos/yeontalk/yeontalk.mp4#t=108)

* 프로필 사진 수정
    ```
    - 사진을 찍어 프로필 사진으로 설정할 수 있다. 

    - 갤러리에 있는 사진을 프로필 사진으로 설정할 수 있다.

    - 기본 이미지를 프로필 사진으로 설정할 수 있다.
    ```

* 프로필 정보 수정
    ```
    - 사용자 본인의 프로필 정보를 수정할 수 있다. (닉네임, 나이, 국가, 지역, 자기 소개)
    ```
    

* 사진첩 관리
	```
	- 사진첩에 사용자의 사진들을 올릴 수 있다.
	```




