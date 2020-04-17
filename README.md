# [라인 Android 앱 과제](https://programmers.co.kr/competitions/139/2020-line-recruitment)

이미지 첨부 기능을 가진 메모장 어플리케이션

### 기간

20년 02월 10일 10:00 ~ 02월 24일 14:00

### 스크린샷

<img src="https://github.com/lkw1120/line-memo-android/blob/master/assets/images/Screenshot_2020-02-24-03-38-42.png" width="30%"><img src="https://github.com/lkw1120/line-memo-android/blob/master/assets/images/Screenshot_2020-02-24-03-38-58.png" width="30%"><img src="https://github.com/lkw1120/line-memo-android/blob/master/assets/images/Screenshot_2020-02-24-03-39-30.png" width="30%">
<img src="https://github.com/lkw1120/line-memo-android/blob/master/assets/images/Screenshot_2020-02-24-03-40-03.png" width="30%"><img src="https://github.com/lkw1120/line-memo-android/blob/master/assets/images/Screenshot_2020-02-24-03-40-09.png" width="30%"><img src="https://github.com/lkw1120/line-memo-android/blob/master/assets/images/Screenshot_2020-02-24-03-41-30.png" width="30%">

### 요구사항

- 메모리스트
  + 로컬 영역에 저장된 메모를 읽어 리스트 형태로 화면에 표시합니다.
  + 리스트에는 메모에 첨부되어있는 이미지의 썸네일, 제목, 글의 일부가 보여집니다. (이미지가 n개일 경우, 첫 번째 이미지가 썸네일이 되어야 함)
  + 리스트의 메모를 선택하면 메모 상세 보기 화면으로 이동합니다.
  + 새 메모 작성하기 기능을 통해 메모 작성 화면으로 이동할 수 있습니다.
- 메모 상세 보기
  + 작성된 메모의 제목과 본문을 볼 수 있습니다.
  + 메모에 첨부되어있는 이미지를 볼 수 있습니다. (이미지는 n개 존재 가능)
  + 메뉴를 통해 메모 내용 편집 또는 삭제가 가능합니다.
- 메모 편집 및 작성
  + 제목 입력란과 본문 입력란, 이미지 첨부란이 구분되어 있어야 합니다. (글 중간에 이미지가 들어갈 수 있는 것이 아닌, 첨부된 이미지가 노출되는 부분이 따로 존재)
이미지 첨부란의 ‘추가' 버튼을 통해 이미지 첨부가 가능합니다. 첨부할 이미지는 다음 중 한 가지 방법을 선택해서 추가할 수 있습니다. 이미지는 0개 이상 첨부할 수 있습니다. 외부 이미지의 경우, 이미지를 가져올 수 없는 경우(URL이 잘못되었거나)에 대한 처리도 필요합니다.
    * 사진첩에 저장되어 있는 이미지
    * 카메라로 새로 촬영한 이미지
    * 외부 이미지 주소(URL) (참고: URL로 이미지를 추가하는 경우, 다운로드하여 첨부할 필요는 없습니다.)
  + 편집 시에는 기존에 첨부된 이미지가 나타나며, 이미지를 더 추가하거나 기존 이미지를 삭제할 수 있습니다.

### 사용한 라이브러리

- Foundation
  + AppCompat
  + Android KTX
- Architecture
  + DataBinding
  + ViewModel
  + LiveData
- UI
  + ViewPager2
  + ListAdapter
- Third party
  + Glide

### 참조

- [github.com/android/architecture-components-samples](https://github.com/android/architecture-components-samples)
- [github.com/android/sunflower](https://github.com/android/sunflower)
