import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:nnz/src/controller/bottom_nav_controller.dart';
import 'package:nnz/src/pages/share/sharing_complete.dart';

class ShareAuthProvider extends GetConnect {
  final logger = Logger();

  @override
  void onInit() async {
    await dotenv.load();

    //Set baseUrl from .env file
    httpClient.baseUrl = dotenv.env['BASE_URL'];
    httpClient.defaultContentType = '';
    httpClient.timeout = const Duration(microseconds: 5000);
    super.onInit();
  }

  Future<Response> postShareAuth({
    required var authImage,
  }) async {
    final formData = FormData({});
    print("이게 이미지임");
    print(authImage);
    formData.files.add(MapEntry(
        'image', MultipartFile(authImage.path, filename: authImage.name)));
    final token = Get.find<BottomNavController>().accessToken;
    final boundary = formData.boundary;
    final res = await post(
        "https://k8b207.p.ssafy.io/api/nanum-service/nanums/21", formData,
        headers: {
          'Content-Type': 'multipart/form-data; boundary=$boundary',
          'Authorization':
              'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaXNzIjoibm56IiwiaWF0IjoxNjgzODY0NjM1LCJhdXRoUHJvdmlkZXIiOiJOTloiLCJyb2xlIjoiQURNSU4iLCJpZCI6MiwiZW1haWwiOiJzc2FmeTAwMUBzc2FmeS5jb20iLCJleHAiOjE2ODUxNjA2MzV9.pd0j7IpJvhVwUFP-2RIxiinohoOk18ectzV1Qfu3eyhijyvEC1I66_793yQjX2aoyrkKgTTA3ERkZjKgmEIhtg',
        });
    if (res.statusCode == 204) {
      Get.to(() => const SharingComplete());
    } else {
      Get.snackbar("실패", "알수없는 오류로 실패하였습니다");
    }
    return res;
  }
}
