Android Project Template
 - Đây là dự án base sử dụng Dagger2, Navigation Jetpack, Databinding, RxJava, MVVM design pattern...
 - Dự án này tích hợp sẵn Firebase Crashlytics để tracking crash app, khi clone app về thì mọi người thay
  file google-services.json ứng với project của mọi người.
 - Source base này quản lý version của thư viện gradle trong file versionsManager.gradle. Bất cứ khi nào
  import thư viện vào thì nên tách phần version sang file versionsManager rồi trong gradle trỏ đến đó. Như vậy
  code sẽ rất clear và dễ quản lý.
 - Mọi người tránh sửa những class base như BaseFragment, BaseActivity để giữ cho nó đúng nghĩa là base của toàn bộ app.
 - Để sử dụng được base này thì có kiến thức về:
    + Kotlin
    + Mô hình MVVM
    + Navigation Jetpack
    + Databinding
 * Base dự án này vẫn đang trong quá trình hoàn thiện, bất kỳ ai cảm thấy thừa, hay thiếu chức năng nào đều có thể đề xuất để
 cải thiện base này.
