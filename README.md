# Earth1Round Server

**우리는 지금까지 얼마만큼 걸었을까요?🧐** 라는 질문에서 우리의 프로젝트는 시작되었습니다. <br>
우리의 한 걸음, 한 걸음은 **동그라미 친구**들을 만나 함께 **지구탐험**🌐을 할 수 있어요. <br>

![걸어서지구한바퀴](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/e807fd1d-e8c9-42bb-b15c-c50b4e5bef01/%ED%94%84%EB%A0%88%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%981.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220918%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220918T141528Z&X-Amz-Expires=86400&X-Amz-Signature=dc01183907b9fb4c214a9c672d9255a2846f76c56dec3d2810023f09d0ef1b47&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22%25ED%2594%2584%25EB%25A0%2588%25EC%25A0%25A0%25ED%2585%258C%25EC%259D%25B4%25EC%2585%25981.png%22&x-id=GetObject)

## Environment
- Language
```markdown
- Java 15
```
- Framework & dependencies
```markdown
- Spring Boot v2.7.1
- JPA
- jsonwebtoken
- lombok
- validation
```
- Database
```markdown
- MySQL
```
- Infra
```markdown
- AWS EC2
- Ubuntu 20.04
```

## Features

### 로그인 API
- Kakao, google 소셜 로그인

### 프로필 API
- 유정 정보 조회
- 닉네임 설정

### 코스 API
- 현재 코스 조회 - 사용자가 저장한 코스들 중 현재 진행중인 코스를 조회
- 완료한 코스 리스트 조회 - 사용자 기록에 들어갈 완료한 코스의 목록을 시간순으로 조회
- 코스 저장 - 선택한 출발지와 목적지를 저장 
- 코스 완료 처리 - 진행중인 코스가 완료되었을 때 완료 처리

### 캐릭터 API
- 캐릭터 번호 조회 - 현재 사용자의 캐릭터 번호 조회
- 캐릭터 번호 수정 - 사용자의 캐릭터 번호 변경

## App Flow

![flow](https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7dabcadc-3afb-4896-b3cb-4d868cbe4472)
