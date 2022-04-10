# 약속을 심플하게, Promisor 🤙

다수의 인원이 만날 때, 모두의 일정과 위치 접근성을 고려하여 약속 시간과 장소를 선정할 수 있는 서비스

## TEAM 우아한 남매들 👨‍👨‍👧‍👦
|![image](https://user-images.githubusercontent.com/85024598/162111859-7fe5f829-ea40-4175-af51-24bc82d7c52e.png)|![image](https://user-images.githubusercontent.com/85024598/161661535-974fd170-5cb4-45d6-b878-13f2588827b9.png)|![image](https://user-images.githubusercontent.com/85024598/162609064-7b2b117c-7102-4dea-9a4d-e80519d703e4.png)|![image](https://user-images.githubusercontent.com/85024598/161661594-12ece4da-fb7f-42bb-9f0f-8f7a7ea3f8d8.png)|
|---|---|---|---|
|고산하([@headf1rst](https://github.com/headf1rst))|김채은([@chchaeun](https://github.com/chchaeun))|이준석([@juy4556](https://github.com/juy4556))|황승환([@xx0hn](https://github.com/xx0hn))|
|Leader / Back End|PM / Front End|Infra / Back End|DBA / Back End|


## 프로젝트 개요 💁‍♀️

### 제안 배경

친구들끼리 약속을 정하거나 회의를 할 때, 사람이 여러 명인 경우 각자의 일정이 있기 때문에 다양한 문제가 발생할 수 있다. 모임원이 여럿인 경우, 모두가 모이는 약속을 정하기 위해서 다음과 같은 조건이 요구된다.

1. 모두가 동시에 메신저에 접속한 상태여야 한다.
2. 모두의 개인적인 일정을 고려해서 약속을 잡아야 한다. (시간)
3. 모두의 접근성을 고려한 장소를 선정해야 한다. (장소)

위의 문제들 때문에 모두의 일정에대한 정보와 장소에 관한 정보를 수집해서 최적의 일정과 장소를 정해주는 플랫폼을 고려해보았고 그에대한 데이터를 시각화 하는 서비스가 필요하다고 생각을 하게 되었다.

### 프로젝트 목표

다수의 인원이 만날 때, 모두의 일정과 위치 접근성을 고려하여 약속 시간과 장소를 선정할 수 있는 서비스를 제공한다.

## 비즈니스 목표 🏹

- 약속 시간과 장소를 정하는 데에 소모되는 시간, 감정적 스트레스를 최소화할 수 있는 환경을 제공한다.
- 서비스 이용자를 증가시켜 가게 홍보 효과를 제공한다.

### 비즈니스 목표 달성을 위한 요구사항

![image](https://user-images.githubusercontent.com/85024598/160569147-453eec26-104e-4936-8fe1-7dfc9dbc2d6b.png)

## 요구사항 우선 순위 ⬆️

Client-Driven : 많이 사용되는 기술일 수록 우선순위를 높게 설정한다.

### 일반회원

1. 회원가입은 자체 로그인과 소셜 로그인으로 한다.
2. 사용자 프로필을 만든다(이름, 프로필 사진 등)
3. 친구를 추가하고 그룹을 만든다.
4. 그룹별로 약속을 생성한다.
5. 그룹장은 약속 날짜와 장소를 최종 등록한다(추천 기능 없이 임의 등록 가능).
6. 서로의 일정을 캘린더를 사용하여 표시하고 공유할 수 있게 한다(불가, 가능 날짜 표시).
7. 안되는 날짜에 사유를 남길수 있다.
8. 그룹원 개개인의 일정을 모아 최적의 날짜를 찾아준다.
9. 서로의 출발 장소를 지도를 통해 볼 수 있다.
10. 그룹원 개개인의 출발 장소를 모아서 모두에게 이동 시간이 공평한 중간 장소를 찾아준다.
11. 후보 장소의 맛집, 핫플을 추천해 준다.
12. 그룹원들 간 채팅을 할 수 있다.
13. 그룹별로 약속을 한눈에 볼 수 있다.
14. 사용자가 잡은 약속을 한눈에 볼 수 있다.
15. 후보 장소와 후보 날짜를 채팅방에 공유할 수 있다.
16. 사용자들이 방문한 가게에 대한 별점과 코멘트를 남길 수 있다.
17. 약속 당일에 약속에 대한 알림이 온다.
18. 날짜/장소 투표 기능을 만든다.

### 자영업자 회원

1. 가게 리뷰를 확인한다.
2. 후보 장소의 맛집, 핫플 추천 시 상위노출 될 수 있는 광고를 신청한다.
3. 가게 클릭수, 리뷰 등의 통계를 대시보드를 통해 확인한다.

## 기능 🌳

요구사항을 위해 필요한 기능과 설명을 표로 정리했다. 요구사항 11번까지의 기능을 최우선으로 개발하는 것을 목표로 잡았다.
![image](https://user-images.githubusercontent.com/85024598/159659350-86496919-843a-4f2e-856f-5c8ac11164e5.png)
![image](https://user-images.githubusercontent.com/85024598/159659375-857c4d15-6c9d-4a7f-8b52-5f9c7407be90.png)

## 유스케이스 다이어그램 🧑🏼‍🤝‍🧑🏿

![image](https://user-images.githubusercontent.com/85024598/159659440-a3fbdd4c-a0af-48cd-86d6-86f13ae60d75.png)

## 사용 기술 🛠️

### 채택 이유

['남들이 다 쓰니까'라는 이유는 없다! - 백엔드 편](https://headf1rst.github.io/etc/promisor-diary-3/)<br>
['남들이 다 쓰니까'라는 이유는 없다! - 프론트엔드 편](https://velog.io/@chchaeun/%EB%82%A8%EB%93%A4%EC%9D%B4-%EB%8B%A4-%EC%93%B0%EB%8B%88%EA%B9%8C%EB%9D%BC%EB%8A%94-%EC%9D%B4%EC%9C%A0%EB%8A%94-%EC%97%86%EB%8B%A4-%ED%94%84%EB%A1%A0%ED%8A%B8%EC%97%94%EB%93%9C-%ED%8E%B8)

### Back-end

| Category  | Stack                                                                           |
| --------- | ------------------------------------------------------------------------------- |
| Framework | - Spring Boot 2.6.3                                                             |
| Test      | - JUnit 5                                                                       |
| Infra     | - Nginx 1.14.0 <br>- AWS EC2 18.04.1 <br>- Jenkins 2.305 <br> - Sonarqube 9.0.1 |
| Database  | - MySql <br> - 공공데이터                                                       |

### Front-end

| Category           | Stack             |
| ------------------ | ----------------- |
| Language           | Typescript        |
| Framework(Library) | React             |
| CSS                | Styled Components |

### Communication

| Category          | Stack  |
| ----------------- | ------ |
| Sprint Management | Jira   |
| Notification      | Slack  |
| Documents         | Notion |

![image](https://user-images.githubusercontent.com/85024598/159660843-c0b17263-d4e7-44ef-8462-fe8afe8053b2.png)

## 프로젝트 진행 일정 🗓️

![image](https://user-images.githubusercontent.com/85024598/159660914-6990ed34-adfe-477d-a143-f84b7a6d16d8.png)

### 스프린트별 목표 정의

- **Sprint#1**
  - 요구사항 확인 및 상세화
  - 업무 우선순위 및 업무량 산정
  - 개발환경 구현
  - Jira workflow 초안
  - Promisor 화면 구성 방안
- **Sprint#2**
  - 디자인 시안 확정
  - UI 설계
  - DevOps 환경 구축
  - 회원가입 / 로그인 기능 구현
  - 친구, 그룹 CRUD 기능 구현
- **Sprint#3**
  - 약속 관련 기능 구현
  - 공공기관 API 활용
- **Sprint#4**
  - 핵심 업무 기능 구현 (체팅)
- **Sprint#5**
  - 핵심 업무 기능 구현 (GPS 기반 장소 추천)
- **Sprint#6**
  - 시스템 보안 환경 구성 완료
  - 통합 테스트

### 마일스톤

![image](https://user-images.githubusercontent.com/85024598/159661010-8e48f0a0-563e-4da7-a7ae-e16e934d7b7e.png)

## 원활한 협업을 위한 팀만의 규칙 🤝

1. 프로젝트 중 발생한 문제를 함께 그려가자.
   - 혼자 생각하고 있다고 달라지는 것은 없다!
2. 아웃 오브 사이트, 아웃 오브 마인드. 👨‍👨‍👧‍👦
   - 회의는 가능한 오프라인으로 진행한다.
3. 시간은 금이다. ❌
   - 지각은 금물이다.
   - 사정이 있으면 최소한 하루 전에 얘기한다.
4. 저스트 두 잇!
   - 에러 만들 것을 지레 걱정하지 말자.
5. 인생은 즐겁게, 일상은 치열하게.
6. 새롭게 배운 것들은 공유하자.
7. 말 아끼다가 똥 된다. 💩
   - 좋은 아이디어가 있으면 말하자.
8. 밥은 먹고 하자. 🍚
9. 기능을 추가하기 위해서는 모든 사람의 동의가 필요하다. ✅
10. 완성도는 꾸준함에서 온다. 🏃
    - 1일 1커밋 하기
