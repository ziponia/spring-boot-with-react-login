# Spring boot + jpa + react login example

프론트엔드 앱 실행
- npm install -g create-react-app
- cd www && npm install && npm start

백엔드 앱 실행
- mysql 설치 필요
- api/src/resources/application.properties 파일 DB 설정
- mvn install && mvn spring-boot:run

2019. 05. 17. 변경사항

- Spring boot 를 버전업 하였습니다.
- 애플리케이션 이 더 이상 mysql 을 필요로 하지 않습니다. ( h2을 사용합니다. )