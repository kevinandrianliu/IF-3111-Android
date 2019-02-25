# mobile

Tugas Besar IF3111 Platform Based Development - Android

Dibuat oleh: BKK

Bella Destiana Junaidi - 13516070 | Kevin Andrian Liwinata - 13516118 | Kevin Fernaldy - 13516109


Deskripsi Umum
------

STEPS adalah suatu aplikasi Android yang berfungsi untuk menghitung jumlah langkah total dan jarak total yang telah ditempuh oleh seseorang. Untuk dapat mengakses aplikasi, diperlukan login akun menggunakan email terlebih dahulu(jika tidak ada akun maka pengguna dapat membuat baru). 

Selain itu, terdapat beberapa fungsi lainnya yang telah diimplementasikan dalam STEPS untuk meningkatkan interaksi pengguna dengan aplikasi tersebut, seperti adanya notifikasi untuk mengingatkan pengguna serta adanya opsi untuk mengatur tampilan serta informasi akun. 

STEPS menggunakan sensor seperti gyroscope dan accelerometer serta Location Services untuk mengukur jarak dan banyak langkah yang telah digunakan.


Petunjuk Instalasi
------

1. Download aplikasi dari gitlab

2. Buka Android Studio kemudian pilih 'Open Project' untuk aplikasi 

3. Ketika sync telah selesai, tekan tombol 'Run'(segitiga hijau) pada toolbar atau pilihan menu Run -> Run 'app' untuk menginstall dan menjalankan aplikasi


Petunjuk Penggunaan
------

1. Login dengan menggunakan email dan password (jika tidak ada, tekan 'Register Now' yang terletak di bawah, kemudian buat akun baru menggunakan email dan password)

2. Untuk memulai perhitungan langkah, tekan tombol Start

3. Untuk menghentikan perhitungan langkah, tekan tombol Stop

4. Untuk melihat menu profil serta pilihan-pilihan untuk mengedit profil dan pengaturan, tekan action bar pada sebelah kiri atas

5. Untuk mengedit profil, tekan pilihan Edit Profile pada menu profil, pilih nama serta gambar yang diinginkan, dan tekan Save Changes

6. Untuk mengatur tampilan, tekan pilihan Settings pada menu profil.


Deliverables
------
mobile/app/src/main/java/com/example/tugasbesarandroid: Letak Activity menu utama(termasuk sensor yang dipakai), Activity untuk Edit Profile, Activity untuk Settings, Activity untuk Login dan Register, dan Services yang dipakai

- MainActivity.java : Activity utama(termasuk sensor untuk mengukur langkah dan jarak)
- EditProfileActivity.java : Activity Edit Profile
- LoginRegisterActivity.java : Activity Login dan Register
- SettingsActivity.java : Activity Settings
- SettingsFragment.java : Fragment Settings
- LoginFragment.java : Fragment Login
- RegisterFragment.java : Fragment Register
- BackgroundService.java : Background Service
- HandleFirebaseMessaging.java : Push Notification

 mobile/app/src/main/res/layout: Letak layout tampilan Activity dan Fragment
 
- activity_edit_profile.xml
- activity_loginregister.xml
- activity_main.xml
- fragment_login.xml
- fragment_register.xml
- nav_header.xml

mobile/app/src/main/res/menu: Letak tampilan Navigation View

- navigation_menu.xml