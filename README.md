# android-arduino-bluetooth

## 개요
이 저장소는 Bluetooth Low Energy(BLE)를 통해 Arduino와 통신하는 간단한 Android 애플리케이션 예제를 제공합니다. JDY-08 BLE 모듈을 대상으로 하지만 BLE가 가능한 Arduino 환경이라면 어디든 적용할 수 있습니다.

이 앱은 `JDY-08`이라는 이름의 기기를 스캔하여 연결 후 스마트폰에서 아두이노로 간단한 텍스트 명령을 보낼 수 있게 합니다.

## 준비 사항
- 최신 SDK가 설치된 Android Studio
- JDY-08 BLE 모듈(또는 유사 모듈)이 장착된 Arduino 보드
- USB 케이블과 아두이노에 코드를 업로드할 기본 지식

## 설정
1. 이 저장소를 클론한 후 Android Studio로 엽니다.
2. BLE 기능이 있는 Android 기기에서 앱을 빌드하여 실행합니다.
3. JDY-08 모듈을 연결한 아두이노의 전원을 켜고 기기 이름이 `JDY-08`으로 광고되는지 확인합니다.
4. 앱을 이용해 모듈에 연결하고 명령을 전송합니다.

이 프로젝트는 Android와 Arduino 간의 블루투스 통신을 실험해 보기 위한 출발점입니다.
