연톡
======================

# 1. 연톡에 관하여
## 1.1. 연톡이란?
익명의 상대와 1대1 실시간 채팅, 사진 전송, 동영상 전송, 영상통화가 가능한 어플이다.


## 1.2. 연톡 시연 영상
[**연톡 시연 영상 링크**](http://52.79.51.149/yeontalk/uploads/yeontalk_testing.mp4)

## 1.3. 추가 소스
[**연톡 서버 소스 링크**](https://github.com/Kim-ChangSu/Yeontalk-Server_Source)

[**연톡 챗봇 소스 링크**](https://github.com/Kim-ChangSu/Yeontalk-Chatbot_Source)

****
# 2. 주요 기능
## 2.1. 친구 리스트
* 친구 목록
![Alt Text](http://52.79.51.149/yeontalk/uploads/userslist.gif)

    ```
    - 친구들을 최근 어플 접속 시간 순으로 정렬한다.

       + 이유 : 사용자가 최근 접속한 사람에게 대화를 걸 경우, 대화가 지속적으로 이어질 가능성이 높다.  

	- 화면을 위로 스크롤 하거나 목록 버튼을 클릭함으로써 친구 목록이 갱신된다.

	   + 이유 : 실시간으로 친구 목록이 계속해서 갱신된다면, 사용자가 리스트의 잦은 변화로 인해 불편함을 느낄 것이다.
    
    - 화면을 아래로 스크롤함에 따라 목록에 친구들이 30명씩 추가된다.

       + 이유 : 서버로부터 한번에 너무 많은 친구들의 정보들을 가져온다면, 사용자가 오랜 시간동안 화면이 로딩되는 것을 기다려야 한다.
    ```

* 프로필 사진 목록

![Alt Text](http://52.79.51.149/yeontalk/uploads/usersimage_list.gif | width=500)

    ```
    - 프로필 사진을 등록한 사람들만 모아서 보여준다.

       + 이유 : 친구들의 프로필 사진을 더 자세히 보기 위해 추가로 화면을 클릭해야 하는 번거러움을 덜어준다. 
    ```
    
* 즐겨찾기

![Alt Text](http://52.79.51.149/yeontalk/uploads/favorites.gif)


	- 관심이 있는 친구들을 따로 즐겨찾기 목록에 추가할 수 있다. 

       + 이유 : 친구목록이 지속적으로 업데이트 되더라도 관심 있는 친구들을 따로 모아 놓을 수 있다.

	- 즐겨찾기 목록에 있는 친구들은 친구 조건 설정에 따라서 친구 목록이 수정 될 때 해당 조건들에 영항을 받지 않는다. 


* 친구 조건 설정의 따른 친구 리스트 수정

	```
	- 친구 목록을 사용자가 원하는 조건에 맞추어 보여준다. 

       + 이유 : 사용자가 본인이 원하는 대화 상대를 더욱 쉽게 찾을 수 있다. 
  		
	```

## 2.2. 프로필 관리

* 프로필 사진 수정
    ```
    - 카메라 어플로 사진을 찍어 프로필 사진으로 설정할 수 있다. 

    - 갤러리에 있는 사진을 프로필 사진으로 설정할 수 있다.

    - 기본 이미지를 프로필 사진으로 설정할 수 있다.
    ```

* 프로필 정보 수정
    ```
    - 사용자 본인의 프로필 정보를 수정할 수 있다. (닉네임, 나이, 국가, 지역, 자기 소개)
    ```
    

* 사진첩 관리
	```
	- 사진첩에 본인의 사진들을 올릴 수 있다.
	
	```

## 2.3 실시간 1대1 채팅

* 실시간 채팅
    

* 채팅방 나누기 
   

* 실시간 채팅 읽음, 안 읽음 기능
    

* 네트워크 연결 끊김에 따른 채팅 에러 처리


## 2.4. 사진 전송 및 동영상 압축 전송 

* 사진과 동영상 전송

* 동영상 압축

* 동영상 보기
	

## 2.5. 영상 통화

* 영상 통화
    

## 2.6. 챗봇

* 챗봇


